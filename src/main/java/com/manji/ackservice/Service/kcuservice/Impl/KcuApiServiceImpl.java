package com.manji.ackservice.Service.kcuservice.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manji.ackservice.Service.kcuservice.IKcuApiService;
import com.manji.ackservice.Service.kcuservice.IKcuCategoryService;
import com.manji.ackservice.common.model.ApiResultData;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.datahostService.DataHost;
import com.manji.ackservice.mapper.kcumapper.*;
import com.manji.ackservice.model.kcumodel.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/26
 * Time:20:18
 */
@Service
public class KcuApiServiceImpl implements IKcuApiService{
    @Autowired
    private IKcuCategoryService kcuCategoryService;
    @Autowired
    private KcuInformationMapper kcuInformationMapper;
    @Autowired
    private KcuCategoryMapper kcuCategoryMapper;
    @Autowired
    private KcuFeedbackSetMapper kcufeedbackSetMapper;
    @Autowired
    private KcuFeedbackMapper kcuFeedbackMapper;
    @Autowired
    private KcuSelfServiceMapper kcuSelfServiceMapper;
    @Autowired
    private DataHost dataHost;
    @Autowired
    private KcuApiServiceMapper kcuApiServiceMapper;


    /** 
    * @Description: 热门一级目录查询 
    * @Param: [system_code] 
    * @return: com.manji.ackservice.common.model.ApiResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/27 
    */ 
    @Override
    public ApiResultData listHotCategory(String system_code) {
        ApiResultData apiResultData = new ApiResultData();
        try {
            List<KcuCategory> kcuCategoryList = kcuCategoryMapper.listHotCategory(0,system_code,1);
            List<Map> kcuCategoryListresult = kcuCategoryListResult(kcuCategoryList);
            Map<String,Object> result = new HashMap<>();
            result.put("categoryList",kcuCategoryListresult);
            apiResultData.setData(result);
        } catch (Exception e) {
            apiResultData.setCode(0);
            apiResultData.setDesc("失败");
            apiResultData.setSuccess(false);
        }
        return apiResultData;
    }
    
    /** 
    * @Description: 根据当前目录查询子一级目录 
    * @Param: [id, system_code] 
    * @return: com.manji.ackservice.common.model.ApiResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/27 
    */ 
    @Override
    public ApiResultData listCategoryByPid(Integer id, String system_code) {
        ApiResultData apiResultData = new ApiResultData();
        if (0 != id) {
            KcuCategory kcuCategory = kcuCategoryMapper.getKcuCategory(id);
            if (null == kcuCategory) {
                apiResultData.setCode(0);
                apiResultData.setDesc("查无该目录");
                apiResultData.setSuccess(false);
                return apiResultData;
            } else {
                if (kcuCategory.getLeaf() == 1) {
                    apiResultData.setCode(0);
                    apiResultData.setDesc("该目录为叶子节点目录，没有下一级目录");
                    apiResultData.setSuccess(false);
                    return apiResultData;
                }
            }
        }
        try {
            if (0 == id) {
                List<KcuCategory> kcuCategoryList = kcuCategoryMapper.listKcuCategoryByPidAndSystemCode(id,system_code);
                List<Map> kcuCategoryListresult = kcuCategoryListResult(kcuCategoryList);
                Map<String,Object> result = new HashMap<>();
                result.put("categoryList",kcuCategoryListresult);
                apiResultData.setData(result);
            } else {
                List<KcuCategory> kcuCategoryList = kcuCategoryMapper.listKcuCategoryByPid(id);
                List<Map> kcuCategoryListresult = kcuCategoryListResult(kcuCategoryList);
                Map<String,Object> result = new HashMap<>();
                result.put("categoryList",kcuCategoryListresult);
                apiResultData.setData(result);
            }
        } catch (Exception e) {
            apiResultData.setCode(0);
            apiResultData.setDesc("失败");
            apiResultData.setSuccess(false);
        }
        return apiResultData;
    }


