const mongoose = require('mongoose');
const { Schema } = mongoose;

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

const User = mongoose.model("User", userSchema);

module.exports = User;