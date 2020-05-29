package com.iber.portal.util.refund;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.DocumentHelper;

import com.iber.portal.util.common.IdGenerationTool;
import com.iber.portal.util.common.RefundReqData;
import com.iber.portal.util.common.WXHttpsRequest;
import com.iber.portal.util.config.WXConfig;


/**
 * 微信退款
 * @author wrong
 *
 */
public class WXRefundCore {
	
	private static String URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	private String transactionId;
	private String outTradeNo;
	private String refundOrderId;
	private int totalFee;
	private int refundFee;
	
	/**
	 * 
	 * @param transactionId  原支付流水号
	 * @param outTradeNo  原订单号
	 * @param refundOrderId  退款订单号，由商户生成，（32位字符组成）
	 * @param totalFee 原支付总价，单位分
	 * @param refundFee 退款总价，单位分，（注意：只能小于等于原支付总价）
	 */
	public WXRefundCore(String transactionId,String outTradeNo, String refundOrderId,
			int totalFee, int refundFee) {
		this.transactionId = transactionId;
		this.outTradeNo = outTradeNo;
		this.refundOrderId = refundOrderId;
		this.totalFee = totalFee;
		this.refundFee = refundFee;
	}

	public Map<String,String> wxRefundForMap(){
		return dom4jParse(wxRefund());
	}
	
	public String wxRefund(){
    	String opUserID = WXConfig.MCH_ID;
    	try {
    		RefundReqData data = new RefundReqData(transactionId, outTradeNo, "", refundOrderId, totalFee, refundFee, opUserID, "");
        	WXHttpsRequest wxHttpsRequest = new WXHttpsRequest();
        	return wxHttpsRequest.sendPost(URL, data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private  Map<String,String> dom4jParse(String protocolXML) {   
		 Map<String,String> map = new HashMap<String, String>();
	        try {   
	        	 org.dom4j.Document doc=(org.dom4j.Document)DocumentHelper.parseText(protocolXML);   
	             org.dom4j.Element books = doc.getRootElement();   
	            // Iterator users_subElements = books.elementIterator("UID");//指定获取那个元素   
	             Iterator   Elements = books.elementIterator();   
	            while(Elements.hasNext()){   
	            	org.dom4j.Element user = (org.dom4j.Element)Elements.next(); 
	            	map.put(user.getName(), user.getText());
	            }   
	         } catch (Exception e) {   
	             e.printStackTrace();   
	         } 
	        return map;
	     }
	 
	
	public static void main(String[] args) {
		String transactionId = "4008322001201606167347050819";
    	String outTradeNo = "C-B-52-1466048569235";
		 String refundOrderId = IdGenerationTool.getGengenerationId();
		 int totalFee = 1;
		 int refundFee = 1;
		 WXRefundCore core = new  WXRefundCore(transactionId,outTradeNo, refundOrderId, totalFee, refundFee);
		 String s = core.wxRefund();
		 System.out.println(s);
	}
}
