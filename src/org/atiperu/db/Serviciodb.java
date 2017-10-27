package org.atiperu.db;

import java.util.Date;
import java.util.List;

import org.atiperu.bean.Campania;
import org.atiperu.bean.DetalleCampania;
import org.atiperu.bean.HistorialEnviosSms;

public class Serviciodb {

	Factory factory = Factory.getFactory(Factory.POSTGRES);

	IMensajeria mensajeria = factory.getMensajeria();
	IReportes reporte = factory.getReportes();

	public List<Campania> listarCampania(Date fechaActual) throws Exception {
		return mensajeria.listarCampania(fechaActual);
	}

	public List<DetalleCampania> obtenerDetalleCampaniaConListaNegra(int idCampania) throws Exception {
		// TODO Auto-generated method stub
		return mensajeria.obtenerDetalleCampaniaConListaNegra(idCampania);
	}

	public List<DetalleCampania> obtenerDetalleCampaniaSinListaNegra(int idCampania) throws Exception {
		// TODO Auto-generated method stub
		return mensajeria.obtenerDetalleCampaniaSinListaNegra(idCampania);
	}

	public void close() {
		mensajeria.close();
	}

	public void insertarHistorialEnviosSms(HistorialEnviosSms his) throws Exception {
		// TODO Auto-generated method stub
		//reporte.insertarHistorialEnviosSms(his);
		reporte.asyncInsertarHistorialEnviosSms(his);

	}

	public void cambiarEstadoCampaniaEjecutando(int id_campania, int estado) throws Exception {
		// TODO Auto-generated method stub
		mensajeria.cambiarEstadoCampaniaEjecutando(id_campania,estado);
	}

	public List<HistorialEnviosSms> obtenerListaHistorica(int id_campania, boolean estado)  throws Exception {
		// TODO Auto-generated method stub
		return reporte.obtenerListaHistorica(id_campania,estado);
	}

	public List<DetalleCampania> obtenerListaHistorica2(int id_campania, boolean estado,int numeroReintento)throws Exception {
		// TODO Auto-generated method stub
		return reporte.obtenerListaHistorica2(id_campania,estado,numeroReintento);
	}

}
