const express = require('express');

const app = express();
app.use(express.json());

app.get('/micro3',(req,res)=>{
    
    res.json({
        hello: "Microservice 3 😄 limited",
    });
});


app.listen(5003, ()=>{
    console.log("Microservice 3 on 5003");
})