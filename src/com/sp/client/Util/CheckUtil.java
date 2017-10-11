package com.sp.client.Util;

import java.util.Date;
import java.text.SimpleDateFormat;

public class CheckUtil {
	public static boolean bCheckDate(String inputDate) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date date = bartDateFormat.parse(inputDate);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
