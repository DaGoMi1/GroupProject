const User = require('../model/User');

const userController = {};

userController.createUser = async (req, res) => {
  try {
    const {email, password, password2, username, name} = req.body;
    const newUser = new User({email, password, password2, username, name});
    await newUser.save();
    res.status(200).json({status:'ok', data: newUser})
  } catch (error) {
    res.status(400).json({status:'fail', message: error.message});    
  }
}

userController.getUser = async (req, res) => {
  try {
    const userList = await User.find({}).select("email password password2 username name");
    res.status(200).json({status:'ok', data: userList})
  } catch (error) {
    res.status(400).json({status:'fail', message : error.message})
  }
}

module.exports = userController;