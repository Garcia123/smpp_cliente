package org.atiperu.hilos;

import java.net.ConnectException;

import org.apache.log4j.Logger;
import org.atiperu.bean.Campania;
import org.atiperu.bean.DetalleCampania;
import org.atiperu.bean.HistorialEnviosSms;
import org.atiperu.bean.ParanSubmit;
import org.atiperu.db.Serviciodb;
import org.atiperu.smpp.SmppService;

public class ProcesoEnvioSms implements Runnable {

	private final static Logger log = Logger.getLogger(ProcesoEnvioSms.class);
	private SmppService smpp;
	private Serviciodb db;
	private Campania campania;
	private int reintento;

	public ProcesoEnvioSms(SmppService smpp, Serviciodb db, Campania campania,int reintento) {
		this.smpp = smpp;
		this.db = db;
		this.campania = campania;
		this.reintento = reintento;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!campania.getCola().isEmpty()) {
			
			DetalleCampania abonado = campania.poll();

			ParanSubmit sms = new ParanSubmit();
			sms.setDesctNpi((byte) 0);
			sms.setDesctTon((byte) 0);
			sms.setSenderAddress(SmppService.SHORTNUMBER);
			sms.setDestAddress(abonado.getNumero());
			sms.setSenderNpi((byte) 0);
			sms.setSenderTon((byte) 0);
			sms.setShortMessage(abonado.getMensaje());
			sms.setRegisteredDelivery((byte) 0);

			try {

				HistorialEnviosSms his = smpp.Submit(sms);
				his.setReintento(reintento);
				his.setId_campania(campania.getId_campania());
				db.insertarHistorialEnviosSms(his);

			} catch (ConnectException e) {
				log.error("smsc " + e.getMessage());
			} catch (Exception e) {
				log.error("smsc " + e.getMessage());
			}

		}

	}

}
