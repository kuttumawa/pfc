package uned.dlr.pfc.util;

/**
 * Create enviroment.
 * Execute js code.
 *
 */
public interface ExecutorIF {
	
	/**
	 * @param code
	 * @return 
	 */
	public String execute(String code,String name);

}
