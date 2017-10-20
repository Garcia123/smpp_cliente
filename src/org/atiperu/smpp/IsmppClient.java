package org.atiperu.smpp;

import org.atiperu.bean.HistorialEnviosSms;
import org.atiperu.bean.ParanSubmit;

public interface IsmppClient {
	
	public void AsynSubmit(ParanSubmit sms) throws Exception;
	public HistorialEnviosSms submit(ParanSubmit sms)throws Exception;
	public void close();

}
