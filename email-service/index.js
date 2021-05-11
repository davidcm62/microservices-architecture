const amqp = require("amqplib/callback_api");

amqp.connect("amqp://rabbitmq", function (error0, connection) {
    if (error0) {
        throw error0;
    }

    connection.createChannel(function (error1, channel) {
        if (error1) {
            throw error1;
        }
        var exchange = "USER_CREATED";

        channel.assertExchange(exchange, "fanout", {
            durable: false,
        });

        channel.assertQueue("", { exclusive: true }, (error2, q) => {
            if (error2) {
                throw error2;
            }
            console.log(" [*] Waiting for messages in %s. To exit press CTRL+C",q.queue);
            channel.bindQueue(q.queue, exchange, "");

            channel.consume(q.queue,function (msg) {
                if (msg.content) {
                    console.log(" [x] received : %s", msg.content.toString());
                    //send email
                    console.log("email sent");
                }
            },{ noAck: true });
        });
    });
});
