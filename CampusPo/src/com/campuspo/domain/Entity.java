package com.campuspo.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Entity {
	
	public static final String FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	
	public static final Date parseDate(String dataString) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		
		return sdf.parse(dataString);
	}

}
