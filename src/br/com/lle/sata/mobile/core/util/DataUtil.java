package br.com.lle.sata.mobile.core.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataUtil {

	public static int getDiferencaDias(Date dia1, Date dia2) {
		return getDiferencaDias(converteToCalendar(dia1), converteToCalendar(dia2));
	}
	
	public static int getDiferencaDias(Calendar dia1, Calendar dia2) {
		long longDia = dia1.getTimeInMillis();
		long longFechamento = dia2.getTimeInMillis();
		return (int) (((longFechamento - longDia) / (24*60*60*1000)));
	}
	
	public static Date converteToDate(String data) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dataUtil = formatter.parse(data);
		return new Date(dataUtil.getTime()); 
	}
	
	public static Calendar converteToCalendar(Date data) {
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(data);
		return cal;
	}
}