    /**
    * @Description: 根据知识点id查询知识点详情
    * @Param: [id]
    * @return: com.manji.ackservice.common.model.ApiResultData
    * @Author: LuoYu
    * @Date: 2018/7/27
    */
    @Override
    public ApiResultData getInformationById(String sessionId,Integer id) {
        ApiResultData apiResultData = new ApiResultData();
        Map<String, Object> informationmap = new HashMap<>();
        try {
            KcuInformation kcuInformation = kcuInformationMapper.getKcuInformationById(id);
            if (null == kcuInformation) {
                apiResultData.setCode(0);
                apiResultData.setSuccess(false);
                apiResultData.setDesc("数据库无该知识点");
                return apiResultData;
            } else {
                informationmap.put("id",kcuInformation.getId());
                informationmap.put("title",kcuInformation.getTitle());
                informationmap.put("content",kcuInformation.getContent());
                informationmap.put("view_number",kcuInformation.getView_number());
                informationmap.put("type",kcuInformation.getType());
                informationmap.put("kcuCategoryId",kcuInformation.getKcucategory_id());
            }
            if (StringUtils.isBlank(sessionId)) {
                informationmap.put("state",false);
            } else {
                ResultData resultData = dataHost.getSessionInfo(sessionId);
                if (resultData.getCode().equals("0000")) {
                    Object result=resultData.getData().get("result");
                    JSONObject jsonObject = (JSONObject) JSON.toJSON(result);
                    Integer userId = jsonObject.getInteger("user_id");
                    KcuFeedback kcuFeedbackresult = kcuFeedbackMapper.getKcuFeedback(userId,id);
                    if (null != kcuFeedbackresult) {
                        Integer kcuFeedbackstate = kcuFeedbackresult.getState();
                        if (kcuFeedbackstate == 0) {
                            informationmap.put("state",true);
                        } else {
                            informationmap.put("state",false);
                        }
                    } else {
                        informationmap.put("state",true);
                    }
                } else {
                    informationmap.put("state",false);
                }
            }
            apiResultData.setData(informationmap);
            kcuInformationMapper.updateViewNumber(id);
        } catch (Exception e) {
            apiResultData.setCode(0);
            apiResultData.setDesc("查询知识点失败");
            apiResultData.setSuccess(false);
            return apiResultData;
        }
        return apiResultData;
    }

    /** 
    * @Description: 根据系统code查询反馈配置内容 
    * @Param: [system_code] 
    * @return: com.manji.ackservice.common.model.ApiResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/27 
    */ 
    @Override
    public ApiResultData getKcuFeedbackSet(String system_code) {
        ApiResultData apiResultData = new ApiResultData();
        KcuFeedbackSet kcuFeedbackSet = new KcuFeedbackSet();
        kcuFeedbackSet.setSystem_code(system_code);
        kcuFeedbackSet.setIs_delete(0);
        List<Map> kcuFeedbackSetList = new ArrayList<>();
        List<KcuFeedbackSet> feedbackSetList = kcufeedbackSetMapper.listKcuFeedbackset(kcuFeedbackSet);
        if (null != feedbackSetList && feedbackSetList.size()>0) {
            for(KcuFeedbackSet kcuFeedbackSet1 : feedbackSetList){
                Map<String, Object> taskmap = new HashMap<>();
                taskmap.put("id",kcuFeedbackSet1.getId());
                taskmap.put("feedback_content",kcuFeedbackSet1.getFeedback_content());
                kcuFeedbackSetList.add(taskmap);
            }
            Map<String,Object> result = new HashMap<>();
            result.put("feedbackSetList",kcuFeedbackSetList);
            apiResultData.setData(result);
        } else {
            apiResultData.setCode(0);
            apiResultData.setSuccess(false);
            apiResultData.setDesc("数据库无该系统的反馈配置内容");
            return apiResultData;
        }
        return apiResultData;
    }

    /** 
    * @Description: 用户提交反馈 
    * @Param: [sessionId, kcuFeedback] 
    * @return: com.manji.ackservice.common.model.ApiResultData 
    * @Author: LuoYu
    * @Date: 2018/8/8 
    */ 
    @Override
    public ApiResultData addKcuFeedback(String sessionId,KcuFeedback kcuFeedback) {
        ApiResultData apiResultData = new ApiResultData();
        if (!StringUtils.isBlank(sessionId)) {
            ResultData resultData = dataHost.getSessionInfo(sessionId);
            if (resultData.getCode().equals("0000")) {
                Object result=resultData.getData().get("result");
                JSONObject jsonObject = (JSONObject) JSON.toJSON(result);
                Integer userId = jsonObject.getInteger("user_id");
                String userName = jsonObject.getString("nick_name");
                String userTel = jsonObject.getString("mobile");
                kcuFeedback.setUser_id(userId);
                kcuFeedback.setUser_name(userName);
                kcuFeedback.setUser_tel(userTel);
                KcuFeedback kcuFeedbackresult = kcuFeedbackMapper.getKcuFeedback(userId,kcuFeedback.getKcuinformation_id());
                apiResultData.setData("成功");
                if (null == kcuFeedbackresult) {
                    boolean is_success = kcuFeedbackMapper.addKcuFeedback(kcuFeedback);
                    if (!is_success) {
                        apiResultData.setCode(0);
                        apiResultData.setSuccess(false);
                        apiResultData.setData("失败");
                        apiResultData.setDesc("添加失败");
                        return apiResultData;
                    }
                    return apiResultData;
                } else {
                    kcuFeedback.setId(kcuFeedbackresult.getId());
                    boolean is_success = kcuFeedbackMapper.updateKcuFeedback(kcuFeedback);
                    if (!is_success) {
                        apiResultData.setCode(0);
                        apiResultData.setSuccess(false);
                        apiResultData.setData("失败");
                        apiResultData.setDesc("添加反馈失败");
                        return apiResultData;
                    }
                    return apiResultData;
                }
            } else {
                apiResultData.setCode(0);
                apiResultData.setSuccess(false);
                apiResultData.setData("失败");
                apiResultData.setDesc("添加失败,用户信息有误");
                return apiResultData;
            }
        } else {
            apiResultData.setCode(0);
            apiResultData.setSuccess(false);
            apiResultData.setData("失败1");
            apiResultData.setDesc("添加失败,用户信息有误");
            return apiResultData;
        }
    }



