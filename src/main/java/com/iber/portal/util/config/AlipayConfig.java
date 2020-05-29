package com.iber.portal.util.config;

import com.iber.portal.util.PropertyUtil;


public class AlipayConfig { 
	public static String PARTNER = PropertyUtil.getString("ali.partner");
	public static final String PRIVATE_KEY = PropertyUtil.getString("ali.private.key");
	public static final String ALI_PUBLIC_KEY = PropertyUtil.getString("ali.public.key");
	public static String INPUT_CHARSET = "utf-8";
	public static String SIGN_TYPE = "RSA";
	public static final String RETURN_KEY_BUYER_ID = "buyer_id";
	public static final String RETURN_KEY_TRADE_NO = "trade_no";
	public static final String RETURN_KEY_BODY = "body";
	public static final String RETURN_KEY_USE_COUPON = "use_coupon";
	public static final String RETURN_KEY_NOTIFY_TIME = "notify_time";
	public static final String RETURN_KEY_SUBJECT = "subject";
	public static final String RETURN_KEY_SIGN_TYPE = "sign_type";
	public static final String RETURN_KEY_IS_TOTAL_FEE_ADJUST = "is_total_fee_adjust";
	public static final String RETURN_KEY_NOTIFY_TYPE = "notify_type";
	public static final String RETURN_KEY_OUT_TRADE_NO = "out_trade_no";
	public static final String RETURN_KEY_GMT_PAYMENT = "gmt_payment";
	public static final String RETURN_KEY_TRADE_STATUS = "trade_status";
	public static final String RETURN_KEY_DISCOUNT = "discount";
	public static final String RETURN_KEY_SIGN = "sign";
	public static final String RETURN_KEY_BUYER_EMAIL = "buyer_email";
	public static final String RETURN_KEY_GMT_CREATE = "gmt_create";
	public static final String RETURN_KEY_PRICE = "price";
	public static final String RETURN_KEY_TOTAL_FEE = "total_fee";
	public static final String RETURN_KEY_QUANTITY = "quantity";
	public static final String RETURN_KEY_SELLER_ID = "seller_id";
	public static final String RETURN_KEY_NOTIFY_ID = "notify_id";
	public static final String RETURN_KEY_SELLER_EMAIL = "seller_email";
	public static final String RETURN_KEY_PAYMENT_TYPE = "payment_type";

}
