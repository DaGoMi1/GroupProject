const mongoose = require('mongoose')

const Schedule = require('../model/Schedule');

const ScheduleController = {};

ScheduleController.createSchedule = async (req, res) => {
  try {
    const {startDay, endDay, title, color} = req.body
    const newSchedule = new Schedule({startDay, endDay, title, color});
    await newSchedule.save();
    res.status(200).json({status:'ok', newSchedule});
  } catch (error) {
    res.status(400).json({status:'fail', message:error.message});
  }
}

ScheduleController.getSchedule = async(req,res)=>{
  try {
    const scheduleList = await Schedule.find({});
    res.status(200).json({status:"ok", scheduleList});
  } catch (error) {
    res.status(400).json({status:"fail", message:error.message});
  }
}

ScheduleController.updateSchedule = async(req, res)=>{
  try {
    const { _id } = req.params;
    const {startDay, endDay, color, changeTitle} = req.body;
    const updateSchedule = await Schedule.updateOne({_id},
      {
        $set:{
          startDay,
          endDay,
          color,
          changeTitle
        }
    })
    
    res.status(200).json({status:"ok", updateSchedule});
  } catch (error) {
    res.status(400).json({status:"Fail", message: error.message})
  }
}

ScheduleController.deleteSchedule = async(req,res)=>{
  try {
    const { _id } = req.body;
    const deleteSchedule = await Schedule.deleteOne({_id});
    res.status(200).json({status:"ok", deleteSchedule});
  } catch (error) {
    res.status(400).json({status:"fail", message: error.message});
  }
}

module.exports = ScheduleController;