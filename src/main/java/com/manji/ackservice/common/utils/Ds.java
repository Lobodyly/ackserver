package com.manji.ackservice.common.utils;

import com.manji.ackservice.controller.base.BaseModel;
import com.manji.ackservice.controller.base.BaseSearchAddBiz;

/**
 * Created by lutao on 2018/8/4.
 */
public class Ds {

    /**
     * 数据同步 lingsh
     */
    public  void DsInforMation (){
        BaseModel baseModel=new BaseModel();

        try {
            BaseSearchAddBiz.addHotSearchWords(baseModel);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
