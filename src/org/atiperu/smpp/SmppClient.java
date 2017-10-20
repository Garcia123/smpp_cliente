package org.atiperu.smpp;

import java.net.ConnectException;

import org.apache.log4j.Logger;
import org.atiperu.bean.ParanSubmit;
import org.atiperu.bean.SmppConf;
import org.smpp.Data;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.Address;
import org.smpp.pdu.AddressRange;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.UnbindResp;

public class SmppClient {

	private SmppConf conf;
	private AddressRange addressRange;
	private Session session;
	private ReceptorEventosPDU receptor;

	private final static Logger log = Logger.getLogger(SmppClient.class);

	public SmppClient(SmppConf conf) {
		this.conf = conf;
	}

	public boolean isBound() {
		boolean rep = false;
		if (this.session != null) {
			rep = session.isBound();
		}
		return rep;
	}

	public void bind() throws Exception {
		try {

			if (session != null) {
				if (session.isBound())
					return;
			}

			addressRange = new AddressRange();
			addressRange.setTon((byte) conf.getAddTon());
			addressRange.setNpi((byte) conf.getAddNpi());

			BindRequest request = null;
			BindResponse response = null;

			request = new BindTransmitter();

			TCPIPConnection connection = new TCPIPConnection(conf.getIpAdress(), conf.getPort());
			connection.setReceiveTimeout(20 * 1000);
			session = new Session(connection);

			request.setSystemId(conf.getSystemId());
			request.setPassword(conf.getPassword());
			request.setSystemType(conf.getSystemType());
			request.setInterfaceVersion((byte) 0x34);
			request.setAddressRange(addressRange);

			log.info(String.format("Bind transmitter request: systemId=%s, passowrd=%s, systemType=%s,ton=%s,npi=%s",
					conf.getSystemId(), conf.getPassword(), conf.getSystemType(), addressRange.getTon(),
					addressRange.getNpi()));

			// receptor = new ReceptorEventosPDU();
			response = session.bind(request);

			if (response.getCommandStatus() == Data.ESME_ROK) {
				log.info(String.format("bind transmitter response: comanadId=%08x, status=%08x, sequence=%08x",
						response.getCommandId(), response.getCommandStatus(), response.getSequenceNumber()));
			} else {
				log.error(String.format("bind transmitter response: comanadId=%08x, status=%08x, sequence=%08x",
						response.getCommandId(), response.getCommandStatus(), response.getSequenceNumber()));
			}

		} catch (ConnectException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public void bindAsynchronous() throws ConnectException, Exception {
		try {

			if (session != null) {
				if (session.isBound())
					return;
			}

			addressRange = new AddressRange();
			addressRange.setTon((byte) conf.getAddTon());
			addressRange.setNpi((byte) conf.getAddNpi());

			BindRequest request = null;
			BindResponse response = null;

			request = new BindTransmitter();

			TCPIPConnection connection = new TCPIPConnection(conf.getIpAdress(), conf.getPort());
			connection.setReceiveTimeout(20 * 1000);
			session = new Session(connection);

			request.setSystemId(conf.getSystemId());
			request.setPassword(conf.getPassword());
			request.setSystemType(conf.getSystemType());
			request.setInterfaceVersion((byte) 0x34);
			request.setAddressRange(addressRange);

			log.info(String.format("Bind transmitter request: systemId=%s, passowrd=%s, systemType=%s,ton=%s,npi=%s",
					conf.getSystemId(), conf.getPassword(), conf.getSystemType(), addressRange.getTon(),
					addressRange.getNpi()));

			receptor = new ReceptorEventosPDU();
			response = session.bind(request, receptor);

			if (response.getCommandStatus() == Data.ESME_ROK) {
				log.info(String.format("bind transmitter response: comanadId=%08x, status=%08x, sequence=%08x",
						response.getCommandId(), response.getCommandStatus(), response.getSequenceNumber()));
			} else {
				log.error(String.format("bind transmitter response: comanadId=%08x, status=%08x, sequence=%08x",
						response.getCommandId(), response.getCommandStatus(), response.getSequenceNumber()));
			}

		} catch (ConnectException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public void unbind() throws Exception {

		try {

			if (session != null) {
				if (!session.isBound())
					return;
			}

			log.info("unbind request......");
			if (session.getReceiver().isReceiver()) {
				log.info("desenlazando......");
			}

			UnbindResp response = session.unbind();

			if (response.getCommandStatus() == 0) {
				log.info(String.format("unbind response: comandId=%08x, status=%08x, sequence=%08x",
						response.getCommandId(), response.getCommandStatus(), response.getSequenceNumber()));

			} else {
				log.error(String.format("unbind response: comandId=%08x, status=%08x, sequence=%08x",
						response.getCommandId(), response.getCommandStatus(), response.getSequenceNumber()));

			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	public void AsynSubmit(ParanSubmit sms) throws Exception {

		try {

			SubmitSM request = new SubmitSM();
			// SubmitSMResp response;

			request.setSourceAddr(new Address(sms.getSenderTon(), sms.getDesctNpi(), sms.getSenderAddress()));
			request.setDestAddr(new Address(sms.getDesctTon(), sms.getDesctNpi(), sms.getDestAddress()));
			request.setReplaceIfPresentFlag((byte) 0);
			request.setShortMessage(sms.getShortMessage(), Data.ENC_GSM7BIT);
			request.setScheduleDeliveryTime("");
			request.setValidityPeriod("");
			request.setEsmClass((byte) 0);
			request.setProtocolId((byte) 0);
			request.setPriorityFlag((byte) 0);
			request.setRegisteredDelivery(sms.getRegisteredDelivery());
			request.setDataCoding((byte) 0);
			request.setSmDefaultMsgId((byte) 0);

			request.assignSequenceNumber(true);
			// System.out.println("Submit request " + request.debugString());
			log.info(String.format("async submit request: remitente=%s, destino=%s, mensaje=%s, sequence=%08x",
					sms.getSenderAddress(), sms.getDestAddress(), sms.getShortMessage(), request.getSequenceNumber()));

			session.submit(request);
			// System.out.println("Submit response " + response.debugString());

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public SubmitSMResp submit(ParanSubmit sms) throws Exception {

		SubmitSMResp response = null;

		try {

			SubmitSM request = new SubmitSM();

			request.setSourceAddr(new Address(sms.getSenderTon(), sms.getDesctNpi(), sms.getSenderAddress()));
			request.setDestAddr(new Address(sms.getDesctTon(), sms.getDesctNpi(), sms.getDestAddress()));
			request.setReplaceIfPresentFlag((byte) 0);
			request.setShortMessage(sms.getShortMessage(), Data.ENC_GSM7BIT);
			request.setScheduleDeliveryTime("");
			request.setValidityPeriod("");
			request.setEsmClass((byte) 0);
			request.setProtocolId((byte) 0);
			request.setPriorityFlag((byte) 0);
			request.setRegisteredDelivery(sms.getRegisteredDelivery());
			request.setDataCoding((byte) 0);
			request.setSmDefaultMsgId((byte) 0);

			request.assignSequenceNumber(true);
			// System.out.println("Submit request " + request.debugString());
			log.info(String.format("submit request: remitente=%s, destino=%s, mensaje=%s, sequence=%08x",
					sms.getSenderAddress(), sms.getDestAddress(), sms.getShortMessage(), request.getSequenceNumber()));

			response = session.submit(request);
			// System.out.println("Submit response " + response.debugString());

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return response;

	}

	public SmppConf getConf() {
		return conf;
	}

	public void setConf(SmppConf conf) {
		this.conf = conf;
	}

	public AddressRange getAddressRange() {
		return addressRange;
	}

	public void setAddressRange(AddressRange addressRange) {
		this.addressRange = addressRange;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public ReceptorEventosPDU getReceptor() {
		return receptor;
	}

	public void setReceptor(ReceptorEventosPDU receptor) {
		this.receptor = receptor;
	}

}
