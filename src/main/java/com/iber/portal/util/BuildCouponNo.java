package com.iber.portal.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * 
 * @author Administrator
 *
 */
public class BuildCouponNo {
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "2", "3", "4", "5","6", "7", 
			"8", "9", "A", "B", "C", "D", "E", "F", "G", "H","J","K", "L", 
			"M", "N", "P", "Q", "R", "S", "T", "U", "V","W", "X", "Y", "Z" };

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "")+UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 12; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x38]);
		}
		return shortBuffer.toString().toUpperCase();

	}
	public static void main(String[] args) {
		BuildCouponNo num=new BuildCouponNo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String batchNo = sdf.format(Calendar.getInstance().getTime());
		
		for(int i=0;i<1000;i++){
			 System.out.println( batchNo+"******************"+num.generateShortUuid());
		}
		
}

}