package pfc;



import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uned.dlr.pfc.model.PfcTest;
import uned.dlr.pfc.service.PfcTestServiceIF;

public class TestPfcTest {
	static ClassPathXmlApplicationContext context;
	static PfcTestServiceIF pfcTestService;
	static String CODIGO_1 = "describe(\"Factorial\",function() {" + "it(\"should get factorial of given number\"),function(){"
			+ "expect(factorial(3)).toEqual(6);" + "};" + " " + "});";

	@BeforeClass
	public  static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext_test.xml");
		pfcTestService=(PfcTestServiceIF) context.getBean("pfcTestService");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCrearTest() {
		PfcTest test=pfcTestService.crear("Test1");
		assertNotNull(test);
		assertNotNull(test.getId());
		assertEquals("Test1",test.getNombre());
		
	}
	
	@Test
	public void testActualizarTest() {
		PfcTest test=pfcTestService.crear("Test1");
		assertNotNull(test);
		assertNotNull(test.getId());
		assertEquals("Test1",test.getNombre());
		assertNull(test.getCode());
		
		test.setCode(CODIGO_1);
		test=pfcTestService.actualizar(test);
		assertNotNull(test);
		assertNotNull(test.getId());
		assertEquals("Test1",test.getNombre());
		assertNotNull(test.getCode());
		
	}
	

}
