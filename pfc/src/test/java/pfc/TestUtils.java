package pfc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uned.dlr.pfc.model.User;
import uned.dlr.pfc.service.UserServiceIF;
import uned.dlr.pfc.util.JSLintExecutor;

public class TestUtils {

	static ClassPathXmlApplicationContext context;
	final String CODIGO="var a;function a(){is =0;}";
	
	@BeforeClass
	public  static void setUpBeforeClass() throws Exception {
		//context = new ClassPathXmlApplicationContext("applicationContext_test.xml");
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	
	}

	@Test
	public void testJsLInt() {
		JSLintExecutor lint=new JSLintExecutor();
		String resultado=lint.execute(CODIGO);
		System.out.println(resultado);
		
	}


}
