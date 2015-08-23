package uned.dlr.pfc.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.log4j.Logger;

import com.googlecode.jslint4java.Issue;
import com.googlecode.jslint4java.JSLint;
import com.googlecode.jslint4java.JSLintBuilder;
import com.googlecode.jslint4java.JSLintResult;



public class JSLintExecutor implements ExecutorIF {

	private static final Logger log = Logger.getLogger(JSLintExecutor.class);
	private long timeout;
	private File jslintSource;
	private String encoding = "UTF-8";


	@Override
	public String execute(String code, String name) {
		JSLint jsLint = null;
		try {
			jsLint = applyJSlintSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSLintResult res= jsLint.lint(name,code);
		List<Issue> problemas=res.getIssues();
		StringBuilder sb=new StringBuilder();
		for(Issue iss:problemas){
			sb.append(iss).append("\n");
		}
		return sb.toString();
	}
	
	   private JSLint applyJSlintSource() throws Exception {
	        JSLintBuilder builder = new JSLintBuilder();
	        if (timeout > 0) {
	            builder.timeout(timeout);
	        }
	        if (jslintSource != null) {
	            try {
	                return builder.fromFile(jslintSource, Charset.forName(encoding));
	            } catch (IOException e) {
	                throw new Exception("Cant' load jslint.js", e);
	            }
	        } else {
	            return builder.fromDefault();
	        }
	    }

	@Override
	public String execute(String code) {
		return execute(code,"");
	}
	
	static class JsLintResult{
		
		JsLintResult(String line){
			if (line!=null && line.length()>0){
				String[] r=line.split(":");
				fichero=r[1];
				linea=r[2];
				columna=r[3];
				error=r[4];
			}
		}
		String fichero;
		String linea;
		String columna;
		String error;
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("JsLintResult [");
			if (linea != null)
				builder.append("linea=").append(linea).append(", ");
			if (columna != null)
				builder.append("columna=").append(columna).append(", ");
			if (error != null)
				builder.append("error=").append(error);
			builder.append("]");
			return builder.toString();
		}
		
		
	}

}
