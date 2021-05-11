const express = require('express');

const app = express();
app.use(express.json());

app.post('/movies',(req,res)=>{
    
    res.json({
        hello: "Microservice 2 😄",
        body: req.body
    });
});


app.listen(5002, ()=>{
    console.log("Microservice 2 on 5002");
})