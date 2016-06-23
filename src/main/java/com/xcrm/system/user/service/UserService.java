package com.xcrm.system.user.service;

import java.util.List;

import com.xcrm.system.user.dao.IUserDao;
import com.xcrm.system.user.dao.impl.UserDaoImpl;
import com.xcrm.system.user.entity.User;



public class UserService {
	private IUserDao userDao;

	public UserService() {
		userDao = new UserDaoImpl();
	}
	public boolean addUser(User user) throws Exception {
		return userDao.add(user) > 0 ? true : false;
	}
	public List<User> getUsers() throws Exception {
		return userDao.list();
	}
	public boolean deleteUser(String id) throws Exception {
		return userDao.delete(id) > 0 ? true : false;
	}
	public User findById(String id) throws Exception {
		return userDao.findById(id);
	}
	public void updateUser(User user) throws Exception {
		 userDao.update(user);
	}
}
