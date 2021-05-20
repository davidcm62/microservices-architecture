FROM node:16-alpine3.11

WORKDIR /usr/src/app

COPY package*.json ./
COPY .env ./
COPY src ./src

RUN npm install

EXPOSE 5001

CMD node ./src/index.js
