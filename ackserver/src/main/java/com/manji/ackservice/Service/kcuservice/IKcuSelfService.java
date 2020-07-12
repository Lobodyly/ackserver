package com.manji.ackservice.Service.kcuservice;

import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.kcumodel.KcuSelfService;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/31
 * Time:11:15
 */
public interface IKcuSelfService {
    ResultData setKcuSelfService(List<KcuSelfService> model) throws Exception;

    ResultData deleteKcuSelfService(Integer id,Integer is_delete);

    ResultData listKcuSelfService();
}
