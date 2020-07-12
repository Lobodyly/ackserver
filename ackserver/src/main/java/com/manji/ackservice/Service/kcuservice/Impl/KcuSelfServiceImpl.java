package com.manji.ackservice.Service.kcuservice.Impl;

import com.alibaba.fastjson.JSON;
import com.manji.ackservice.Service.kcuservice.IKcuSelfService;
import com.manji.ackservice.common.model.ApiResultData;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.mapper.kcumapper.KcuSelfServiceMapper;
import com.manji.ackservice.model.kcumodel.KcuSelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/31
 * Time:11:22
 */
@Service
public class KcuSelfServiceImpl implements IKcuSelfService{
    @Autowired
    private KcuSelfServiceMapper kcuSelfServiceMapper;

    /** 
    * @Description: 自助服务配置 
    * @Param: [kcuSelfService] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/8/7 
    */ 
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultData setKcuSelfService(List<KcuSelfService> model) throws Exception {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"自助服务配置成功");
        String jsontest = JSON.toJSONString(model);
        List<KcuSelfService> kcuSelfServicelist = JSON.parseArray(jsontest,KcuSelfService.class);
        if (null != kcuSelfServicelist && kcuSelfServicelist.size()>0) {
            for (KcuSelfService kcuSelfService : kcuSelfServicelist) {
                KcuSelfService kcuSelfService1 = new KcuSelfService();
                kcuSelfService1.setSystem_code(kcuSelfService.getSystem_code());
                kcuSelfService1.setTitle(kcuSelfService.getTitle());
                Date date = new Date();
                kcuSelfService.setAdd_time(date);
                kcuSelfService.setIs_delete(0);
                List<KcuSelfService> resultlist = kcuSelfServiceMapper.listKcuSelfService(kcuSelfService1);
                if (null == kcuSelfService.getId()) {
                    if (null != resultlist && resultlist.size()>0) {
                        throw new Exception("添加自助服务配置失败,数据库已经存在相同标题自助服务");
                    }
                    boolean is_success = kcuSelfServiceMapper.addKcuSelfService(kcuSelfService);
                    if (!is_success) {
                        throw new Exception("添加自助服务配置失败");
                    }
                } else {
                    if (null != resultlist && resultlist.size()>0) {
                        if (!resultlist.get(0).getId().equals(kcuSelfService.getId())) {
                            throw new Exception("修改自助服务配置失败,数据库已经存在相同标题内容");
                        }
                    }
                    boolean is_success = kcuSelfServiceMapper.updateKcuSelfService(kcuSelfService);
                    if (!is_success) {
                        throw new Exception("修改自助服务配置失败");
                    }
                }
            }
        }
        return resultData;
    }

    /**
    * @Description: 修改自助服务配置的删除状态
    * @Param: [id, is_delete]
    * @return: com.manji.ackservice.common.model.ResultData
    * @Author: LuoYu
    * @Date: 2018/7/31
    */
    @Override
    public ResultData deleteKcuSelfService(Integer id, Integer is_delete) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"修改自助服务配置成功");
        boolean is_success = kcuSelfServiceMapper.deleteKcuSelfService(id,is_delete);
        if (!is_success) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("修改自助服务配置失败");
            return resultData;
        }
        return resultData;
    }

    /**
     * @Description: 查询自助服务
     * @Param: [system_code]
     * @return: com.manji.ackservice.common.model.ApiResultData
     * @Author: LuoYu
     * @Date: 2018/7/31
     */
    @Override
    public ResultData listKcuSelfService() {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询自助服务配置成功");
        KcuSelfService kcuSelfService = new KcuSelfService();
        kcuSelfService.setIs_delete(0);
        List<KcuSelfService> kcuSelfServiceListresult = kcuSelfServiceMapper.listKcuSelfService(kcuSelfService);
        List<Map> kcuSelfServiceList = new ArrayList<>();
        if (null != kcuSelfServiceListresult && kcuSelfServiceListresult.size()>0) {
            for(KcuSelfService kcuSelfService1 : kcuSelfServiceListresult){
                Map<String, Object> taskmap = new HashMap<>();
                taskmap.put("id",kcuSelfService1.getId());
                taskmap.put("title",kcuSelfService1.getTitle());
                taskmap.put("image_url",kcuSelfService1.getImage_url());
                taskmap.put("link_url",kcuSelfService1.getLink_url());
                taskmap.put("type",kcuSelfService1.getType());
                taskmap.put("sort",kcuSelfService1.getSort());
                taskmap.put("system_code",kcuSelfService1.getSystem_code());
                kcuSelfServiceList.add(taskmap);
            }
            Map<String,Object> result = new HashMap<>();
            resultData.push("kcuSelfServiceList",kcuSelfServiceList);
        } else {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("数据库无该系统的自助服务配置内容");
            return resultData;
        }

        return resultData;
    }
}
