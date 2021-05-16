import pika
import json

HOST = "localhost"
EXCHANGE = "USER_CREATED_EXCHANGE"


connection = pika.BlockingConnection(pika.ConnectionParameters(host=HOST))
channel = connection.channel()
channel.exchange_declare(exchange=EXCHANGE, exchange_type='fanout')

result = channel.queue_declare(queue='', exclusive=True)
queue_name = result.method.queue
channel.queue_bind(exchange=EXCHANGE, queue=queue_name)



print("Waiting for USER_CREATED events...")

def callback(ch, method, properties, body):
    event = json.loads(body)
    print(f"[email-service] sending email to {event['username']}...")

channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)
channel.start_consuming()
