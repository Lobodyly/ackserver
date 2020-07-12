package com.manji.ackservice.Service.kcuservice.Impl;

import com.github.pagehelper.PageInfo;
import com.manji.ackservice.Service.kcuservice.IKcuFeedbackSetService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.mapper.kcumapper.KcuFeedbackSetMapper;
import com.manji.ackservice.model.kcumodel.KcuFeedbackSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:15:21
 */
@Service
public class KcuFeedbackSetServiceImpl implements IKcuFeedbackSetService{
    @Autowired
    private KcuFeedbackSetMapper kcuFeedbackSetMapper;

    /**
    * @Description: 设置反馈配置内容
    * @Param: [kcuFeedbackSetlist]
    * @return: com.manji.ackservice.common.model.ResultData
    * @Author: LuoYu
    * @Date: 2018/8/13
    */
    @Override
    public ResultData setKcuFeedbackSet(List<KcuFeedbackSet> kcuFeedbackSetlist) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"设置反馈配置成功");
        if (null != kcuFeedbackSetlist && kcuFeedbackSetlist.size()>0) {
            for (KcuFeedbackSet kcuFeedbackSet : kcuFeedbackSetlist) {
                if (kcuFeedbackSet.getId() == null) {
                    boolean is_success = kcuFeedbackSetMapper.addKcuFeedbackSet(kcuFeedbackSet);
                    if (!is_success) {
                        resultData.setCode(ResultEmuns.ERROR.getCode());
                        resultData.setMessage("添加反馈配置失败");
                        return resultData;
                    }
                } else {
                    boolean is_success = kcuFeedbackSetMapper.updateKcuFeedbackset(kcuFeedbackSet);
                    if (!is_success) {
                        resultData.setCode(ResultEmuns.ERROR.getCode());
                        resultData.setMessage("修改反馈配置失败");
                        return resultData;
                    }
                }
            }
        }
        return resultData;
    }


    /** 
    * @Description: 删除反馈内容配置
     * @Param: [id] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/25 
    */ 
    @Override
    public ResultData deleteKcuFeedbackSet(Integer id) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"删除反馈配置成功");
        boolean is_success = kcuFeedbackSetMapper.deleteKcuFeedbackset(id);
        if (!is_success) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("删除反馈配置失败");
            return resultData;
        }
        return resultData;
    }
    
    /** 
    * @Description: 条件查询反馈内容配置
     * @Param: [kcuFeedbackSet] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/25 
    */ 
    @Override
    public ResultData listKcuFeedbackSet(KcuFeedbackSet kcuFeedbackSet) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询反馈配置成功");
        List<KcuFeedbackSet> kcuFeedbackSetList = kcuFeedbackSetMapper.listKcuFeedbackset(kcuFeedbackSet);
        resultData.push("data",kcuFeedbackSetList);
        return resultData;
    }
}
