package com.iber.portal.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @Author:cuichongc
 * @Version:1.0
 */

public final class ResultMsg {

	static Map<String, Object> msgList = new HashMap<String, Object>();
	
	public static Map<String, Object> succResult(String msg) {
		msgList.put("status", "succ");
		msgList.put("msg", msg);
		return msgList;
	}

	public static Map<String, Object> fialResult(String msg) {
		msgList.put("status", "fail");
		msgList.put("msg", msg);
		return msgList;
	}

	public static void main(String[] args) {
		Map<String, Object> succResult = ResultMsg.succResult("出问题了...，请稍后重试！");
		System.out.println(succResult);
	}

}
