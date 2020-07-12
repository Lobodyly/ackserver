package com.manji.ackservice.common.model;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;


/**
 * 公共返回对象
 * @author Administrator
 *
 */
public class ResultData {

	private String code;

	private String message;
	
	private Map<String,Object> data =new HashMap<String,Object>();

	/**
	 * 默认成功构方法
	 */
	public ResultData(){
		this.code ="0000";
		this.message ="成功";
	}
	
	/**
	 *
	 * @param code
	 * @param message
	 */
	public ResultData(String code, String message){
		
		this.code =code;
		this.message =message;
		
		
	}
	
	public void push(String name,Object obj){
		this.data.put(name, obj);
	}
	
	public String toString(){
		
		return JSON.toJSONString(this);
		
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	
	
	
}
