class UserRepository {
    constructor() {
        this.pool = require("./mysqlPool");
    }

    async checkUsernameExists(username) {
        const result = await this.pool.query(
            "SELECT * FROM users WHERE username=LOWER(?);",
            [username]
        );
        return result.length != 0;
    }

    async checkEmailExists(email) {
        const result = await this.pool.query(
            "SELECT * FROM users WHERE email=LOWER(?);",
            [email]
        );
        return result.length != 0;
    }

    async insert({
        id,
        username,
        email,
        name,
        image,
        birthdate
    }) {
        if(await this.checkUsernameExists(username)) return false;
        if(await this.checkEmailExists(email)) return false;

        await this.pool.query(
            `INSERT INTO users (id,username,email,name,image,birthdate) 
                VALUES (?,?,?,?,?,?);`,
            [
                id,
                username,
                email,
                name,
                image,
                birthdate
            ]
        );
        return true;
    }

    async findById(id) {
        const result = await this.pool.query(
            `SELECT *
            FROM users
            WHERE id=?;`,
            [id]
        );
        return result.length != 0 ? result[0] : null;
    }
}

module.exports = UserRepository;
