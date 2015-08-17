package pfc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uned.dlr.pfc.model.User;
import uned.dlr.pfc.service.UserServiceIF;

public class TestUser {

	static ClassPathXmlApplicationContext context;
	static UserServiceIF userService;
	
	@BeforeClass
	public  static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext_test.xml");
		userService=(UserServiceIF) context.getBean("userService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCrearUser() {
		User user=userService.crear(new User("login","pass"));
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals("login",user.getNombre());
		
	}
	@Test
	public void testActualizaUser() {
		User user=userService.crear(new User("login","pass"));
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals("login",user.getNombre());
		
		user=userService.get(user.getId());
		user.setNombre("loginNEW");
		user=userService.actualizar(user);
		user=userService.get(user.getId());
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals("loginNEW",user.getNombre());
		
	}

}
