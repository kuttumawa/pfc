package uned.dlr.pfc.service;

import java.util.List;

import uned.dlr.pfc.model.Codigo;
import uned.dlr.pfc.model.Proyecto;

public interface ProyectoServiceIF {
	public Proyecto crear(Proyecto proyecto);
	public Proyecto addCodigo(Long long1, Codigo codigo);
	public Proyecto actualizar(Proyecto proyecto);
	public Proyecto getProyecto(Long id);
	public void borrarProyecto(Long id) throws Exception;
	public void borrarCodigoProyecto(Long proyectoId,Long codigoId) throws Exception;
	public List<Proyecto> getProyectosUser(Long userId);

}
