package com.manji.ackservice.Service.kcuservice;

import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.kcumodel.KcuFeedback;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:14:28
 */
public interface IKcuFeedbackService {


    ResultData listKcuFeedback(Integer pageNum, Integer pageSize,String kcuinformation_title,Integer state,String system_code,String feedback_content,String start_time,String end_time,Integer kcuinformation_id);
}
