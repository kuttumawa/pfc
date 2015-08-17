package uned.dlr.pfc.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * 
 * 
 * Lanzador de comandos.
 *
 */
public class Launcher {

	public static void main(String[] args) throws IOException {
		
		executeCommand("src/test/java/jsscripts/prueba1.js");
		
	
	}
	
	public static void executeCommand(String jsFile) throws IOException{
		    String[] cmndJSLINT = {"java", "-jar", "lib/jslint4java-2.0.4.jar "};
		    ArrayList<String> command=new ArrayList<String>(Arrays.asList(cmndJSLINT));
	       // command.add("--maxerr 500");
	        command.add(jsFile);
	        //You can set up your work directory
	        ProcessBuilder probuilder = new ProcessBuilder( command);
	        //probuilder.directory(new File("src/main/js"));
	       
		       
	        Process process = probuilder.start();
	        
	        //Read out dir output
	        InputStream is = process.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is);
	        BufferedReader br = new BufferedReader(isr);
	        String line;
	        System.out.printf("Output of running %s is:\n",
	                Arrays.toString(command.toArray()));
	        while ((line = br.readLine()) != null) {
	            System.out.println(new JsLintResult(line));
	        }
	        
	        //Wait to get exit value
	        try {
	            int exitValue = process.waitFor();
	            System.out.println("\n\nExit Value is " + exitValue);
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
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
