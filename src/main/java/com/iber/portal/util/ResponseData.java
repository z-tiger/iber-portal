package com.iber.portal.util;

import java.util.Map;

public class ResponseData {
	 	private  String message;
	    private  int code;
	    private Map<String, Object> data;

	    public String getMessage() {
	        return message;
	    }

	    public int getCode() {
	        return code;
	    }

	    public Map<String, Object> getData() {
	        return data;
	    }

	    public ResponseData putDataValue(String key, Object value) {
	        data.put(key, value);
	        return this;
	    }

	    public ResponseData(int code, String message) {
	        this.code = code;
	        this.message = message;
	    }

	    public ResponseData(Integer code, String message, Map<String,Object> data){
	    	this.code = code;
	    	this.message = message;
	    	this.data = data;
		}

	    public static ResponseData ok() {
	        return new ResponseData(200, "操作成功");
	    }

	    public static ResponseData notFound() {
	        return new ResponseData(404, "Not Found");
	    }

	    public static ResponseData badRequest() {
	        return new ResponseData(400, "Bad Request");
	    }

	    public static ResponseData forbidden() {
	        return new ResponseData(403, "无效token");
	    }

	    public static ResponseData unauthorized() {
	        return new ResponseData(401, "未经授权的");
	    }

	    public static ResponseData serverInternalError() {
	        return new ResponseData(500, "Server Internal Error");
	    }

	    public static ResponseData paramError() {
	    	return new ResponseData(1000, "参数错误");
	    }
	    
	    public static ResponseData customerError(String message) {
	        return new ResponseData(1001, message);
	    }
	    public static ResponseData customerError(int code, String message) {
	    	return new ResponseData(code, message);
	    }

		@Override
		public String toString() {
			return "ResponseData [message=" + message + ", code=" + code + ", data=" + data + "]";
		}
}
