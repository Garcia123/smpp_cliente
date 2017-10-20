package org.atiperu.db;

public class PsqlFactoryDAO extends Factory {

	@Override
	public IMensajeria getMensajeria() {
		// TODO Auto-generated method stub
		return new PsqlMensajeriaDAO();
	}

	@Override
	public IReportes getReportes() {
		// TODO Auto-generated method stub
		return new PsqlReportesDAO();
	}

}
