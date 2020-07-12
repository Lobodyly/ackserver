package com.manji.ackservice.common.utils;



import lombok.Data;

@Data
public class PcBaseQuery {

	//基础参数
	private String queryStr=""; //查询字段
	private  String indexType; //Buyer Shop
	private int size =20; //每页条数
	private int pageNum =1;//当前页数

}
