package org.atiperu.hilos;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.atiperu.bean.Campania;
import org.atiperu.bean.DetalleCampania;
import org.atiperu.bean.HistorialEnviosSms;
import org.atiperu.bean.ParanSubmit;
import org.atiperu.db.Serviciodb;
import org.atiperu.smpp.SmppService;

public class Proceso extends Thread {

	private boolean stopped = false;
	private final static Logger log = Logger.getLogger(Proceso.class);
	private Serviciodb db;
	private SmppService smpp;

	public Proceso(Serviciodb db, SmppService smpp) {
		this.db = db;
		this.smpp = smpp;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!stopped) {
			try {
				Thread.sleep(1000);
				log.info("buscando campaias activas...");
				List<Campania> lista = this.listarCampanias();

				if (lista.size() != 0) {

					this.mostrarConfiguracionListas(lista);

					this.cargarDetalle(lista);

					this.enviandoSMS(lista);
				}

			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	void enviandoSMS(List<Campania> lista) {

		for (Campania campania : lista) {

			this.cambiarEstadoCampaniaEjecutando(campania.getId_campania(), Campania.EJECUTANDO);

			for (DetalleCampania abonados : campania.getListaNumeros()) {

				ParanSubmit sms = new ParanSubmit();
				sms.setDesctNpi((byte) 0);
				sms.setDesctTon((byte) 0);
				sms.setSenderAddress(SmppService.SHORTNUMBER);
				sms.setDestAddress(abonados.getNumero());
				sms.setSenderNpi((byte) 0);
				sms.setSenderTon((byte) 0);
				sms.setShortMessage(abonados.getMensaje());
				sms.setRegisteredDelivery((byte) 0);

				try {

					HistorialEnviosSms his = smpp.Submit(sms);
					his.setReintento(0);
					his.setId_campania(campania.getId_campania());
					db.insertarHistorialEnviosSms(his);

				} catch (ConnectException e) {
					log.error("smsc " + e.getMessage());
				} catch (Exception e) {
					log.error("smsc " + e.getMessage(), e);
				}
			}

			if (campania.getNumero_reintentos() > 0) {
				envioSmsReintentos(campania);
				log.info("finalizacion de reintentos. cantidad de reintentos: "
						+ campania.obtenerReintentosEjecutados());
			}

			this.cambiarEstadoCampaniaEjecutando(campania.getId_campania(), Campania.CONCLUIDO);

		}
	}

	void envioSmsReintentos(Campania campania) {
		campania.reintentoMasUno();
		log.info("iniciando reintento (" + campania.obtenerReintentosEjecutados() + ").");
		List<HistorialEnviosSms> fallidos = obtenerListaHistorica(campania.getId_campania(),
				HistorialEnviosSms.FALLIDOS);
		log.info("cantidad de mensajes fallidos : (" + fallidos.size() + ")");

		for (HistorialEnviosSms fallido : fallidos) {

			ParanSubmit sms = new ParanSubmit();

			sms.setDesctNpi((byte) 0);
			sms.setDesctTon((byte) 0);
			sms.setSenderAddress(SmppService.SHORTNUMBER);
			sms.setDestAddress(fallido.getNumero());
			sms.setSenderNpi((byte) 0);
			sms.setSenderTon((byte) 0);
			sms.setShortMessage(fallido.getMensaje());
			sms.setRegisteredDelivery((byte) 0);

			try {

				HistorialEnviosSms his = smpp.Submit(sms);
				his.setReintento(campania.obtenerReintentosEjecutados());
				his.setId_campania(campania.getId_campania());
				db.insertarHistorialEnviosSms(his);

			} catch (ConnectException e) {
				log.error("smsc " + e.getMessage());
			} catch (Exception e) {
				log.error("smsc " + e.getMessage(), e);
			}

		}

		if (campania.obtenerReintentosEjecutados() < campania.getNumero_reintentos()) {
			envioSmsReintentos(campania);
		}

	}

	List<HistorialEnviosSms> obtenerListaHistorica(int id_campania, boolean estado) {

		List<HistorialEnviosSms> fallidos = null;

		try {
			fallidos = db.obtenerListaHistorica(id_campania, estado);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("db " + e.getMessage());
		}
		return fallidos;
	}

	void cambiarEstadoCampaniaEjecutando(int id_campania, int estdo) {
		try {
			db.cambiarEstadoCampaniaEjecutando(id_campania, estdo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("db " + e.getMessage());
		}
	}

	void cargarDetalle(List<Campania> lista) {
		// TODO Auto-generated method stub
		for (Campania campania : lista) {

			try {
				log.info("cargando lista de abonados de la campania " + campania.getNombre() + "...");
				if (campania.isFlag_lista_negra()) {
					campania.setListaNumeros(db.obtenerDetalleCampaniaConListaNegra(campania.getId_campania()));
					campania.getListaNumeros().size();
				} else {
					campania.setListaNumeros(db.obtenerDetalleCampaniaSinListaNegra(campania.getId_campania()));
				}
				log.info("carga finalizada, cantidad de abonados (" + campania.getListaNumeros().size() + ")");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

	}

	List<Campania> listarCampanias() {
		List<Campania> lista = new ArrayList<>();
		try {
			lista = db.listarCampania(new Date());
			log.info("campañas encontradas: " + lista.size());
		} catch (Exception e) {
			log.error("db " + e.getMessage());
		}
		return lista;
	}

	void mostrarConfiguracionListas(List<Campania> lista) {

		log.info("mostrando configurcion de campanias..");
		for (Campania campania : lista) {
			log.info(campania.toString());
		}

	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		this.stopped = false;
		super.start();
	}

	public boolean isStopped() {
		return stopped;
	}

	public void detener() {
		this.stopped = true;
		db.close();
	}

}
