package uned.dlr.pfc.service;

import uned.dlr.pfc.dao.PfcTestDao;
import uned.dlr.pfc.model.PfcTest;

public class PfcTestServiceImp implements PfcTestServiceIF {

	PfcTestDao pfcTestDao;
	
	@Override
	public PfcTest crear(String nombre) {
		PfcTest pfcTest=new PfcTest(nombre);
		pfcTestDao.save(pfcTest);
		return pfcTestDao.getPfcTest(pfcTest.getId());
	}

	@Override
	public void borrar(Long pfcTestId) throws Exception {
		// TODO Auto-generated method stub

	}

	public PfcTestDao getPfcTestDao() {
		return pfcTestDao;
	}

	public void setPfcTestDao(PfcTestDao pfcTestDao) {
		this.pfcTestDao = pfcTestDao;
	}

	@Override
	public PfcTest actualizar(PfcTest test) {
		Long id=pfcTestDao.save(test);
		return pfcTestDao.getPfcTest(id);
	}

}
