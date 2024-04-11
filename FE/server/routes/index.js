const express = require('express');
const router = express.Router();
const userController = require('../controllers/user.controller');

router.post('/api/user', userController.createUser);
router.get('/api/user', userController.getUser);

module.exports = router;