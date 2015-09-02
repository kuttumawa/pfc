package uned.dlr.pfc.service;

import uned.dlr.pfc.model.Codigo;
import uned.dlr.pfc.model.Proyecto;
import uned.dlr.pfc.model.User;

public interface CodigoServiceIF {
	public Codigo crear(Codigo codigo);
	public Codigo actualizar(Codigo codigo);
	public void borrar(Long codigoId) throws Exception;
	public String ejecutar(Long id);
	public String minificar(Long id);
	public String revisar(Long id);
	public String ofuscar(Long id);
	public Proyecto compartir(Codigo code,User user1,User user2,String descripcion);
	public Codigo getCodigo(Long id);
	public String ejecutar(String codigo);
	public void actualizarCheckPermisos(Codigo codigo, User user);
	public String optimizar(Long codigoId);
	public void marcarCodigoPassedOrNotPassed(Codigo codigo, User user,
			boolean b);
	

}
