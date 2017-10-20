package org.atiperu.utils;

import java.io.FileReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Propiedades {

	private final static Logger log = Logger.getLogger(Propiedades.class);

	public static Properties importar(String ruta) {

		Properties p = null;

		try {
			p = new Properties();
			p.load(new FileReader(ruta));
		} catch (Exception e) {
			log.error("ocurrio un error al cargar el archivo properties: " + e.getMessage());
		}

		return p;
	}

}
