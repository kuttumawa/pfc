package uned.dlr.pfc.service;



import java.util.List;

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

	@Override
	public User find(String user,String pass) {
		List<User> users= userDao.find(user,pass);
		if (!users.isEmpty()) return users.get(0);
		return null;
	}

	@Override
	public List<User> getAll(String filter) {
		return userDao.findPorNombre(filter);
	}
	@Override
	public User findPorNombre(String filter) {
		List<User> users= userDao.findPorNombre(filter);
		if (!users.isEmpty()) return users.get(0);
		return null;
	}

}
