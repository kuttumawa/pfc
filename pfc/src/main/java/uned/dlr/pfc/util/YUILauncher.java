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
public class YUILauncher {

	public static void main(String[] args) throws IOException {
		executeCommand("src/test/java/jsscripts/prueba1.js");
		}
	
	public static void executeCommand(String jsFile) throws IOException{
		    String[] cmndJSLINT = {"java", "-jar", "lib/yuicompressor-2.4.8.jar "};
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
	            System.out.println(line);
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
	
	

}
