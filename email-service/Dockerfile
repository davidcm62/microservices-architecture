FROM python:3-alpine

WORKDIR /usr/src/app

COPY service.py ./

RUN pip install pika

CMD python service.py
