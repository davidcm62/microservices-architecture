const express = require('express');

const app = express();

app.get('/users',(req,res)=>{
    res.json({
        hello: 'microservice 1 😀'
    })
});

app.get('/users/:id',(req,res)=>{
    res.json({
        hello: 'microservice 1 😀',
        id: req.params.id
    })
});

app.get('/users/:id/test',(req,res)=>{
    res.json({
        hello: 'microservice 1 😀',
        id: req.params.id,
        test: "ok"
    })
});


app.listen(5001, ()=>{
    console.log("Users service on 5001");
})