package org.atiperu.db;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import org.atiperu.bean.JdbcConf;
import org.atiperu.daemon.DaemonApp;
import org.atiperu.utils.Propiedades;

public class PadreConexion {

	private static GenericObjectPool<Connection> pool;
	private final static Logger log = Logger.getLogger(DaemonApp.class);

	public PadreConexion() {
		pool = this.iniciarPool();
	}

	private GenericObjectPool<Connection> iniciarPool() {

		if (pool == null) {
			JdbcConf conf = ObtenerConfiguracion();
			PoolConnection p = new PoolConnection(conf);
			pool = new GenericObjectPool<Connection>(p);
			pool.setConfig(cantidadPool(10));
		}

		return pool;
	}

	private JdbcConf ObtenerConfiguracion() {
		String ruta = "/opt/smpp_client/etc/db.properties";
		JdbcConf conf = new JdbcConf();
		log.info("cargando archivo properties " + ruta);
		Properties p = Propiedades.importar(ruta);
		conf.setForname(p.getProperty("forname"));
		conf.setUrl(p.getProperty("url"));
		conf.setUser(p.getProperty("user"));
		conf.setPass(p.getProperty("pass"));
		
		return conf;
	}

	private GenericObjectPoolConfig cantidadPool(int cantidad) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(cantidad);
		return config;
	}

	private GenericObjectPool<Connection> obtenerPool() {
		if (pool == null)
			pool = this.iniciarPool();
		return pool;
	}

	public Connection pedirConexion() throws Exception {
		return this.obtenerPool().borrowObject();
	}

	public void retornarConexion(Connection con) {

		try {
			if (con != null)
				this.obtenerPool().returnObject(con);
		} catch (Exception e) {

		}

	}

	protected void closePool() {
		this.obtenerPool().close();
	}

}
