package pfc;

import static org.junit.Assert.assertNotNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uned.dlr.pfc.util.ClosureExecutor;
import uned.dlr.pfc.util.Executor;
import uned.dlr.pfc.util.ExecutorIF;
import uned.dlr.pfc.util.JSLintExecutor;

public class TestUtils {

	static ClassPathXmlApplicationContext context;
	final String CODIGO="var a;function a(){is =0;}";
	 String CODIGO_2 =  "function hello(name) {" +
		          "alert('Hello, ' + name);" +
		        "}" +
		        "hello('New user');";
	
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
		assertNotNull(resultado);
		System.out.println(resultado);
		
	}

	@Test
	public void testJsLInt() {
		ExecutorIF lint=new JSLintExecutor();
		String resultado=lint.execute(CODIGO);
		assertNotNull(resultado);
		System.out.println(resultado);
		
	}
	/*@Test
	public void testYUICompressor() {
		ExecutorIF yui=new YUIExecutor();
		String resultado=yui.execute(CODIGO);
		System.out.println(resultado);
		
	}*/
	@Test
	public void testMinificacionConClosure() {
		ExecutorIF closure=new ClosureExecutor();
		String resultado=closure.execute(CODIGO_2);
		assertNotNull(resultado);
		System.out.println(resultado);
		
	}


}
