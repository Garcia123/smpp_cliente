package org.atiperu.smpp;

import java.util.Properties;

import org.atiperu.bean.HistorialEnviosSms;
import org.atiperu.bean.ParanSubmit;
import org.atiperu.utils.Propiedades;

public class SmppService {

	public static final String SHORTNUMBER = getConfigNumeroCorto();
	Factory factory = Factory.getFactory(Factory.OPENSMPP);

	IsmppClient cliente = factory.getSmppClient();

	public void AsynSubmit(ParanSubmit sms) throws Exception {
		cliente.AsynSubmit(sms);
	}

	public void close() {
		cliente.close();
	}

	public HistorialEnviosSms Submit(ParanSubmit sms) throws Exception {
		// TODO Auto-generated method stub
		return cliente.submit(sms);
	}

	private static String getConfigNumeroCorto() {
		Properties p = Propiedades.importar("/opt/smpp_client/etc/smpp.properties");
		return p.getProperty("shortNumber");
	}
}
