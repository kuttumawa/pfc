package uned.dlr.pfc.service;

import uned.dlr.pfc.dao.UserDao;
import uned.dlr.pfc.model.User;

public class UserServiceImp implements UserServiceIF {

	UserDao userDao;
	@Override
	public User crear(User user) {
		userDao.save(user);
		return user;
	}

	@Override
	public User actualizar(User user) {
		userDao.save(user);
		return user;
	}

	@Override
	public void borrar(Long userId) throws Exception {
		userDao.deleteById(userId);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User get(Long id) {
		return userDao.getUser(id);
	}

}
