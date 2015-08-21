package pfc;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uned.dlr.pfc.model.Codigo;
import uned.dlr.pfc.model.Proyecto;
import uned.dlr.pfc.model.User;
import uned.dlr.pfc.service.CodigoServiceIF;
import uned.dlr.pfc.service.ProyectoServiceIF;
import uned.dlr.pfc.service.UserServiceIF;

public class TestProyecto  {
	static ClassPathXmlApplicationContext context;
	static UserServiceIF userService;
	static CodigoServiceIF codigoService;
	static ProyectoServiceIF proyectoService;
	int proyectoCount;
	
	@BeforeClass
	public  static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext_test.xml");
		userService=(UserServiceIF) context.getBean("userService");
		codigoService=(CodigoServiceIF) context.getBean("codigoService");
		proyectoService=(ProyectoServiceIF) context.getBean("proyectoService");
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	public Proyecto crearProyecto() {
		User user=userService.crear(new User("user1","pass1"));
		Proyecto proyecto=new Proyecto("p"+proyectoCount++,user);;
		proyecto=proyectoService.crear(proyecto);
		assertNotNull(proyecto);
		List<Proyecto> proyectos=proyectoService.getProyectosUser(user.getId());
		assertTrue(proyectos.size()>0);
		return proyecto;
	}
	
	@Test
	public void testAddCodigoAlProyecto() {
		Proyecto p1=crearProyecto();
		assertTrue(proyectoService.getProyecto(p1.getId()).getCodigos().isEmpty());
		Codigo c1=codigoService.crear(new Codigo("factorial"));
		proyectoService.addCodigo(p1.getId(),c1);
		assertFalse(proyectoService.getProyecto(p1.getId()).getCodigos().isEmpty());
		assertTrue(proyectoService.getProyecto(p1.getId()).getCodigos().get(0).getPropietarios().contains(p1.getId()));
	}
	@Test
	public void testBorrarProyectoSinCodigoCompartido() {
		Proyecto p1=crearProyecto();
		assertTrue(proyectoService.getProyecto(p1.getId()).getCodigos().isEmpty());
		Codigo c1=codigoService.crear(new Codigo("factorial"));
		proyectoService.addCodigo(p1.getId(),c1);
		assertFalse(proyectoService.getProyecto(p1.getId()).getCodigos().isEmpty());
		assertTrue(proyectoService.getProyecto(p1.getId()).getCodigos().get(0).getPropietarios().contains(p1.getId()));
		
		try {
			proyectoService.borrarProyecto(p1.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNull(proyectoService.getProyecto(p1.getId()));
		assertNull(codigoService.getCodigo(c1.getId()));
		
	}
	@Test
	public void testBorrarProyectoConCodigoCompartido() {
		Proyecto p1=crearProyecto();
		assertTrue(proyectoService.getProyecto(p1.getId()).getCodigos().isEmpty());
		Codigo c1=codigoService.crear(new Codigo("factorial"));
		proyectoService.addCodigo(p1.getId(),c1);
		assertFalse(proyectoService.getProyecto(p1.getId()).getCodigos().isEmpty());
		assertTrue(proyectoService.getProyecto(p1.getId()).getCodigos().get(0).getPropietarios().contains(p1.getId()));
		
		User user2=userService.crear(new User("user2","pass2"));
		codigoService.compartir(c1, p1.getUser(), user2, "Compartiendo "+c1.getNombre());
		
		try {
			proyectoService.borrarProyecto(p1.getId());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNull(proyectoService.getProyecto(p1.getId()));
		assertNotNull(codigoService.getCodigo(c1.getId()));
		
	}
	
	

	
}
