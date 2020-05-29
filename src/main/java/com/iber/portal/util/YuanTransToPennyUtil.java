package com.iber.portal.util;

import org.apache.commons.lang3.StringUtils;

public class YuanTransToPennyUtil {

	// 用于将数字放大100倍,元转化为分存进数据库
	public static Integer TransToPenny(String money) {
		if (StringUtils.isBlank(money)) {
			return 0;
		} else {
			int index = money.indexOf(".");
			if (-1 != index) { // 不为-1,存在小数点,可能为小数
				String str = money.substring(0, index)
						+ money.substring(index + 1);
				if (StringUtils.isNumeric(str)) {
					Double d = (double) Math.round(Double.valueOf(money) * 100)  ;
					return d.intValue();
				} else {
					return -1;
				}
			} else if (StringUtils.isNumeric(money)) {
				return Integer.valueOf(money) * 100;
			} else {
				return -1;
			}
		}
	}
}
