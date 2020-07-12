package com.manji.ackservice.Service.kcuservice.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manji.ackservice.Service.kcuservice.IKcuFeedbackService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.mapper.kcumapper.KcuFeedbackMapper;
import com.manji.ackservice.model.kcumodel.KcuFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:14:30
 */
@Service
public class KcuFeedbackServiceImpl implements IKcuFeedbackService{
    @Autowired
    private KcuFeedbackMapper kcuFeedbackMapper;


    /** 
    * @Description: 查询反馈 
    * @Param: [kcuFeedback] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/25 
    */ 
    @Override
    public ResultData listKcuFeedback(Integer pageNum, Integer pageSize,String kcuinformation_title,Integer state,String system_code,String feedback_content,String start_time,String end_time,Integer kcuinformation_id) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询反馈成功");
        PageHelper.startPage(pageNum, pageSize);
        List<KcuFeedback> kcuFeedbackList = kcuFeedbackMapper.listKcuFeedback(kcuinformation_title,state,system_code,feedback_content,start_time,end_time,kcuinformation_id);
        if (null != kcuFeedbackList && kcuFeedbackList.size()>0) {
            PageInfo pageInfo = new PageInfo(kcuFeedbackList);
            resultData.push("data",pageInfo);
        }
        return resultData;
    }
}
