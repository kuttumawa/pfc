package uned.dlr.pfc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;



public class YUIExecutor implements ExecutorIF {

	private static final Logger log = Logger.getLogger(YUIExecutor.class);
	int linebreakpos = 0;
    boolean verbose = false;
	boolean disableOptimizations = false;
	boolean preserveAllSemiColons = false;
	boolean munge = false;
	String localFilename=null;;

	@Override
	public String execute(String code, String name) {
		 InputStream stream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
		 Reader  in = new InputStreamReader(stream);
		 try {
			JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {

			     public void warning(String message, String sourceName,
			             int line, String lineSource, int lineOffset) {
			        
					System.err.println("\n[WARNING] in " + localFilename);
			         if (line < 0) {
			             System.err.println("  " + message);
			         } else {
			             System.err.println("  " + line + ':' + lineOffset + ':' + message);
			         }
			     }

			     public void error(String message, String sourceName,
			             int line, String lineSource, int lineOffset) {
			         System.err.println("[ERROR] in " + localFilename);
			         if (line < 0) {
			             System.err.println("  " + message);
			         } else {
			             System.err.println("  " + line + ':' + lineOffset + ':' + message);
			         }
			     }

			     public EvaluatorException runtimeError(String message, String sourceName,
			             int line, String lineSource, int lineOffset) {
			         error(message, sourceName, line, lineSource, lineOffset);
			         return new EvaluatorException(message);
			     }
			 });
			in.close(); in = null;
            OutputStreamWriter out=new OutputStreamWriter(new ByteArrayOutputStream()) ;
          
			compressor.compress(out, linebreakpos, munge, verbose,
                    preserveAllSemiColons, disableOptimizations);
		} catch (EvaluatorException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return null;
	}


	@Override
	public String execute(String code) {
		// TODO Auto-generated method stub
		return execute(code,"");
	}
	
	  

}
