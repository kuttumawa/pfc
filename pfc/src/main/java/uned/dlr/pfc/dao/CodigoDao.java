package uned.dlr.pfc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import uned.dlr.pfc.model.Codigo;

@Transactional
public class CodigoDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(Codigo codigo) {
		if (codigo == null)
			return -1L;
		if(codigo.getId() == null) em.persist(codigo);
		else em.merge(codigo);
		return codigo.getId();
	}
	
	public Codigo getCodigo(Long id){
		return em.find(Codigo.class,id);
	}

	public List<Codigo> getAll() {
		return em.createQuery("SELECT p FROM Codigo p order by p.nombre", Codigo.class)
				.getResultList();
	}

	public void deleteById(Long id) throws Exception {
		Codigo ent = em.find(Codigo.class, id);
		em.remove(ent); 
	}
	
	
}
