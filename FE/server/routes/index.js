const express = require('express');
const router = express.Router();
const userController = require('../controllers/user.controller');
const scheduleController = require('../controllers/schedule.controller');
const authController = require('../controllers/auth.controller');

router.post('/home/user', userController.createUser);
router.get('/home/user', userController.getUser);
router.post('/home/user/login', userController.userLogin);
router.post('/admin/schedule/add', scheduleController.createSchedule);
router.get('/schedule/list/data', scheduleController.getSchedule);
router.get('/home/name', authController.authenticate, userController.getUser);
router.delete('/admin/schedule', scheduleController.deleteSchedule);
router.put('/admin/schedule/:id', scheduleController.updateSchedule);
module.exports = router;