package uned.dlr.pfc.service;

import java.util.Iterator;

import uned.dlr.pfc.dao.CodigoDao;
import uned.dlr.pfc.dao.ProyectoDao;
import uned.dlr.pfc.model.Codigo;
import uned.dlr.pfc.model.Proyecto;

public class ProyectoServiceImp implements ProyectoServiceIF{
	private CodigoDao codigoDao;
	private ProyectoDao proyectoDao;
	@Override
	public Proyecto crear(Proyecto proyecto) {
		proyectoDao.save(proyecto);
		return proyecto;
	}

	@Override
	public Proyecto addCodigo(Long proyectoId,Codigo codigo) {
		Proyecto p=proyectoDao.getProyecto(proyectoId);
		codigo.addPropietario(p.getId());
		p.addCode(codigo);
		codigoDao.save(codigo);
		proyectoDao.save(p);
		return p;
	}

	@Override
	public Proyecto actualizar(Proyecto proyecto) {
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
	public Proyecto getProyecto(Long id) {
		return proyectoDao.getProyecto(id);
	}

	@Override
	public void borrarProyecto(Long proyectoId) throws Exception {
		Proyecto p=proyectoDao.getProyecto(proyectoId);
		for(Codigo c :p.getCodigos()){
			borrarCodigoProyecto(proyectoId,c.getId());
		}
		proyectoDao.deleteById(proyectoId);
	}

	@Override
	public void borrarCodigoProyecto(Long proyectoId, Long codigoId) throws Exception {
		Proyecto p=proyectoDao.getProyecto(proyectoId);
		Iterator<Codigo> it=p.getCodigos().iterator();
		while(it.hasNext()){
			Codigo c=it.next();
			if(c.getId().equals(codigoId)){
			  it.remove();
		    }
		}
		proyectoDao.save(p);
		Codigo codigo=codigoDao.getCodigo(codigoId);
		codigo.getPropietarios().remove(proyectoId);
		if(codigo.getPropietarios().size()==0) codigoDao.deleteById(codigo.getId());
		else{
			codigoDao.save(codigo);
		}

		
	}

}