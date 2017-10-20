package org.atiperu.hilos;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.atiperu.bean.HistorialEnviosSms;
import org.atiperu.db.PadreConexion;
import org.atiperu.utils.Fechas;

public class InsertarHistorialSms implements Runnable {

	private QueryRunner run;
	private PadreConexion pool;
	private HistorialEnviosSms obj;

	public InsertarHistorialSms(QueryRunner run, PadreConexion pool, HistorialEnviosSms obj) {
		super();
		this.run = run;
		this.pool = pool;
		this.obj = obj;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		Connection con = null;
		Object[] paran = { obj.getId_campania(), Fechas.parseTimestamp(obj.getFecha_envio()), obj.getNumero(),
				obj.getMensaje(), obj.isEstado(), obj.getReintento() };
		try {
			con = pool.pedirConexion();
			run.update(con,
					"INSERT INTO reportes.historial_envios_sms(id_campania, fecha_envio, numero, mensaje, estado, reintento )VALUES (?, ?, ?, ?, ?, ?);",
					paran);
		} catch (Exception e) {
			// TODO: handle exceptionx
		} finally {
			if (con != null)
				pool.retornarConexion(con);
		}

	}

}
