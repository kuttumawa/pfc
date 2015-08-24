package uned.dlr.pfc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import uned.dlr.pfc.model.PfcTest;

@Transactional
public class PfcTestDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(PfcTest pfcTest) {
		if (pfcTest == null)
			return -1L;
		if(pfcTest.getId() == null) em.persist(pfcTest);
		else em.merge(pfcTest);
		return pfcTest.getId();
	}
	
	public PfcTest getPfcTest(Long id){
		return em.find(PfcTest.class,id);
	}

	public List<PfcTest> getAll() {
		return em.createQuery("SELECT p FROM PfcTest p order by p.nombre", PfcTest.class)
				.getResultList();
	}

	public void deleteById(Long id) throws Exception {
		PfcTest ent = em.find(PfcTest.class, id);
		em.remove(ent); 
	}
	
	
}
