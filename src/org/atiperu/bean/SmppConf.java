package org.atiperu.bean;

public class SmppConf {
	
	private String ipAdress;
	private int port;
	private String systemId;
	private String password;
	private String systemType;
	private int addTon;
	private int addNpi;
	private int connPool;
	
	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public int getAddTon() {
		return addTon;
	}
	public void setAddTon(int addTon) {
		this.addTon = addTon;
	}
	public int getAddNpi() {
		return addNpi;
	}
	public void setAddNpi(int addNpi) {
		this.addNpi = addNpi;
	}
	
	public int getConnPool() {
		return connPool;
	}
	public void setConnPool(int connPool) {
		this.connPool = connPool;
	}
	@Override
	public String toString() {
		return "SmppConf [ipAdress=" + ipAdress + ", port=" + port + ", systemId=" + systemId + ", password=" + password
				+ ", systemType=" + systemType + ", addTon=" + addTon + ", addNpi=" + addNpi + "]";
	}
	
	
}
