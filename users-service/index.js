const express = require("express");
const amqp = require("amqplib/callback_api");

let rabbitChannel;

amqp.connect("amqp://rabbitmq", function (error, connection) {
    if(error){
        throw error;
    }

    connection.createChannel(function (error1, channel) {
        if (error1) {
            throw error1;
        }
        rabbitChannel = channel;
    });
});

const app = express();
app.use(express.json());

app.get("/users", (req, res) => {
    res.json({
        hello: "microservice 1 😀",
    });
});

app.get("/users/:id", (req, res) => {
    res.json({
        hello: "microservice 1 😀",
        id: req.params.id,
    });
});

app.get("/users/:id/test", (req, res) => {
    res.json({
        hello: "microservice 1 😀",
        id: req.params.id,
        test: "ok",
    });
});

app.post("/users", (req, res) => {
    const { username, password } = req.body;

    // save user to db
    // ...
    //
    console.log(`User ${username} - ${password} registered`);
    
    //publish event
    let exchange = "USER_CREATED";
    let event = {
        username: username
    };

    rabbitChannel.assertExchange(exchange, "fanout", { durable: false });
    rabbitChannel.publish(exchange, "", Buffer.from(JSON.stringify(event)));


    res.json(req.body);
});

app.listen(5001, () => {
    console.log("Users service on 5001");
});
