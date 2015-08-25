package uned.dlr.pfc.service;


import java.util.List;

import uned.dlr.pfc.model.User;

public interface UserServiceIF {
	public User crear(User user);
	public User actualizar(User user);
	public void borrar(Long userId) throws Exception;
	public User get(Long id);
	public User find(String user,String pass);
	public List<User> getAll(String filter);
	public User findPorNombre(String filter);

}
