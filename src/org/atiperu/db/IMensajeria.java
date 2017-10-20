package org.atiperu.db;

import java.util.Date;
import java.util.List;

import org.atiperu.bean.Campania;
import org.atiperu.bean.DetalleCampania;

public interface IMensajeria {

	List<Campania> listarCampania(Date fechaActual) throws Exception;

	List<DetalleCampania> obtenerDetalleCampaniaConListaNegra(int idCampania) throws Exception;

	List<DetalleCampania> obtenerDetalleCampaniaSinListaNegra(int idCampania) throws Exception;
	
	void cambiarEstadoCampaniaEjecutando(int id_campania, int estado) throws Exception;

	void close();

}
