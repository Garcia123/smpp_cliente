package org.atiperu.smpp;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.atiperu.bean.SmppConf;

public class PoolSession implements PooledObjectFactory<SmppClient> {

	private SmppConf conf;

	public PoolSession(SmppConf conf) {
		// TODO Auto-generated constructor stub
		this.conf = conf;
	}

	public PooledObject<SmppClient> wrap(SmppClient smppClent) {
		return new DefaultPooledObject<SmppClient>(smppClent);
	}

	public SmppClient creater() throws Exception {

		SmppClient smppClient;
		try {
			smppClient = new SmppClient(conf);
			// smppClient.bindAsynchronous();
			smppClient.bind();

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return smppClient;
	}

	@Override
	public void activateObject(PooledObject<SmppClient> obj) throws Exception {
		// TODO Auto-generated method stub
		SmppClient smppClient = obj.getObject();
		boolean enlasado = smppClient.isBound();
		if (enlasado) {
			// smppClient.bindAsynchronous();
			smppClient.bind();
		}

	}

	@Override
	public void destroyObject(PooledObject<SmppClient> obj) throws Exception {
		// TODO Auto-generated method stub
		SmppClient smppClient = obj.getObject();
		smppClient.unbind();
	}

	@Override
	public PooledObject<SmppClient> makeObject() throws Exception {
		// TODO Auto-generated method stub
		return wrap(creater());
	}

	@Override
	public void passivateObject(PooledObject<SmppClient> obj) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean validateObject(PooledObject<SmppClient> obj) {
		// TODO Auto-generated method stub
		SmppClient smppClient = obj.getObject();
		return smppClient != null;
	}

	public SmppConf getConf() {
		return conf;
	}

	public void setConf(SmppConf conf) {
		this.conf = conf;
	}

}
