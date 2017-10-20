package org.atiperu.daemon;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.atiperu.db.Serviciodb;
import org.atiperu.hilos.Proceso;
import org.atiperu.smpp.SmppService;

public class DaemonApp implements Daemon {

	private final static Logger log = Logger.getLogger(DaemonApp.class);
	private static final Serviciodb db = new Serviciodb();
	private static final SmppService smpp = new SmppService();
	Proceso p;

	@Override
	public void init(DaemonContext arg0) throws DaemonInitException, Exception {
		// TODO Auto-generated method stub
		// String[] arg = arg0.getArguments();
		PropertyConfigurator.configure("/opt/smpp_client/etc/log4j.properties");
		log.info("iniciando proceso...");
		p = new Proceso(db, smpp);
	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		p.start();
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		log.info("deteniendo proceso...");
		p.detener();
	}

	@Override
	public void destroy() {
		p = null;
	}

}
