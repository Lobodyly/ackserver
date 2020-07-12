package com.manji.ackservice.common.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lutao on 2018/7/4.
 * 定义api的公共返回类
 * {"Code":"1","Data":{"data":[{"id":1,"percetage":0.5,"proportion":0.01}]},"Desc":"success",Edition:null,IsSuccess:true}
 */

public class ApiResultData {

    @JsonProperty("Code")  private int  Code;
    @JsonProperty("Data") private  Object Data;
    @JsonProperty("Desc") private  String Desc;
    @JsonProperty("Edition") private  String Edition;
    @JsonProperty("IsSuccess") private  boolean IsSuccess;

    /**
     * 默认成功构造方法
     */
    public ApiResultData(){
        this.Code =1;
        this.Desc="成功";
        this.Edition="";
        this.IsSuccess=true;
    }

    /**
     * 默认成功构造方法
     */
    public ApiResultData(Integer code, String message){
        this.Code =code;
        this.Desc="成功";
        this.Edition=message;
        this.IsSuccess=false;
    }

    @JsonIgnore
    public int getCode() {
        return Code;
    }

    @JsonIgnore
    public void setCode(int code) {
        Code = code;
    }

    @JsonIgnore
    public Object getData() {
        return Data;
    }

    @JsonIgnore
    public void setData(Object data) {
        Data = data;
    }

    @JsonIgnore
    public String getDesc() {
        return Desc;
    }

    @JsonIgnore
    public void setDesc(String desc) {
        Desc = desc;
    }

    @JsonIgnore
    public String getEdition() {
        return Edition;
    }

    @JsonIgnore
    public void setEdition(String edition) {
        Edition = edition;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return IsSuccess;
    }

    @JsonIgnore
    public void setSuccess(boolean success) {
        IsSuccess = success;
    }
}

