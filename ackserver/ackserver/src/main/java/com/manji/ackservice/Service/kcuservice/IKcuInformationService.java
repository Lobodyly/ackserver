package com.manji.ackservice.Service.kcuservice;

import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.kcumodel.KcuInformation;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/23
 * Time:16:24
 */
public interface IKcuInformationService {

    ResultData addKcuInformation(Integer kcucategory_id, String title, String content, String add_user_name, Integer state,String remark,Integer type) throws Exception;

    ResultData updateKcuInformation(Integer id,Integer kcucategory_id, String title, String content, String update_user_name, Integer state,String remark,Integer type) throws Exception;

    ResultData updateState(Integer id,Integer state, String update_user_name) throws Exception;

    ResultData updateType(Integer id,Integer type, String update_user_name) throws Exception;

    ResultData deleteKcuInformation(String ids);

    ResultData getKcuInformationById(Integer id);

    ResultData listKcuInformation(Integer pageNum, Integer pageSize,Integer id,Integer level,Integer state,Integer type,String kcusystem_code,String title);

}
