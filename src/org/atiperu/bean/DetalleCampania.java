package org.atiperu.bean;

public class DetalleCampania {
	private int id_campania_detalle;
	private int id_campania;
	private String numero;
	private String mensaje;
	
	public int getId_campania_detalle() {
		return id_campania_detalle;
	}
	public void setId_campania_detalle(int id_campania_detalle) {
		this.id_campania_detalle = id_campania_detalle;
	}
	public int getId_campania() {
		return id_campania;
	}
	public void setId_campania(int id_campania) {
		this.id_campania = id_campania;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
