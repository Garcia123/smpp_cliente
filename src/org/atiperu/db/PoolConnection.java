package org.atiperu.db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.atiperu.bean.JdbcConf;

public class PoolConnection implements PooledObjectFactory<Connection>  {
	
	private JdbcConf jdbc;
	
	public PoolConnection(JdbcConf jdbc) {
		// TODO Auto-generated constructor stub
		this.jdbc = jdbc;
		
	}
	
	public PooledObject<Connection> wrap(Connection con) {
		return new DefaultPooledObject<Connection>(con);
	}
	
	public Connection creater() throws Exception {

		Class.forName(jdbc.getForname());
		Connection con = null;

		try {
			con = DriverManager.getConnection(jdbc.getUrl(),jdbc.getUser(),jdbc.getPass());
		} catch (Exception e) {
			throw e;
		}

		return con;
	}
	
	@Override
	public void activateObject(PooledObject<Connection> obj) throws Exception {
		// TODO Auto-generated method stub
		Connection con = obj.getObject();
		boolean conexionCerrada = con.isClosed();
		if (conexionCerrada) {
			con = DriverManager.getConnection(jdbc.getUrl(),jdbc.getUser(),jdbc.getPass());
		}
	}

	@Override
	public void destroyObject(PooledObject<Connection> obj) throws Exception {
		// TODO Auto-generated method stub
		Connection con = obj.getObject();
		con.close();
	}

	@Override
	public PooledObject<Connection> makeObject() throws Exception {
		// TODO Auto-generated method stub
		return wrap(creater());
	}

	@Override
	public void passivateObject(PooledObject<Connection> obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validateObject(PooledObject<Connection> obj) {
		// TODO Auto-generated method stub
		return obj.getObject() != null;
	}

}
