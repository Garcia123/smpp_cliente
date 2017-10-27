package org.atiperu.smpp;

import java.util.Properties;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.atiperu.bean.SmppConf;
import org.atiperu.utils.Propiedades;

public class PadreSesion {

	private static GenericObjectPool<SmppClient> pool;

	public PadreSesion() {
		// TODO Auto-generated constructor stub
		pool = this.iniciarPool();
	}

	private GenericObjectPool<SmppClient> iniciarPool() {
		if (pool == null) {
			SmppConf conf = ObtenerConfiguracion();
			PoolSession p = new PoolSession(conf);
			pool = new GenericObjectPool<SmppClient>(p);
			pool.setConfig(cantidadPool(conf.getConnPool()));
		}
		return pool;
	}

	private SmppConf ObtenerConfiguracion() {

		SmppConf conf = new SmppConf();

		Properties p = Propiedades.importar("/opt/smpp_client/etc/smpp.properties");

		conf.setAddNpi(Integer.parseInt(p.getProperty("addNpi")));
		conf.setAddTon(Integer.parseInt(p.getProperty("addTon")));
		conf.setIpAdress(p.getProperty("ipAdress"));
		conf.setPassword(p.getProperty("password"));
		conf.setPort(Integer.parseInt(p.getProperty("port")));
		conf.setSystemId(p.getProperty("systemId"));
		conf.setConnPool(Integer.parseInt(p.getProperty("connPool")));
		conf.setSystemType("");
		return conf;
	}

	private GenericObjectPoolConfig cantidadPool(int cantidad) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(cantidad);
		return config;
	}

	private GenericObjectPool<SmppClient> obtenerPool() {
		if (pool == null)
			pool = this.iniciarPool();
		return pool;
	}

	protected SmppClient pedirSesion() throws Exception {
		return this.obtenerPool().borrowObject();
	}

	protected void retornarSesion(SmppClient smpp) {

		try {
			if (smpp != null)
				this.obtenerPool().returnObject(smpp);
		} catch (Exception e) {

		}

	}

	protected void closePool() {
		this.obtenerPool().close();
	}

}
