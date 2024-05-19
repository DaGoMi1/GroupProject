const mongoose = require('mongoose');
const { Schema } = mongoose;
const jwt = require('jsonwebtoken');
require('dotenv').config()
const JWT_SECRET_KEY = process.env.JWT_SECRET_KEY

const UserObj = {
  email : {
    type : String,
    required : true,
  },

  password : {
    type : String,
    required : true,
  },
  
  password2 : {
    type : String,
    required : true,
  },

  name : {
    type : String,
    required : true,
  },

  username : {
    type : String,
    required : true,
  },
}


const userSchema = Schema(UserObj, {timestamps : true} )

userSchema.methods.generateToken = () => {
  const token = jwt.sign({ _id: this._id }, JWT_SECRET_KEY );
  return token
}

const User = mongoose.model("User", userSchema);

module.exports = User;