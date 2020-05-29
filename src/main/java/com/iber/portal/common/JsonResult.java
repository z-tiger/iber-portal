package com.iber.portal.common;

import java.io.Serializable;

/**
 * @author liubiao
 * @create 2018-03-28 14:41
 **/
public class JsonResult<T> implements Serializable{
    private static final long serialVersionUID = -5058014603139698855L;

    public static final int SUCCESS = 1;
    public static final int ERROR =0 ;

    public String message ;
    public int status;
    public T data;

    public JsonResult(){

    }

    public JsonResult(Exception e) {
        this(e.getMessage(), ERROR, null);
    }

    public JsonResult(T data) {
        this("", SUCCESS, data);
    }

    public JsonResult(String erroMessage) {

        this(erroMessage, ERROR, null);
    }

    public JsonResult(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public JsonResult(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public JsonResult(int status, T data) {
        this.status = status;
        this.data = data;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
