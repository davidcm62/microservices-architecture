/* eslint-disable no-useless-escape */
require("dotenv").config();
const express = require("express");
const { body, validationResult } = require("express-validator");
const { v4: uuidv4 } = require("uuid");
const jwt = require("jsonwebtoken");

const UserRepository = require("./database");

const PORT = process.env.PORT || 5001;
const USER_CREATED_EXCHANGE = process.env.USER_CREATED_EXCHANGE || "USER_CREATED_EXCHANGE";
const RABBITMQ_HOST = process.env.RABBITMQ_HOST || "rabbitmq";

const app = express();
app.use(express.json());

const checkToken = async (req, res, next) => {
    const nonSecurePaths = [
        "^\/users$"
    ];

    const isNotSecure = nonSecurePaths.some((el) => {
        return new RegExp(el).test(req.path);
    });

    if (isNotSecure) return next();

    
    const authHeader = req.headers.authorization;
    const token = authHeader?.split(' ')[1];

    if (!token) {
        return res.sendStatus(401);
    } 

    jwt.verify(token, process.env.TOKEN_SIGN_KEY, (err, data) => {
        if (err) {
            return res.sendStatus(403);
        }

        req.userData = data;
        next();
    });
};

app.all("*", checkToken);

app.post(
    "/users",
    body("username").isString().isLength({ min: 1, max: 50 }),
    body("password").isString().isLength({ min: 1 }),
    body("email").isEmail().isLength({ min: 1, max: 260 }),
    body("name").isString().isLength({ min: 1, max: 200 }),
    body("image").isURL().isLength({ min: 10, max: 400 }),
    body("birthdate").isDate(),
    async (req, res) => {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return res.status(400).json(errors);
        }

        const user = {
            id: uuidv4(),
            username: req.body.username,
            email: req.body.email,
            name: req.body.name,
            image: req.body.image,
            birthdate: req.body.birthdate,
            password: req.body.password,
        };

        const userRepository = new UserRepository();
        if (!(await userRepository.insert(user))) {
            return res.status(409).json();
        }

        //TODO: publish event

        delete user.password;
        res.status(201).json(user);
    }
);

app.get("/users/me", async (req, res) => {
    const { userId } = req.userData;

    const userRepository = new UserRepository();
    const result = await userRepository.findById(userId);
    if (!result) {
        return res.status(404).json();
    }

    res.status(200).json(result);
});

app.get("/users/:id", async (req, res) => {
    const { id } = req.params;
    const userRepository = new UserRepository();
    const result = await userRepository.findById(id);
    if (!result) {
        return res.status(404).json();
    }

    res.status(200).json(result);
});

app.listen(PORT, () => {
    console.log(`Users service running on http://localhost:${PORT}`);
});
