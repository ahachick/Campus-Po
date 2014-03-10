package com.campuspo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	
	public static void parseStringToDate(String dateString) {
		
	}

	public static String getTimeDiff(Date date) {

		Long diff = (System.currentTimeMillis() - date.getTime()) / 1000 ;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
		
		String str = null;
		
		if (diff > 24 * 60 * 60 ) {
			//System.out.println("1天前");
			str="1天前";
		} else if (diff > 5 * 60 * 60 ) {
			//System.out.println("2小时前");
			str="2小时前";
		} else if (diff > 1 * 60 * 60 ) {
			//System.out.println("1小时前");
			str="1小时前";
		} else if (diff > 30 * 60 ) {
			//System.out.println("30分钟前");
			str="30分钟前";
		} else if (diff > 15 * 60 ) {
			//System.out.println("15分钟前");
			str="15分钟前";
		} else if (diff > 5 * 60 ) {
			//System.out.println("5分钟前");
			str="5分钟前";
		} else if (diff > 1 * 60 ) {
			//System.out.println("1分钟前");
			str="1分钟前";
		}else{
			str="刚刚";
		}	
		
		if(diff >24 * 60 * 60)
			str = sdf.format(date);
	
		return str;
	}
	
	public static String getTimeDiff(Long timeStamp) {

		Long diff = (System.currentTimeMillis() - timeStamp) / 1000 / 60;

		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
		
		Date date = new Date(timeStamp);
		
		String str = sdf.format(date);
	
	
	
		return str;
	}

}
