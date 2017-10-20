package org.atiperu.bean;

import java.util.Date;

public class HistorialEnviosSms {

	private int id_historial_envios_sms;
	private int id_campania;
	private Date fecha_envio;
	private String numero;
	private String mensaje;
	private boolean estado;
	private int reintento;
	
	public static final boolean FALLIDOS = false;
	public static final boolean SUCCESS = true;

	public int getId_historial_envios_sms() {
		return id_historial_envios_sms;
	}

	public void setId_historial_envios_sms(int id_historial_envios_sms) {
		this.id_historial_envios_sms = id_historial_envios_sms;
	}

	public int getId_campania() {
		return id_campania;
	}

	public void setId_campania(int id_campania) {
		this.id_campania = id_campania;
	}

	public Date getFecha_envio() {
		return fecha_envio;
	}

	public void setFecha_envio(Date fecha_envio) {
		this.fecha_envio = fecha_envio;
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

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public int getReintento() {
		return reintento;
	}

	public void setReintento(int reintento) {
		this.reintento = reintento;
	}
	

}
