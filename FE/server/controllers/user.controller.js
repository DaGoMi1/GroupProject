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

userController.userLogin = async (req, res) => {
  // 입력한 email이 있는지 먼저 찾고 뒤에 그 이메일의 비밀번호를 찾음
  try {
    const {email, password} = req.body;
    const user = await User.findOne({email});

    if(user){
      if(user.password === password){
        const token = user.generateToken();        
        return res.status(200).json({status:"Ok",user, token});
      }
    }
    throw new Error("아이디 또는 비밀번호가 일치하지 않습니다.");
  } catch (error) {
    res.status(400).json({status : "fail",message : error.message})
  }
}

userController.getUser = async(req,res) => {
  try {
    const {userId} = req;
    const user = User.findById(userId);
    if(!user){
      throw new Error("유저 id 찾을 수 없음");
    }
    res.status(200).json({status: "Ok", user})
  } catch (error) {
    res.status(400).json({status : "fail",message : error.message})
  }
}
module.exports = userController;