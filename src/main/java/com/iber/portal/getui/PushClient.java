package com.iber.portal.getui;

import com.alibaba.fastjson.JSON;
import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.iber.portal.common.SysConstant;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * 类名称：PushClient 
 * 类描述： 个推公共处理类 
 * 创建人：tc 创建时间：2016-3-20 上午11:51:29 
 * 修改人：tc
 * 修改时间：2016-3-20 上午11:51:29 
 * @version
 */
public class PushClient {

	private static final Logger logger = LoggerFactory
			.getLogger(PushClient.class);

	//
	/*static String appId = "6wAovQLX5T6jx8Iyzi7r17";
	static String appkey = "DjSlcR7XK691FV6fm8wUi8";
	static String master = "LcXV4jUjYF7EMSgnsMwj77";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";*/

	static String appId = "E0eMFwZ6Pf6tmO1gvbfmY7";
	static String appkey = "2wcBraAuYh6WPTbKM4KXe7";
	static String master = "mQg12flU976MSLPqngOfT9";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	
	public static void main(String[] args) throws Exception {
////		getUserStatus("3b4381dc11417c989c0851358f7b0a62");
//		List<String> cIdList = queryClientId("粤A4LZ04") ;
//		for(String cid : cIdList) {
//			System.err.println(cid);
//			System.err.println(getUserStatus(cid));
////			push("3b4381dc11417c989c0851358f7b0a62", "{'msg':'test--------------'}");
//		}
		PushCommonBean pushCommonBean = new PushCommonBean(SysConstant.SYS_WARN_LONG_TIME_ORDER, "1", "系统预警消息", "尊敬的会员，您当前的订单已超长，请在6小时内及时充值，否则车辆{0}将被锁定且无法继续使用，给您造成的不便敬请谅解！");
		final String string = JSON.toJSONString(pushCommonBean);
		System.out.println(string);
		push("0e78ddbc714198ae528f8c02493d453a",pushCommonBean);
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
		IGtPush push = new IGtPush(appkey, master,true);
		IAliasResult queryClient = push.queryClientId(appId, alias);
		return queryClient.getClientIdList() ==null ? new ArrayList<String>() : queryClient.getClientIdList();
	}

	/**
	 * 根据CID查询别名
	 * 
	 * @param cId
	 * @throws Exception
	 */
	public static void queryAilas(String cId) throws Exception {
		IGtPush push = new IGtPush(appkey, master,true);
		IAliasResult queryRet = push.queryAlias(appId, cId);
		logger.info("根据cid获取别名：" + queryRet.getAlias());

	}
	
	/**
	 * 根据CID查询别名
	 * 
	 * @param cId
	 * @throws Exception
	 */
	public static String queryAilass(String cId) throws Exception {
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
		IGtPush push = new IGtPush(appkey, master,true);
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
		String pushMessage = JSONObject.fromObject(msg).toString();
		logger.info("推送信息:" + pushMessage);
		IGtPush push = new IGtPush( appkey, master,true);
		// 透传模板
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTransmissionType(2);//1.调转首页 2 当前界面
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