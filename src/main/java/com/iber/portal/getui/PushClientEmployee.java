package com.iber.portal.getui;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 类描述： 员工端个推公共处理类
 * 创建人：tc 创建时间：2016-3-20 上午11:51:29 
 * 修改人：tc
 * 修改时间：2016-3-20 上午11:51:29 
 * @version
 */
public class PushClientEmployee {

	private static final Logger logger = LoggerFactory
			.getLogger(PushClient.class);
	//
	/*static String appId = "6wAovQLX5T6jx8Iyzi7r17";
	static String appkey = "DjSlcR7XK691FV6fm8wUi8";
	static String master = "LcXV4jUjYF7EMSgnsMwj77";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";*/

	//员工端appid
	static String appId = "6UXin4uqKL9XOv5aBOrWR9";
	static String appkey = "bPmyeDk4lR6F5LUNCzzz33";
	static String master = "LFCGnWCGc8A23IrRubg245";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	//会员appid
	final static String appIdMember = "E0eMFwZ6Pf6tmO1gvbfmY7";
	final static String appkeyMember = "2wcBraAuYh6WPTbKM4KXe7";
	final static String masterMember = "mQg12flU976MSLPqngOfT9";
	final static IGtPush pushMember = new IGtPush(appkeyMember, masterMember,true);

	public static void main(String[] args) throws Exception {
		PushCommonBean pushCommonBean = new PushCommonBean("", "1", "支付失败", null);
		//push("db3cf792e482c2448876b70b04257ec3",pushCommonBean);
		List<String> cidList = PushClient.queryClientId("粤B5Q2W2");
		for (String lpnCid : cidList) {
			System.err.println(lpnCid);
			push(lpnCid,pushCommonBean);
		}

	}

	public static TransmissionTemplate TransmissionTemplateDemo() {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTransmissionType(1);
		template.setTransmissionContent("OS-TOSingle Test");
		return template;
	}

	/**
	 * 别名绑定
	 * 
	 * @param cId
	 *            客户端ID
	 * @param alias
	 *            别名 别名默认为手机号
	 * @throws Exception
	 */
	public static void bindAlias(String cId, String alias) throws Exception {
		IGtPush push = new IGtPush(appkey, master,true);

		IAliasResult bindSCid = push.bindAlias(appId, alias, cId);
		logger.info("绑定结果：" + bindSCid.getResult() + "错误码:"
				+ bindSCid.getErrorMsg());
	}

	/**
	 * 根据别名查询CID
	 * 
	 * @param alias
	 * @throws Exception
	 */
	public static List<String> queryClientId(String alias){

		if (StringUtils.isNumeric(alias)){
			IGtPush push = new IGtPush(appkey, master,true);
			IAliasResult queryClient = push.queryClientId(appId, alias);
			return queryClient.getClientIdList() ==null ? new ArrayList<String>() : queryClient.getClientIdList();
		}else{
			IGtPush push = new IGtPush( appkeyMember, masterMember,true);
			IAliasResult queryClient = push.queryClientId(appIdMember, alias);
			return queryClient.getClientIdList() ==null ? new ArrayList<String>() : queryClient.getClientIdList();
		}

	}

	/**
	 * 根据CID查询别名
	 * 
	 * @param cId
	 * @throws Exception
	 */
	public static void queryAilas(String cId) throws Exception {
		IGtPush push = new IGtPush( appkey, master,true);
		IAliasResult queryRet = push.queryAlias(appId, cId);
		logger.info("根据cid获取别名：" + queryRet.getAlias());

	}
	
	/**
	 * 根据CID查询别名
	 * 
	 * @param cId
	 * @throws Exception
	 */
	public static String queryAilass(String cId)  {
		IGtPush push = new IGtPush(appkey, master,true);
		IAliasResult queryRet = push.queryAlias(appId, cId);
		logger.info("根据cid获取别名：" + queryRet.getAlias());
		return queryRet.getAlias();
	}

	/**
	 * 解除CID的别名绑定
	 * 
	 * @param cId
	 * @param alias
	 * @throws Exception
	 */
	public static void AliasUnBind(String cId, String alias) throws Exception {
		IGtPush push = new IGtPush(appkey, master,true);
		IAliasResult AliasUnBind = push.unBindAlias(appId, alias, cId);
		logger.info("解除绑定结果:" + AliasUnBind.getResult());
	}

	/**
	 * 解除别名下的所有CID绑定
	 * 
	 * @param alias
	 * @throws Exception
	 */
	public static void AliasUnBindAll(String alias) throws Exception {
		IGtPush push = new IGtPush( appkey, master,true);
		IAliasResult AliasUnBindAll = push.unBindAliasAll(appId, alias);
		logger.info("解除绑定结果:" + AliasUnBindAll.getResult());
	}

	/**
	 * 客户端推送信息
	 * 
	 * @param cId
	 * @param msg
	 */
	public static String push(String cId, Object msg) {
		String pushMessage = net.sf.json.JSONObject.fromObject(msg).toString();
		logger.info("推送信息:" + pushMessage);
		String alias = queryAilass(cId);
		IGtPush push ;
		String appId  ;
		String appkey ;
		if (StringUtils.isNumeric(alias)){
			appId = PushClientEmployee.appId ;
			appkey =  PushClientEmployee.appkey  ;
			push = new IGtPush( appkey, master,true);
		}else{
			appId = PushClientEmployee.appIdMember ;
			appkey =  PushClientEmployee.appkeyMember  ;
			push = pushMember;
		}

		// 透传模板
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTransmissionType(2); //1.调转首页 2 当前界面
		template.setTransmissionContent(pushMessage);

		SingleMessage message = new SingleMessage();
		// 设置消息离线
		message.setOffline(true);
		// 离线有效时间，毫秒
		message.setOfflineExpireTime(60 * 1000);
		message.setData(template);
		// message.setPushNetWorkType(1); //是否wifi推送
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(cId);
		try {
			IPushResult ret = push.pushMessageToSingle(message, target);
			logger.info("推送结果" + ret.getResponse().toString());
			return ret.getResponse().get("result").toString() ;
		} catch (RequestException e) {
			String requstId = e.getRequestId();
			IPushResult ret = push.pushMessageToSingle(message, target,
					requstId);
			logger.info("推送结果" + ret.getResponse().toString());
			return ret.getResponse().get("result").toString() ;
		}
	}
	
	public static String getUserStatus(String cid) {
		IGtPush push = new IGtPush(appkey, master,true);
	    IQueryResult abc = push.getClientIdStatus(appId, cid);
	    return String.valueOf(abc.getResponse().get("result"));
	}
}