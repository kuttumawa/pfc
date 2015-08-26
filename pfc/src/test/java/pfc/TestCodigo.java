package pfc;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uned.dlr.pfc.model.Codigo;
import uned.dlr.pfc.model.PfcTest;
import uned.dlr.pfc.model.User;
import uned.dlr.pfc.model.WhatToShareEnum;
import uned.dlr.pfc.service.CodigoServiceIF;

public class TestCodigo {
	static ClassPathXmlApplicationContext context;
	static CodigoServiceIF codigoService;
	static String CODIGO_1 = "describe(\"Factorial\",function() {"
			+ "it(\"should get factorial of given number\"),function(){" + "expect(factorial(3)).toEqual(6);" + "};"
			+ " " + "});";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext_test.xml");
		codigoService = (CodigoServiceIF) context.getBean("codigoService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCrearCodigoNuevo() {
		CodigoServiceIF codigoService = (CodigoServiceIF) context.getBean("codigoService");
		Codigo nuevoCodigo = new Codigo("NuevoCodigo");
		nuevoCodigo.addPropietario(13L);
		;

		nuevoCodigo = codigoService.crear(nuevoCodigo);
     
		assertTrue(nuevoCodigo.getPropietarios().size() > 0);
		assertTrue(nuevoCodigo.getPropietarios().contains(13L));
		
		nuevoCodigo.setTest(new PfcTest("nombreTest") );
		nuevoCodigo = codigoService.actualizar(nuevoCodigo);
		assertNotNull(nuevoCodigo.getTest().getId());
	}
	

	@Test
	public void testBorrarCodigoNoCompartido() throws Exception {
		CodigoServiceIF codigoService = (CodigoServiceIF) context.getBean("codigoService");
		Codigo nuevoCodigo = new Codigo("NuevoCodigo");
		nuevoCodigo = codigoService.crear(nuevoCodigo);
		assertTrue(nuevoCodigo.getPropietarios().size() == 0);
		codigoService.borrar(nuevoCodigo.getId());
		assertNull(codigoService.getCodigo(nuevoCodigo.getId()));

	}

	@Test
	public void testNoseDebeBorraCodigoCompartido() throws Exception {
		CodigoServiceIF codigoService = (CodigoServiceIF) context.getBean("codigoService");
		Codigo nuevoCodigo = new Codigo("NuevoCodigo");
		nuevoCodigo.addPropietario(13L);
		nuevoCodigo = codigoService.crear(nuevoCodigo);
		assertTrue(nuevoCodigo.getPropietarios().size() > 0);
		assertTrue(nuevoCodigo.getPropietarios().contains(13L));
		codigoService.borrar(nuevoCodigo.getId());
		assertNotNull(codigoService.getCodigo(nuevoCodigo.getId()));

	}
	
	@Test
	public void noSePermiteAlterarCodeDeUnCompartidoComoTest() throws Exception {
		CodigoServiceIF codigoService = (CodigoServiceIF) context.getBean("codigoService");
		Codigo nuevoCodigo = new Codigo("NuevoCodigo");
		nuevoCodigo.addPropietario(13L);
		nuevoCodigo.setCode("No Cambiar");
		nuevoCodigo.setTest(new PfcTest());
		nuevoCodigo.getTest().setCode("Si puede Cambiar");
		nuevoCodigo.setWhatToShare(WhatToShareEnum.test);
		nuevoCodigo = codigoService.actualizar(nuevoCodigo);
		
		assertTrue(nuevoCodigo.getPropietarios().size() > 0);
		assertTrue(nuevoCodigo.getPropietarios().contains(13L));
		User userCompartir = new User();
		userCompartir.setId(66L);
		nuevoCodigo.addPropietario(userCompartir.getId());
		nuevoCodigo = codigoService.actualizar(nuevoCodigo);
		//intentamos cambiar code
		nuevoCodigo.setCode("CAMBIO");
		nuevoCodigo.getTest().setCode("CAMBIO");
		codigoService.actualizarCheckPermisos(nuevoCodigo, userCompartir);
		nuevoCodigo=codigoService.getCodigo(nuevoCodigo.getId());
		assertNotEquals(nuevoCodigo.getCode(),"CAMBIO");
		assertEquals(nuevoCodigo.getTest().getCode(),"CAMBIO");
		
		
		assertNotNull(codigoService.getCodigo(nuevoCodigo.getId()));

	}
	
	
	final String CODIGO_2="function v(){return 'Hello, Rhino';};var s = 'Hello, Rhino'; s;";
	@Test
	public void testEjecutarCodigo() throws Exception {
		Codigo codigo=new Codigo("SayHello");
		codigo.setCode(CODIGO_2);
		codigoService.crear(codigo);
		assertNotNull(codigo.getId());
		String resultado = codigoService.ejecutar(codigo.getId()) ;
		assertEquals("Hello, Rhino",resultado);
	}
	

}
