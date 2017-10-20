package org.atiperu.smpp;

public abstract class Factory {

	public static final int OPENSMPP = 1;
	
	public abstract IsmppClient getSmppClient();
	
	public static Factory getFactory(int tipo) {
		switch (tipo) {
		case OPENSMPP:
			return new OpenFactoryDAO();
		default:
			return null;
		}

	}

}
