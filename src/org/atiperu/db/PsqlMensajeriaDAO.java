package org.atiperu.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.atiperu.bean.Campania;
import org.atiperu.bean.DetalleCampania;
import org.atiperu.utils.Fechas;

public class PsqlMensajeriaDAO extends PadreConexion implements IMensajeria {

	private static final QueryRunner run = new QueryRunner();

	@Override
	public void cambiarEstadoCampaniaEjecutando(int id_campania,int estado) throws Exception {
		// TODO Auto-generated method stub
		Connection con = super.pedirConexion();
		Object[] paran = { estado, id_campania };
		try {
			run.update(con, "UPDATE mensajeria.campania SET id_estado=? WHERE (id_campania=?);", paran);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			super.retornarConexion(con);
		}
	}

	@Override
	public List<Campania> listarCampania(Date fechaActual) throws Exception {
		// TODO Auto-generated method stub
		Connection con = super.pedirConexion();
		List<Campania> lista = new ArrayList<>();
		ResultSetHandler<List<Campania>> h = new BeanListHandler<Campania>(Campania.class);

		Date fechaMenos10 = Fechas.sumarRestarSegundos(fechaActual, -2);
		Date fechaMas10 = Fechas.sumarRestarSegundos(fechaActual, 2);

		try {
			lista = run.query(con,
					"SELECT can.id_campania, can.id_tipo, can.nombre, can.fecha_hora_inicio,"
							+ " can.fecha_hora_fin, can.cantidad_usuarios, can.flag_lista_negra,"
							+ " can.numero_reintentos, can.id_usuario, can.id_estado,"
							+ " can.id_categoria, can.id_tipo_sms FROM mensajeria.campania AS can"
							+ " where (can.fecha_hora_inicio between ? and ?) and can.id_estado = 2 ",
					h, new java.sql.Timestamp(fechaMenos10.getTime()), new java.sql.Timestamp(fechaMas10.getTime()));
			
		} catch (Exception e) {
			throw e;
		} finally {
			super.retornarConexion(con);
		}
		return lista;
	}

	@Override
	public List<DetalleCampania> obtenerDetalleCampaniaConListaNegra(int idCampania) throws Exception {

		List<DetalleCampania> detalle = new ArrayList<>();
		ResultSetHandler<List<DetalleCampania>> h = new BeanListHandler<DetalleCampania>(DetalleCampania.class);
		Connection con = super.pedirConexion();

		try {
			detalle = run.query(con,
					"SELECT cdt.numero, cdt.mensaje, cdt.id_campania," + "	cdt.id_campania_detalle FROM"
							+ " mensajeria.campania_detalle AS cdt  where  id_campania = ? AND "
							+ " cdt.numero not in ( SELECT dn.numero FROM mensajeria.det_lista_negra dn "
							+ " WHERE dn. id_cab_lista_negra  = (select  n.id_cab_lista_negra "
							+ " FROM mensajeria.cab_lista_negra n where n.estado = TRUE) )",
					h, idCampania);
		} catch (Exception e) {
			throw e;
		} finally {
			super.retornarConexion(con);
		}
		
		return detalle;
	}

	@Override
	public List<DetalleCampania> obtenerDetalleCampaniaSinListaNegra(int idCampania) throws Exception {
		List<DetalleCampania> detalle = new ArrayList<>();
		ResultSetHandler<List<DetalleCampania>> h = new BeanListHandler<DetalleCampania>(DetalleCampania.class);
		Connection con = super.pedirConexion();

		try {
			detalle = run.query(con,
					" SELECT" + "	cdt.numero," + "	cdt.mensaje," + "	cdt.id_campania,"
							+ "	cdt.id_campania_detalle" + " FROM"
							+ "	mensajeria.campania_detalle AS cdt  where  cdt.id_campania = ?",
					h, idCampania);
		} catch (Exception e) {
			throw e;
		} finally {
			super.retornarConexion(con);
		}

		// TODO Auto-generated method stub
		return detalle;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.closePool();
	}

}
