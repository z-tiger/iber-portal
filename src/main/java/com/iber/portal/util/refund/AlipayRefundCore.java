package com.iber.portal.util.refund;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.httpclient.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.iber.portal.util.AlipayCore;
import com.iber.portal.util.config.AlipayConfig;
import com.iber.portal.util.httpClient.HttpProtocolHandler;
import com.iber.portal.util.httpClient.HttpRequest;
import com.iber.portal.util.httpClient.HttpResponse;
import com.iber.portal.util.httpClient.HttpResultType;
import com.iber.portal.util.sign.RSA;

/**
 * 支付宝免密码退款
 * @author wrong
 *
 */
public class AlipayRefundCore {
	
	public static void main(String[] args) throws Exception {
		Random r = new Random();
		String batchNo = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + r.nextInt(9999);
		String tradeNo = "2016062921001004240287050621";
		String mny = "0.04";
		String notifyUrl = "http://cdfkinjsom.proxy.qqbrowser.cc:8000/refund_fastpay_by_platform_nopwd-JAVA-GBK/servlet/NotifyUrlServlet";
		AlipayRefundCore core = new AlipayRefundCore(batchNo, tradeNo, mny, notifyUrl);
		String s = core.alipayRefund();
		System.out.println(s);
	}
	
	private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
	
	private String batchNo;
	private String batchNum;
	private String detailData;
	private String notifyUrl;
	
	/**
	 * 支付宝退款
	 * @param batchNo 批次号    格式为：退款日期（8 位当天 日期yyyyMMddHHmmss）+流水号（3～24 位， 流水号可以接受数字或英文 字符，建议使用数字，但不 可接受“000”）
	 * @param tradeNo  原银行流水号
	 * @param mny 退款金额（单位元，只能允许2位小数，如0.01元）
	 * @param notifyUrl 异步通知地址URL
	 */
	public AlipayRefundCore(String batchNo, String tradeNo, String mny, String notifyUrl) {
		this.batchNo = batchNo;
		this.batchNum = "1";
		this.detailData = tradeNo + "^" + mny + "^退款";
		this.notifyUrl = notifyUrl;
	}
	
	
	public String alipayRefund() throws Exception{
		Map<String, String> parasMap = getRequestParams();
		String xml = buildRequest("", "", parasMap);
		return dom4jParse(xml).get("is_success");
	}

	/**
	 * 获取当前系统时间，为退款请求时间
	 * @return
	 */
	private String getNowTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 请求数据
	 * @return
	 */
	private Map<String, String> getRequestParams(){
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
        sParaTemp.put("partner", AlipayConfig.PARTNER);
        sParaTemp.put("_input_charset", AlipayConfig.INPUT_CHARSET);
		sParaTemp.put("notify_url", notifyUrl);
		sParaTemp.put("dback_notify_ url", "http://183.239.183.44:18888/services/i/refund_fastpay_by_platform_nopwd-JAVA-GBK/servlet/DBNotifyServlet");
		sParaTemp.put("batch_no", batchNo);
		sParaTemp.put("refund_date", getNowTime());
		sParaTemp.put("batch_num", batchNum);
		sParaTemp.put("detail_data", detailData);
		return sParaTemp;
	}
	
	/**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	private  String buildRequestMysign(Map<String, String> sPara) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.SIGN_TYPE.equals("RSA") ){
        	mysign = RSA.sign(prestr, AlipayConfig.PRIVATE_KEY, AlipayConfig.INPUT_CHARSET);
        }
        return mysign;
    }
	
	/**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private  Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.SIGN_TYPE);

        return sPara;
    }
    
    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    private  String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW
                      + "_input_charset=" + AlipayConfig.INPUT_CHARSET + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }
    
   
    
    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果
     * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值
     * 如：buildRequest("", "",sParaTemp)
     * @param strParaFileName 文件类型的参数名
     * @param strFilePath 文件路径
     * @param sParaTemp 请求参数数组
     * @return 支付宝处理结果
     * @throws Exception
     */
    private  String buildRequest(String strParaFileName, String strFilePath,Map<String, String> sParaTemp) throws Exception {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.STRING);
        //设置编码集
        request.setCharset(AlipayConfig.INPUT_CHARSET);

        request.setParameters(generatNameValuePair(sPara));
        request.setUrl(ALIPAY_GATEWAY_NEW+"_input_charset="+AlipayConfig.INPUT_CHARSET);
        
        HttpResponse response = httpProtocolHandler.execute(request,strParaFileName,strFilePath);
        if (response == null) {
            return null;
        }
        
        String strResult = response.getStringResult();	

        return strResult;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private  NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }
    
    /**
     * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数
     * 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     * @return 时间戳字符串
     * @throws IOException
     * @throws DocumentException
     * @throws MalformedURLException
     */
    private  String query_timestamp() throws MalformedURLException,
                                                        DocumentException, IOException {

        //构造访问query_timestamp接口的URL串
        String strUrl = ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + AlipayConfig.PARTNER + "&_input_charset" +AlipayConfig.INPUT_CHARSET;
        StringBuffer result = new StringBuffer();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());

        List<Node> nodeList = doc.selectNodes("//alipay/*");

        for (Node node : nodeList) {
            // 截取部分不需要解析的信息
            if (node.getName().equals("is_success") && node.getText().equals("T")) {
                // 判断是否有成功标示
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
                for (Node node1 : nodeList1) {
                    result.append(node1.getText());
                }
            }
        }

        return result.toString();
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
    
}
