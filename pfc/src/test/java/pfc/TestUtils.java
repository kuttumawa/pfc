package pfc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uned.dlr.pfc.model.User;
import uned.dlr.pfc.service.UserServiceIF;
import uned.dlr.pfc.util.Executor;
import uned.dlr.pfc.util.ExecutorIF;
import uned.dlr.pfc.util.JSLintExecutor;
import uned.dlr.pfc.util.YUIExecutor;

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
	public void testJSExecutor() {
		ExecutorIF jsExecutor=new Executor();
		String resultado=jsExecutor.execute(CODIGO);
		System.out.println(resultado);
		
	}

	@Test
	public void testJsLInt() {
		ExecutorIF lint=new JSLintExecutor();
		String resultado=lint.execute(CODIGO);
		System.out.println(resultado);
		
	}
	@Test
	public void testYUICompressor() {
		ExecutorIF yui=new YUIExecutor();
		String resultado=yui.execute(CODIGO);
		System.out.println(resultado);
		
	}


}
