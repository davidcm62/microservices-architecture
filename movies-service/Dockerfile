FROM node:16-alpine3.11

WORKDIR /usr/src/app

COPY package*.json ./
COPY index.js ./

RUN npm install

EXPOSE 5002

CMD node index.js
