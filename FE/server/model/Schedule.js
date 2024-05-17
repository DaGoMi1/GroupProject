const { type } = require('@testing-library/user-event/dist/type');
const mongoose = require('mongoose');
const { Schema } = mongoose;

const ScheduleObj = {
  startDay : {
    type : String,
    required : true,
  },

  endDay : {
    type : String,
    required : true,
  },

  title : {
    type : String,
    required : true,
  },

  color : {
    type : String,
    required : true,
  }
}

const scheduleSchema = Schema(ScheduleObj, {timestamps : true})

const Schedule = mongoose.model("Schedule", scheduleSchema);

module.exports = Schedule;