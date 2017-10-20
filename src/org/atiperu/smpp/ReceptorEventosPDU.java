package org.atiperu.smpp;

import org.apache.log4j.Logger;
import org.smpp.Data;
import org.smpp.ServerPDUEvent;
import org.smpp.ServerPDUEventListener;
import org.smpp.SmppObject;
import org.smpp.pdu.PDU;

public class ReceptorEventosPDU extends SmppObject implements ServerPDUEventListener {

	// Queue requestEvents = new Queue();
	private final static Logger log = Logger.getLogger(ReceptorEventosPDU.class);

	@Override
	public void handleEvent(ServerPDUEvent event) {
		// TODO Auto-generated method stub
		PDU pdu = event.getPDU();
		if (pdu.isResponse()) {
			// log.info("async response received " + pdu.debugString());
			
			if(pdu.getCommandStatus() == Data.ESME_ROK) {
				log.info(String.format("async submit response: comanadId=%08x, status=%08x, sequence=%08x",
						pdu.getCommandId(), pdu.getCommandStatus(), pdu.getSequenceNumber()));
			}else {
				log.error(String.format("async submit response: comanadId=%08x, status=%08x, sequence=%08x",
						pdu.getCommandId(), pdu.getCommandStatus(), pdu.getSequenceNumber()));
			}
			
			
		} else {
			log.info("pdu de clase desconocida " + pdu.debugString());
		}
	}

}
