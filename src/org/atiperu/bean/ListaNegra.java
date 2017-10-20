package org.atiperu.bean;

import java.util.List;

public class ListaNegra {
	private int id_cab_lista_negra;
	private String descripcion;
	private boolean estado;
	private List<DetalleListaNegra> lista;
	
	public int getId_cab_lista_negra() {
		return id_cab_lista_negra;
	}
	public void setId_cab_lista_negra(int id_cab_lista_negra) {
		this.id_cab_lista_negra = id_cab_lista_negra;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public List<DetalleListaNegra> getLista() {
		return lista;
	}
	public void setLista(List<DetalleListaNegra> lista) {
		this.lista = lista;
	}
	
}

