package uned.dlr.pfc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import uned.dlr.pfc.model.Proyecto;

@Transactional
public class ProyectoDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Proyecto proyecto) {
		if (proyecto == null)
			return -1L;
		if(proyecto.getId() == null) em.persist(proyecto);
		else em.merge(proyecto);
		return proyecto.getId();
	}
	
	public Proyecto getProyecto(Long id){
		return em.find(Proyecto.class,id);
	}

	public List<Proyecto> getAll() {
		return em.createQuery("SELECT p FROM Proyecto p order by p.nombre", Proyecto.class)
				.getResultList();
	}

	public void deleteById(Long id) throws Exception {
		Proyecto ent = em.find(Proyecto.class, id);
		em.remove(ent); 
	}

	public List<Proyecto> getAll(Long userId) {
		return em.createQuery("SELECT p FROM Proyecto p where p.user.id=:userId order by p.nombre", Proyecto.class)
				.setParameter("userId", userId).getResultList();
	}
	
	
}
