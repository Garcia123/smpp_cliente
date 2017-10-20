package org.atiperu.smpp;

public class OpenFactoryDAO extends Factory {

	@Override
	public IsmppClient getSmppClient() {
		// TODO Auto-generated method stub
		return new OpenSmppClientDAO();
	}

}
