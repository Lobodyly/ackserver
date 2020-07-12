package com.manji.ackservice.Service.kcuservice;

import com.manji.ackservice.common.model.ApiResultData;
import com.manji.ackservice.model.kcumodel.KcuFeedback;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/26
 * Time:20:16
 */
public interface IKcuApiService {

    ApiResultData listHotCategory(String system_code);

    ApiResultData listCategoryByPid(Integer id,String system_code);

    ApiResultData getInformationById(String sessionId,Integer id);

    ApiResultData getKcuFeedbackSet(String system_code);

    ApiResultData addKcuFeedback(String sessionId,KcuFeedback kcuFeedback);

    ApiResultData listKcuSelfService(String system_code);

    ApiResultData listCategoryAll(String system_code);

    ApiResultData listKcuInformationNew(Integer pageNum, Integer pageSize, Integer kcucategoryid,String system_code);

}
