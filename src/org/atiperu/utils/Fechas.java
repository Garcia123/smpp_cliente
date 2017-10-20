package org.atiperu.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;

public class Fechas {

	public static Date sumarRestarSegundos(Date fecha, int segundos) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.SECOND, segundos);
		return calendar.getTime();
	}

	public static String format(Date value) {

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			return format.format(value);
		} catch (Exception e) {
			return null;
		}
	}

	public static Timestamp parseTimestamp(Date fecha) {

		return new Timestamp(fecha.getTime());

	}

}
