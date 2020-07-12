package com.manji.ackservice.Service.kcuservice;

import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.kcumodel.KcuCategory;


/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/23
 * Time:16:23
 */
public interface IKcuCategoryService {

    ResultData addKcuCategory(KcuCategory kcuCategory,String system_codelist) throws Exception;

    ResultData updateKcuCategory(KcuCategory kcuCategory,String system_codelist) throws Exception;

    ResultData deleteKcuCategory(Integer id) throws Exception;

    ResultData listKcuCategoryByPidAndSystemCode(Integer pid,String system_code);

    ResultData listKcuCategoryAll(Integer pid,String system_code);

    ResultData getKcuCategory(Integer id);

    ResultData listKcuCategoryByConditions(Integer pageNum, Integer pageSize,Integer pid,String system_code,Integer type,String title);
}
