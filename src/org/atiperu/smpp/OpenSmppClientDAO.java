package org.atiperu.smpp;

import java.util.Date;

import org.apache.log4j.Logger;
import org.atiperu.bean.HistorialEnviosSms;
import org.atiperu.bean.ParanSubmit;
import org.smpp.Data;
import org.smpp.pdu.SubmitSMResp;

public class OpenSmppClientDAO extends PadreSesion implements IsmppClient {

	private final static Logger log = Logger.getLogger(OpenSmppClientDAO.class);
	
	public OpenSmppClientDAO() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public void AsynSubmit(ParanSubmit sms) throws Exception {
		// TODO Auto-generated method stub
		SmppClient smppClient = super.pedirSesion();
		smppClient.AsynSubmit(sms);
		super.retornarSesion(smppClient);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.closePool();
	}

	@Override
	public HistorialEnviosSms submit(ParanSubmit sms) throws Exception {
		// TODO Auto-generated method stub
		HistorialEnviosSms hes = null;
		SmppClient smppClient = super.pedirSesion();

		try {
			SubmitSMResp resp = smppClient.submit(sms);
			hes = new HistorialEnviosSms();
			hes.setMensaje(sms.getShortMessage());
			hes.setFecha_envio(new Date());
			hes.setNumero(sms.getDestAddress());
			hes.setEstado((resp.getCommandStatus() == 0));

			if (resp.getCommandStatus() == Data.ESME_ROK) {
				log.info(String.format("submit response: comanadId=%08x, status=%08x, sequence=%08x",
						resp.getCommandId(), resp.getCommandStatus(), resp.getSequenceNumber()));
			} else {
				log.error(String.format("submit response: comanadId=%08x, status=%08x, sequence=%08x",
						resp.getCommandId(), resp.getCommandStatus(), resp.getSequenceNumber()));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			super.retornarSesion(smppClient);
		}

		return hes;
	}

}
