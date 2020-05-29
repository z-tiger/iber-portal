package com.iber.portal.util.config;

import com.iber.portal.util.PropertyUtil;


public class WXConfig {
	  //appid
    //请同时修改  androidmanifest.xml里面，.PayActivityd里的属性<data android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
  public static final String APP_ID = PropertyUtil.getString("wx.app.id");
  //商户号
   public static final String MCH_ID = PropertyUtil.getString("wx.mch.id");
//  API密钥，在商户平台设置
    public static final  String API_KEY=PropertyUtil.getString("wx.api.key");
    
    public static final String  RETURN_KEY_APPID =  "appid";
    public static final String  RETURN_KEY_FEE_TYPE =  "fee_type";
    public static final String  RETURN_KEY_NONCE_STR =  "nonce_str";
    public static final String  RETURN_KEY_OUT_TRADE_NO =  "out_trade_no";
    public static final String  RETURN_KEY_TRANSACTION_ID =  "transaction_id";
    public static final String  RETURN_KEY_TRADE_TYPE =  "trade_type";
    public static final String  RETURN_KEY_RESULT_CODE =  "result_code";
    public static final String  RETURN_KEY_SIGN =  "sign";
    public static final String  RETURN_KEY_MCH_ID =  "mch_id";
    public static final String  RETURN_KEY_TOTAL_FEE =  "total_fee";
    public static final String  RETURN_KEY_TIME_END =  "time_end";
    public static final String  RETURN_KEY_OPENID =  "openid";
    public static final String  RETURN_KEY_BANK_TYPE =  "bank_type";
    public static final String  RETURN_KEY_RETURN_CODE =  "return_code";
    public static final String  RETURN_KEY_CASH_FEE =  "cash_fee";
}
