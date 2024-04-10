const mongoose = require('mongoose');
const express = require('express');
const bodyParser = require('body-parser');
const indexRouter = require('./routes/index');
const cors = require('cors');

const app = express();
app.use(bodyParser.json());
app.use(cors());
app.use(indexRouter);

const mongoURI = 'mongodb://localhost:27017/ds';

mongoose.connect(mongoURI, {useNewUrlParser: true})
  .then(()=>{
    console.log('몽구스 연결 완료');
  })
  .catch((error)=>{
    console.log('몽구스 연결 실패 :', error);
  })

app.listen(8080, ()=>{
  console.log('8080번에 연결되었다.');
})