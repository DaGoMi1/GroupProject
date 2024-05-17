const express = require('express');
const router = express.Router();
const userController = require('../controllers/user.controller');
const scheduleController = require('../controllers/schedule.controller');

router.post('/home/user', userController.createUser);
router.get('/home/user', userController.getUser);
router.post('/home/user/login', userController.userLogin);
router.post('/admin/schedule/add', scheduleController.createSchedule);
router.get('/schedule/list/data', scheduleController.getSchedule);

module.exports = router;