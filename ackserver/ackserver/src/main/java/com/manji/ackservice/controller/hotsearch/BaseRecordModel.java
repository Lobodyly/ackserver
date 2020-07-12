package com.manji.ackservice.controller.hotsearch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by lutao on 2018/8/2.
 * 知识库热搜词
 */
@Data
public class BaseRecordModel {

   @ApiModelProperty(value = "关键字内容" ,hidden = true)
    private String content; //关键词内容

     private String indexType; //索引类型 shop   Buyer

    private String device; //设备类型，PC   APP

    private Integer count = 1; //收录次数


    private  Integer pageNum;

    private  Integer pageSize;
}
