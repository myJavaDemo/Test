package com.ssh.service.impl;

import com.ssh.dao.UserDao;
import com.ssh.domain.User;
import com.ssh.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public void regist(User user) {
		userDao.save(user);
	}

}
