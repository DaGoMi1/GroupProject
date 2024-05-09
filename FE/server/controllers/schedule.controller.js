const Schedule = require('../model/Schedule');

const ScheduleController = {};

ScheduleController.createSchedule = async (req, res) => {
  try {
    const {startDay, endDay, title, color} = req.body
    const newSchedule = new Schedule({startDay, endDay, title, color});
    await newSchedule.save();
    res.status(200).json({status:'ok', data: newSchedule});
  } catch (error) {
    res.status(400).json({status:'fail', message:error.message});
  }
}

ScheduleController.getSchedule = async(req,res)=>{
  try {
    const scheduleList = await Schedule.find({});
    res.status(200).json({status:"ok", data:scheduleList});
  } catch (error) {
    res.status(400).json({status:"fail", message:error.message});
  }
}

module.exports = ScheduleController;