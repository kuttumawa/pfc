package uned.dlr.pfc.service;

import uned.dlr.pfc.dao.CodigoDao;
import uned.dlr.pfc.dao.ProyectoDao;
import uned.dlr.pfc.model.Codigo;
import uned.dlr.pfc.model.Proyecto;
import uned.dlr.pfc.model.User;
import uned.dlr.pfc.util.ExecutorIF;

public class CodigoServiceImp implements CodigoServiceIF {

	
	private CodigoDao codigoDao;
	private ProyectoDao proyectoDao;
	private ExecutorIF executor; 

	
	public Codigo crear(Codigo codigo){
		codigoDao.save(codigo);
		return codigo;
	}
	public Codigo actualizar(Codigo codigo){
		codigoDao.save(codigo);
		return codigo;
	}

	public String ejecutar(Long id) {
		Codigo codigo=codigoDao.getCodigo(id);
		return executor.execute(codigo.getCode());
	}

	public String minificar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String revisar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String ofuscar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCodigoDao(CodigoDao codigoDao) {
		this.codigoDao = codigoDao;
	}
	public void setProyectoDao(ProyectoDao proyectoDao) {
		this.proyectoDao = proyectoDao;
	}

	@Override
	public Proyecto compartir(Codigo code,User user1, User user2,String descripcion) {
		//Crear un proyecto nuevo para el usuario que contenga el codigo
		Proyecto p=new Proyecto();
		p.setNombre(code.getNombre()+"["+user1.getNombre()+"]");
		p.setDescripcion(descripcion);
		p.setUser(user2);
		p.addCode(code);
		proyectoDao.save(p);
		code.addPropietario(p.getId());
		codigoDao.save(code);
		return p;
	}

	@Override
	public void borrar(Long codigoId) throws Exception {
		Codigo codigo=codigoDao.getCodigo(codigoId);
		if(codigo.getPropietarios().size()==0)codigoDao.deleteById(codigoId);
	}
	@Override
	public Codigo getCodigo(Long id) {
		return codigoDao.getCodigo(id);
	}
	
	public void setExecutor(ExecutorIF executor) {
		this.executor = executor;
	}

	
	
	

}
