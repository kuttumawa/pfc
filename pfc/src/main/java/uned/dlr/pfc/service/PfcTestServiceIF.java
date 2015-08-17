package uned.dlr.pfc.service;

import uned.dlr.pfc.model.PfcTest;

public interface PfcTestServiceIF {
	public PfcTest crear(String nombre);
	public void borrar(Long pfcTestId) throws Exception;
	public PfcTest actualizar(PfcTest test);
	

}
