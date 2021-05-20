const express = require('express');

const app = express();
app.use(express.json());

app.get('/movies',(req,res)=>{
    
    console.log(req.headers);

    res.sendStatus(501);
});


app.listen(5002, ()=>{
    console.log("Microservice 2 on 5002");
})