package com.manji.ackservice.controller.base;

import lombok.Data;

/**
 * Created by lutao on 2018/8/1.
 * 索引
 */
@Data
public class BaseModel {




    private  String id ;


    private  String title;


    private  String content ;


    private  long add_timestamp;


    private  long upd_timestamp;


    private  String view_number;


     private  String state;


     private  String type;


    private  String indexType;
}
