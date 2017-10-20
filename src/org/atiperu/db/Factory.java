package org.atiperu.db;

public abstract class Factory {

	public static final int POSTGRES = 1;
	
	public abstract IMensajeria getMensajeria();
	public abstract IReportes getReportes();
	
	public static Factory getFactory(int tipodb) {
		switch (tipodb) {
		case POSTGRES:
			return new PsqlFactoryDAO();
		default:
			return null;
		}
	}
}
