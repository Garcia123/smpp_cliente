package org.atiperu.bean;

public class ParanSubmit {
	private String destAddress;
	private String senderAddress;
	private String shortMessage;
	private byte senderTon;
	private byte senderNpi;
	private byte desctTon;
	private byte desctNpi;
	private byte registeredDelivery;
	
	public String getDestAddress() {
		return destAddress;
	}
	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public String getShortMessage() {
		return shortMessage;
	}
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}
	public byte getSenderTon() {
		return senderTon;
	}
	public void setSenderTon(byte senderTon) {
		this.senderTon = senderTon;
	}
	public byte getSenderNpi() {
		return senderNpi;
	}
	public void setSenderNpi(byte senderNpi) {
		this.senderNpi = senderNpi;
	}
	public byte getDesctTon() {
		return desctTon;
	}
	public void setDesctTon(byte desctTon) {
		this.desctTon = desctTon;
	}
	public byte getDesctNpi() {
		return desctNpi;
	}
	public void setDesctNpi(byte desctNpi) {
		this.desctNpi = desctNpi;
	}
	public byte getRegisteredDelivery() {
		return registeredDelivery;
	}
	public void setRegisteredDelivery(byte registeredDelivery) {
		this.registeredDelivery = registeredDelivery;
	}
	
	
	
}
