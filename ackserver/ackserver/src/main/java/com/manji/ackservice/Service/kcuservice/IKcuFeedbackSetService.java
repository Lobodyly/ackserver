package com.manji.ackservice.Service.kcuservice;

import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.kcumodel.KcuFeedbackSet;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:15:18
 */
public interface IKcuFeedbackSetService {

    ResultData setKcuFeedbackSet(List<KcuFeedbackSet> kcuFeedbackSet);

    ResultData deleteKcuFeedbackSet(Integer id);

    ResultData listKcuFeedbackSet(KcuFeedbackSet kcuFeedbackSet);
}