    /** 
    * @Description: 查询自助服务 
    * @Param: [system_code] 
    * @return: com.manji.ackservice.common.model.ApiResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/31 
    */ 
    @Override
    public ApiResultData listKcuSelfService(String system_code) {
        ApiResultData apiResultData = new ApiResultData();
        KcuSelfService kcuSelfService = new KcuSelfService();
        kcuSelfService.setSystem_code(system_code);
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
                kcuSelfServiceList.add(taskmap);
            }
            Map<String,Object> result = new HashMap<>();
            result.put("kcuSelfServiceList",kcuSelfServiceList);
            apiResultData.setData(result);
        } else {
            apiResultData.setCode(0);
            apiResultData.setSuccess(false);
            apiResultData.setDesc("数据库无该系统的自助服务配置内容");
            return apiResultData;
        }

        return apiResultData;
    }

    /** 
    * @Description: 查询所有的分类
    * @Param: [system_code] 
    * @return: com.manji.ackservice.common.model.ApiResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/31 
    */ 
    @Override
    public ApiResultData listCategoryAll(String system_code) {
        ApiResultData apiResultData = new ApiResultData();
        ResultData resultData = kcuCategoryService.listKcuCategoryAll(0,system_code);
        if (resultData.getCode().equals("0000")) {
            Object result=resultData.getData().get("listmap");
            String toJSONString =JSON.toJSONString(result);
            JSONArray jsonArray = JSON.parseArray(toJSONString);
            apiResultData.setData(jsonArray);
        } else {
            apiResultData.setDesc("查询失败");
        }
        return apiResultData;
    }

    /**
    * @Description: 根据不同分类id查询，效率高
    * @Param: [pageNum, pageSize, kcucategoryid, system_code]
    * @return: com.manji.ackservice.common.model.ApiResultData
    * @Author: LuoYu
    * @Date: 2018/8/4
    */
    @Override
    public ApiResultData listKcuInformationNew(Integer pageNum, Integer pageSize, Integer kcucategoryid, String system_code) {
        ApiResultData apiResultData = new ApiResultData();
        if (null == kcucategoryid) {
            apiResultData.setCode(0);
            apiResultData.setDesc("查询知识点列表信息失败");
            apiResultData.setSuccess(false);
            return apiResultData;
        }
        List<Map> kcuInformationList = new ArrayList<>();
        if (kcucategoryid == 0) {
            kcuInformationList= kcuApiServiceMapper.listKcuInformation(kcucategoryid,system_code);
        } else {
            try {
                KcuCategory kcuCategory = kcuCategoryMapper.getKcuCategory(kcucategoryid);
                if (kcuCategory.getLeaf() != 1) {
                    kcuInformationList= kcuApiServiceMapper.listKcuInformationByFirst(kcucategoryid,system_code);
                } else {
                    kcuInformationList= kcuApiServiceMapper.listKcuInformationBySecond(kcucategoryid);
                }
            } catch (Exception e) {
                apiResultData.setCode(0);
                apiResultData.setDesc("查询知识点信息失败");
                apiResultData.setSuccess(false);
                return apiResultData;
            }
        }
        Map<String,Object> result = new HashMap<>();
        result.put("pageSize",pageSize);
        result.put("pageNumber",pageNum);
        if (kcuInformationList.size()>0) {
            List<Map> listresult= kcuInformationList.stream().skip((pageNum-1)*pageSize).limit(pageSize).collect(Collectors.toList());
            result.put("informationList",listresult);
        } else {
            result.put("informationList",null);
        }
        result.put("totalnumber",kcuInformationList.size());
        apiResultData.setData(result);
        return apiResultData;
    }


    /** 
    * @Description: 将categorylist格式数据中的无用数据剔除一些。
    * @Param: [kcuCategoryList] 
    * @return: java.util.List<java.util.Map> 
    * @Author: LuoYu 
    * @Date: 2018/7/27 
    */ 
    private List<Map> kcuCategoryListResult(List<KcuCategory> kcuCategoryList) {
        List<Map> kcuCategoryListresult = new ArrayList<>();
        if (kcuCategoryList != null && kcuCategoryList.size() > 0) {
            for(KcuCategory kcuCategory : kcuCategoryList){
                Map<String, Object> taskmap = new HashMap<>();
                taskmap.put("id",kcuCategory.getId());
                taskmap.put("title",kcuCategory.getTitle());
                kcuCategoryListresult.add(taskmap);
            }
        }
        return kcuCategoryListresult;
    }

}
