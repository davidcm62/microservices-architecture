FROM node:16-alpine3.11

WORKDIR /usr/src/app

COPY package*.json ./
COPY index.js ./

RUN npm install

EXPOSE 5003

CMD node index.js
