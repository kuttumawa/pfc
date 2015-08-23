package uned.dlr.pfc.service;

import uned.dlr.pfc.model.User;

public interface UserServiceIF {
	public User crear(User user);
	public User actualizar(User user);
	public void borrar(Long userId) throws Exception;
	public User get(Long id);
	public User find(String user,String pass);

}
