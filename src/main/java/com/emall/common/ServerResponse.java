package com.emall.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable{
    private T data;
    private String msg;
    private Integer status;

    private ServerResponse() {
    }

    private ServerResponse(T data) {
        this.data = data;
    }

    private ServerResponse(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    private ServerResponse(T data, String msg, int status) {
        this.data = data;
        this.msg = msg;
        this.status = status;
    }
    @JsonIgnore
    public boolean isSuccess(){
       return this.status==ResponseCode.SUCCESS.getCode();
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public static<T> ServerResponse<T> getSuccessServerResponse(T data,String msg){
        return new ServerResponse<T>(data,msg,ResponseCode.SUCCESS.getCode());
    }
    public static<T> ServerResponse<T> getFailServerResponse(T data,String msg){
        return new ServerResponse<T>(data,msg,ResponseCode.FAIL.getCode());
    }
    public static<T> ServerResponse<T> getIllegalServerResponse(T data,String msg){
        return new ServerResponse<T>(data,msg,ResponseCode.Illegal.getCode());
    }
    public static<T> ServerResponse<T> getNoLoginServerResponse(T data,String msg){
        return new ServerResponse<T>(data,msg,ResponseCode.NOLOGIN.getCode());
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                '}';
    }
}
