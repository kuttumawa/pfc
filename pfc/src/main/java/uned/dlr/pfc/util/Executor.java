package uned.dlr.pfc.util;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.ScriptableObject;

import sun.org.mozilla.javascript.internal.annotations.JSFunction;

public class Executor implements ExecutorIF {

	public String execute(String code,String name) {
		 PfcEngine pfc =new PfcEngine();
		 Context cx = Context.enter();
		  // Set version to JavaScript1.2 so that we get object-literal style
         // printing instead of "[object Object]"
         cx.setLanguageVersion(Context.VERSION_1_7);
         cx.initStandardObjects(pfc);
         String[] names = {"log","version"};
         pfc.defineFunctionProperties(names, PfcEngine.class, ScriptableObject.DONTENUM);
	
		 Object result = null;
		 try{
		   result =cx.evaluateString(pfc, code,name, 1, null);
		 }catch(EvaluatorException e){
		   result= evaluatorExceptiontoString(e);
		 }
		 System.out.println("result: " + result);
         return result.toString();
	}
	
	private class PfcEngine extends ScriptableObject{
		
	    StringBuffer sb=new StringBuffer();
		@JSFunction
		public String version(){
			return "v1.3.3 DLR 2014\n";
		}
		@JSFunction
		public String log(String s){
			sb.append(s==null|s.equalsIgnoreCase("undefined")?"":s).append("\n");
			return sb.toString();
		}

		@Override
		public String getClassName() {
			return this.getClassName();
		}
	}
	public String evaluatorExceptiontoString(EvaluatorException e) {
		StringBuilder builder = new StringBuilder();
		builder.append("ERROR: [linea:");
		builder.append(e.lineNumber());
		builder.append(", columna:");
		builder.append(e.columnNumber());
		builder.append("] ");
		builder.append(e.getMessage());
		
		return builder.toString();
	}

}