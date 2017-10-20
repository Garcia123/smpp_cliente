package org.atiperu.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Campania {

	private int id_campania;
	private int id_tipo;
	private String nombre;
	private Date fecha_hora_inicio;
	private Date fecha_hora_fin;
	private int cantidad_usuarios;
	private boolean flag_lista_negra;
	private int numero_reintentos;
	private int id_usuario;
	private int id_estado;
	private int id_categoria;
	private int id_tipo_sms;
	private List<DetalleCampania> listaNumeros;

	private int reintentosEjecutados = 0;

	public static final int PENDIENTE = 1;
	public static final int APROBADO = 2;
	public static final int EJECUTANDO = 3;
	public static final int CONCLUIDO = 4;

	public int getId_campania() {
		return id_campania;
	}

	public void setId_campania(int id_campania) {
		this.id_campania = id_campania;
	}

	public int getId_tipo() {
		return id_tipo;
	}

	public void setId_tipo(int id_tipo) {
		this.id_tipo = id_tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha_hora_inicio() {
		return fecha_hora_inicio;
	}

	public void setFecha_hora_inicio(Date fecha_hora_inicio) {
		this.fecha_hora_inicio = fecha_hora_inicio;
	}

	public Date getFecha_hora_fin() {
		return fecha_hora_fin;
	}

	public void setFecha_hora_fin(Date fecha_hora_fin) {
		this.fecha_hora_fin = fecha_hora_fin;
	}

	public int getCantidad_usuarios() {
		return cantidad_usuarios;
	}

	public void setCantidad_usuarios(int cantidad_usuarios) {
		this.cantidad_usuarios = cantidad_usuarios;
	}

	public boolean isFlag_lista_negra() {
		return flag_lista_negra;
	}

	public void setFlag_lista_negra(boolean flag_lista_negra) {
		this.flag_lista_negra = flag_lista_negra;
	}

	public int getNumero_reintentos() {
		return numero_reintentos;
	}

	public void setNumero_reintentos(int numero_reintentos) {
		this.numero_reintentos = numero_reintentos;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getId_estado() {
		return id_estado;
	}

	public void setId_estado(int id_estado) {
		this.id_estado = id_estado;
	}

	public int getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}

	public int getId_tipo_sms() {
		return id_tipo_sms;
	}

	public void setId_tipo_sms(int id_tipo_sms) {
		this.id_tipo_sms = id_tipo_sms;
	}

	public List<DetalleCampania> getListaNumeros() {
		if (listaNumeros == null)
			listaNumeros = new ArrayList<DetalleCampania>();
		return listaNumeros;
	}

	public void setListaNumeros(List<DetalleCampania> listaNumeros) {
		this.listaNumeros = listaNumeros;
	}

	public int obtenerReintentosEjecutados() {
		return reintentosEjecutados;
	}

	public void reintentoMasUno() {
		reintentosEjecutados++;
	}

	@Override
	public String toString() {
		return "Campania [id_campania=" + id_campania + ", id_tipo=" + id_tipo + ", nombre=" + nombre
				+ ", fecha_hora_inicio=" + fecha_hora_inicio + ", fecha_hora_fin=" + fecha_hora_fin
				+ ", cantidad_usuarios=" + cantidad_usuarios + ", flag_lista_negra=" + flag_lista_negra
				+ ", numero_reintentos=" + numero_reintentos + ", id_usuario=" + id_usuario + ", id_estado=" + id_estado
				+ ", id_categoria=" + id_categoria + ", id_tipo_sms=" + id_tipo_sms + "]";
	}

}
