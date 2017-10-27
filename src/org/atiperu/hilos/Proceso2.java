package org.atiperu.hilos;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.atiperu.bean.Campania;
import org.atiperu.bean.DetalleCampania;
import org.atiperu.bean.HistorialEnviosSms;
import org.atiperu.db.Serviciodb;
import org.atiperu.smpp.SmppService;

public class Proceso2 extends Thread {

	private boolean stopped = false;
	private final static Logger log = Logger.getLogger(Proceso2.class);
	private Serviciodb db;
	private SmppService smpp;

	public Proceso2(Serviciodb db, SmppService smpp) {
		this.db = db;
		this.smpp = smpp;
	}

	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		super.run();

		while (!stopped) {
			try {

				Thread.sleep(1000);
				log.info("buscando campaias activas...");
				List<Campania> lista = this.listarCampanias();

				if (lista.size() != 0) {
					this.mostrarConfiguracion(lista);
					this.cargarDetalle(lista);
					this.enviarSMS(lista);
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	private void enviarSMS(List<Campania> lista) {
		// TODO Auto-generated method stub
		for (Campania campania : lista) {
			this.cambiarEsado(campania.getId_campania(), Campania.EJECUTANDO);
			try {
				GrupoHilos grupo = new GrupoHilos(5);

				for (int i = 0; i < 5; i++) {
					grupo.asignando(new ProcesoEnvioSms(smpp, db, campania,0));
				}
				grupo.completando();

			} catch (Exception e) {
				// TODO: handle exception
				log.error("smsc " + e.getMessage());
			}
			
			
			
			if (campania.getNumero_reintentos() > 0) {
				reintento(campania);
				int cantidadDeReintentos = campania.obtenerReintentosEjecutados();
				log.info("campañia ("+ campania.getNombre() +"), finalizacion de reintentos. cantidad de reintentos: " + cantidadDeReintentos);
			}
			
			log.info("fin de envio de la campañia ("+ campania.getNombre() +")...");
			this.cambiarEsado(campania.getId_campania(), Campania.CONCLUIDO);
		}
	}

	private void reintento(Campania campania) {
		// TODO Auto-generated method stub
		campania.reintentoMasUno();
		log.info("campañia ("+ campania.getNombre() +"), iniciando reintento numero (" + campania.obtenerReintentosEjecutados() + ").");
		log.info("campañia ("+ campania.getNombre() +"), buscando mensajes fallidos de la campaña...");
		List<DetalleCampania> fallidos = obtenerMensajesFallidos(campania.getId_campania(),HistorialEnviosSms.FALLIDOS, campania.obtenerReintentosEjecutados() - 1);
		campania.setListaNumeros(fallidos);
		campania.setCola(new LinkedList<>(fallidos));
		log.info("campañia ("+ campania.getNombre() +"), cantidad de mensajes fallidos encontrados : (" + fallidos.size() + ")");
		
		try {
			GrupoHilos grupo = new GrupoHilos(5);
			
			for (int i = 0; i < 5; i++) {
				grupo.asignando(new ProcesoEnvioSms(smpp, db, campania,campania.obtenerReintentosEjecutados()));
			}
			grupo.completando();
		}catch(Exception e) {
			log.error("smsc "+e.getMessage());
		}
		
		if (campania.obtenerReintentosEjecutados() < campania.getNumero_reintentos()) {
			reintento(campania);
		}
		
	}

	private  List<DetalleCampania> obtenerMensajesFallidos(int id_campania, boolean estado, int numeroReintento) {
		// TODO Auto-generated method stub
		List<DetalleCampania> fallidos = null;

		try {
			fallidos = db.obtenerListaHistorica2(id_campania, estado, numeroReintento);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("db " + e.getMessage());
		}
		return fallidos;
	}

	private void cambiarEsado(int id_campania, int ejecutando) {
		// TODO Auto-generated method stub
		try {
			db.cambiarEstadoCampaniaEjecutando(id_campania, ejecutando);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("db " + e.getMessage());
		}
	}

	private void cargarDetalle(List<Campania> lista) {
		// TODO Auto-generated method stub

		for (Campania campania : lista) {

			try {
				log.info("campañia ("+ campania.getNombre() +"), cargando lista de abonados de la campania " + campania.getNombre() + "...");
				if (campania.isFlag_lista_negra()) {
					List<DetalleCampania> abonados = db.obtenerDetalleCampaniaConListaNegra(campania.getId_campania());
					campania.setListaNumeros(abonados);
					campania.setCola(new LinkedList<>(abonados));
				} else {
					List<DetalleCampania> abonados = db.obtenerDetalleCampaniaSinListaNegra(campania.getId_campania());
					campania.setListaNumeros(abonados);
					campania.setCola(new LinkedList<>(abonados));
				}
				log.info("campañia ("+ campania.getNombre() +"), carga finalizada, cantidad de abonados (" + campania.getListaNumeros().size() + ")");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

	}

	private void mostrarConfiguracion(List<Campania> lista) {
		// TODO Auto-generated method stub
		log.info("mostrando configuracion de campaña...");
		for (Campania campania : lista) {
			log.info(campania.toString());
		}

	}

	List<Campania> listarCampanias() {

		List<Campania> lista = new ArrayList<Campania>();

		try {
			lista = db.listarCampania(new Date());
			log.info("campañas encontradas: " + lista.size());
		} catch (Exception e) {
			log.error("db " + e.getMessage());
		}
		return lista;
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
		smpp.close();
		db.close();
	}

}



