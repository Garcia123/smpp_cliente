package org.atiperu.db;

import java.util.List;

import org.atiperu.bean.HistorialEnviosSms;

public interface IReportes {

	void insertarHistorialEnviosSms(HistorialEnviosSms obj) throws Exception;
	
	void asyncInsertarHistorialEnviosSms(HistorialEnviosSms obj) throws Exception;

	List<HistorialEnviosSms> obtenerListaHistorica(int id_campania, boolean estado) throws Exception;

}
