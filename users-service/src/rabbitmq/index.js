const amqplib = require("amqplib");

const USER_CREATED_EXCHANGE = process.env.USER_CREATED_EXCHANGE || "USER_CREATED_EXCHANGE";
const RABBITMQ_URL = process.env.RABBITMQ_URL || "amqp://rabbitmq:5672";

module.exports = async (event) => {
    const connection = await amqplib.connect(RABBITMQ_URL, "heartbeat=60");
    const channel = await connection.createChannel();
    
    await channel.assertExchange(USER_CREATED_EXCHANGE, "fanout", { durable: false });
    await channel.publish(USER_CREATED_EXCHANGE, "", Buffer.from(JSON.stringify(event)));

    console.log(`[user-service]: event ${JSON.stringify(event)} published to rabbitmq`);
    
    setTimeout(() => {
        connection.close();
    }, 500);
};