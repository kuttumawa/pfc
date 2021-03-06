package uned.dlr.pfc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import uned.dlr.pfc.model.Proyecto;
import uned.dlr.pfc.model.User;

@Transactional
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(User user) {
		if (user == null)
			return -1L;
		if(user.getId() == null) em.persist(user);
		else em.merge(user);
		return user.getId();
	}
	
	public User getUser(Long id){
		return em.find(User.class,id);
	}

	public List<User> getAll() {
		return em.createQuery("SELECT p FROM User p order by p.nombre", User.class)
				.getResultList();
	}

	public void deleteById(Long id) throws Exception {
		User ent = em.find(User.class, id);
		em.remove(ent); 
	}

	public List<User> find(String usuario,String pass) {
		return em.createQuery("SELECT p FROM User p where p.nombre=:usuario and p.password=:pass order by p.nombre ", User.class)
				.setParameter("usuario", usuario).setParameter("pass", pass).getResultList();
	}

	public List<User> findPorNombre(String filter) {
		return em.createQuery("SELECT p FROM User p where p.nombre like :filter order by p.nombre ", User.class)
				.setParameter("filter", (filter!=null?filter:"")).getResultList();
	
	}
	
	
}
