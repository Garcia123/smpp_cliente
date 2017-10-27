package org.atiperu.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.atiperu.bean.DetalleCampania;
import org.atiperu.bean.HistorialEnviosSms;
import org.atiperu.hilos.InsertarHistorialSms;
import org.atiperu.utils.Fechas;

public class PsqlReportesDAO extends PadreConexion implements IReportes {

	private static final QueryRunner run = new QueryRunner();

	@Override
	public void insertarHistorialEnviosSms(HistorialEnviosSms obj) throws Exception {
		// TODO Auto-generated method stub
		Connection con = super.pedirConexion();
		Object[] paran = { obj.getId_campania(), Fechas.parseTimestamp(obj.getFecha_envio()), obj.getNumero(),
				obj.getMensaje(), obj.isEstado(), obj.getReintento() };
		try {
			run.update(con,
					"INSERT INTO reportes.historial_envios_sms(id_campania, fecha_envio, numero, mensaje, estado, reintento )VALUES (?, ?, ?, ?, ?, ?);",
					paran);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			super.retornarConexion(con);
		}

	}

	@Override
	public synchronized void asyncInsertarHistorialEnviosSms(HistorialEnviosSms obj) throws Exception {
		// TODO Auto-generated method stub
		InsertarHistorialSms async = new InsertarHistorialSms(run, this, obj);
		Thread hilo = new Thread(async);
		hilo.start();
	}

	@Override
	public List<HistorialEnviosSms> obtenerListaHistorica(int id_campania, boolean estado) throws Exception {
		// TODO Auto-generated method stub
		Connection con = super.pedirConexion();
		List<HistorialEnviosSms> lista = new ArrayList<>();
		ResultSetHandler<List<HistorialEnviosSms>> h = new BeanListHandler<HistorialEnviosSms>(
				HistorialEnviosSms.class);

		Object[] paran = { id_campania, estado };

		try {
			lista = run.query(con,
					"SELECT id_historial_envios_sms, id_campania, fecha_envio, numero, mensaje, estado, reintento FROM reportes.historial_envios_sms WHERE id_campania = ? AND estado = ?",
					h, paran);
		} catch (Exception e) {
			throw e;
		} finally {
			super.retornarConexion(con);
		}
		return lista;
	}

	@Override
	public List<DetalleCampania> obtenerListaHistorica2(int id_campania, boolean estado, int numeroReintento) throws Exception {
		// TODO Auto-generated method stub
		Connection con = super.pedirConexion();
		List<DetalleCampania> lista = new ArrayList<>();
		ResultSetHandler<List<DetalleCampania>> h = new ResultSetHandler<List<DetalleCampania>>() {

			@Override
			public List<DetalleCampania> handle(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				List<DetalleCampania> lista = new ArrayList<>();

				while (rs.next()) {
					// DetalleCampania
					DetalleCampania d = new DetalleCampania();
					d.setId_campania(rs.getInt(1));
					d.setNumero(rs.getString(2));
					d.setMensaje(rs.getString(3));
					lista.add(d);
				}
				return lista;
			}
		};

		Object[] paran = { id_campania, estado, numeroReintento };
		String sql = "SELECT id_campania, numero, mensaje FROM reportes.historial_envios_sms WHERE id_campania = ? AND estado = ? AND reintento = ?";
		try {
			lista = run.query(con, sql, h, paran);
		} catch (Exception e) {
			throw e;
		} finally {
			super.retornarConexion(con);
		}

		return lista;
	}

}
