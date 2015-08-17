package uned.dlr.pfc.util;


import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.ScriptableObject;
import sun.org.mozilla.javascript.internal.annotations.JSFunction;


public class Executor implements ExecutorIF {

	public String execute(String code) {
		 PfcEngine pfc =new PfcEngine();
		 Context cx = Context.enter();
		  // Set version to JavaScript1.2 so that we get object-literal style
         // printing instead of "[object Object]"
         cx.setLanguageVersion(Context.VERSION_1_2);
         cx.initStandardObjects(pfc);
         String[] names = {"log","version"};
         pfc.defineFunctionProperties(names, PfcEngine.class, ScriptableObject.DONTENUM);
	
		 Object result = cx.evaluateString(pfc, code,"MySource", 1, null);
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

}
