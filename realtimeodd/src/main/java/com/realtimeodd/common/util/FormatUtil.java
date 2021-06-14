package com.realtimeodd.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
		
	public static String getLocalTime() {  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Date date = new Date();  
	    return formatter.format(date);
	}  
	
	public static String getDecimalValue(Double value) {  
	    DecimalFormat decimalFormat = new DecimalFormat("#.00");  
	    return decimalFormat.format(value);
	}  
}
