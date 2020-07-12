package com.manji.ackservice.Service.kcuservice.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manji.ackservice.Service.kcuservice.IKcuCategoryService;
import com.manji.ackservice.Service.kcuservice.IKcuInformationService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.controller.base.BaseModel;
import com.manji.ackservice.controller.base.BaseSearchAddBiz;
import com.manji.ackservice.mapper.kcumapper.KcuCategoryMapper;
import com.manji.ackservice.mapper.kcumapper.KcuConnectionMapper;
import com.manji.ackservice.mapper.kcumapper.KcuInformationMapper;
import com.manji.ackservice.model.kcumodel.KcuCASConnection;
import com.manji.ackservice.model.kcumodel.KcuCategory;
import com.manji.ackservice.model.kcumodel.KcuInformation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/24
 * Time:9:44
 */
@Service
public class KcuInformationServiceImpl implements IKcuInformationService{
    @Autowired
    private KcuInformationMapper kcuInformationMapper;
    @Autowired
    private KcuCategoryMapper kcuCategoryMapper;
    @Autowired
    private KcuConnectionMapper kcuConnectionMapper;

    /**
    * @Description: 添加知识点
    * @Param: [kcucategory_id, title, content, add_user_name, state, remark, type
    * @return: com.manji.ackservice.common.model.ResultData
    * @Author: LuoYu
    * @Date: 2018/7/24
    */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultData addKcuInformation(Integer kcucategory_id, String title, String content, String add_user_name, Integer state,String remark,Integer type) throws Exception {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"添加成功");
        KcuInformation kcuInformation = new KcuInformation();
        kcuInformation.setTitle(title);
        kcuInformation.setContent(content);
        kcuInformation.setAdd_user_name(add_user_name);
        kcuInformation.setState(state);
        kcuInformation.setRemark(remark);
        kcuInformation.setType(type);
        kcuInformation.setView_number(1);
        kcuInformation.setKcucategory_id(kcucategory_id);
        Date date = new Date();
        kcuInformation.setAdd_time(date);
        KcuInformation kcuInformation1=new KcuInformation();
        kcuInformation1.setTitle(title);
        KcuCategory kcuCategory = kcuCategoryMapper.getKcuCategory(kcucategory_id);
        if (null == kcuCategory) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("添加知识点失败,数据库无该分类");
            return resultData;
        } else {
            if (kcuCategory.getLeaf() == 0) {
                resultData.setCode(ResultEmuns.ERROR.getCode());
                resultData.setMessage("添加知识点失败,该分类下面不能添加知识点");
                return resultData;
            }
        }
        List<KcuInformation> result = kcuInformationMapper.listKcuInformationAll(kcuInformation1);
        if (null != result && result.size()>0) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("添加知识点失败,数据库已经存在相同标题知识点");
            return resultData;
        }
        boolean is_success = kcuInformationMapper.addKcuInformation(kcuInformation);
        if (!is_success) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("添加知识点失败");
            return resultData;
        }
        KcuInformation kcuInformation2 = kcuInformationMapper.getKcuInformationById(kcuInformation.getId());
        addHotSearchWords(kcuInformation2);
        return resultData;
    }

    /** 
    * @Description: 修改知识点 
    * @Param: [id, category_idlist, title, content, update_user_name, state, remark, type]
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/24 
    */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public ResultData updateKcuInformation(Integer id,Integer kcucategory_id, String title, String content, String update_user_name, Integer state,String remark,Integer type) throws Exception {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"修改成功");
        KcuInformation kcuInformation = new KcuInformation();
        kcuInformation.setId(id);
        kcuInformation.setTitle(title);
        kcuInformation.setContent(content);
        kcuInformation.setUpdate_user_name(update_user_name);
        kcuInformation.setState(state);
        kcuInformation.setRemark(remark);
        kcuInformation.setType(type);
        kcuInformation.setKcucategory_id(kcucategory_id);
        kcuInformation.setView_number(1);
        Date date = new Date();
        kcuInformation.setUpdate_time(date);
        KcuInformation kcuInformation1=new KcuInformation();
        kcuInformation1.setTitle(title);
        KcuCategory kcuCategory = kcuCategoryMapper.getKcuCategory(kcucategory_id);
        if (null == kcuCategory) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("修改知识点失败,数据库无该分类");
            return resultData;
        } else {
            if (kcuCategory.getLeaf() == 0) {
                resultData.setCode(ResultEmuns.ERROR.getCode());
                resultData.setMessage("修改知识点失败,该分类下面不能添加知识点");
                return resultData;
            }
        }
        List<KcuInformation> result = kcuInformationMapper.listKcuInformationAll(kcuInformation1);
        if (null != result && result.size()>0) {
            if (!result.get(0).getId().equals(id)) {
                resultData.setCode(ResultEmuns.ERROR.getCode());
                resultData.setMessage("修改知识点失败,数据库已经存在相同标题知识点");
                return resultData;
            }
        }
        boolean is_success = kcuInformationMapper.updateKcuInformation(kcuInformation);
        if (!is_success) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("修改知识点失败");
            return resultData;
        }
        KcuInformation kcuInformation2 = kcuInformationMapper.getKcuInformationById(id);
        addHotSearchWords(kcuInformation2);
        return resultData;
    }

    /** 
    * @Description: 修改启动状态 
    * @Param: [id, state, update_user_name] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/24 
    */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public ResultData updateState(Integer id, Integer state, String update_user_name) throws Exception {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"修改启动状态成功");
        Date date = new Date();
        boolean is_success = kcuInformationMapper.updateState(id,state,update_user_name,date);
        if (!is_success) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("修改启动状态失败");
            return resultData;
        }
        KcuInformation kcuInformation = kcuInformationMapper.getKcuInformationById(id);
        addHotSearchWords(kcuInformation);
        return resultData;
    }

    /** 
    * @Description: 修改热门状态
    * @Param: [id, type, update_user_name] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/24 
    */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public ResultData updateType(Integer id, Integer type, String update_user_name) throws Exception {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"修改热门状态成功");
        Date date = new Date();
        boolean is_success = kcuInformationMapper.updateType(id,type,update_user_name,date);
        if (!is_success) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("修改热门状态失败");
            return resultData;
        }
        KcuInformation kcuInformation = kcuInformationMapper.getKcuInformationById(id);
        addHotSearchWords(kcuInformation);
        return resultData;
    }


    /** 
    * @Description: 可以批量删除知识点 
    * @Param: [ids] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/25 
    */ 
    @Override
    public ResultData deleteKcuInformation(String ids) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"删除知识点成功");
        if (StringUtils.isBlank(ids)) {
            resultData.setMessage("删除知识点失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
            return resultData;
        }
        JSONArray jsonArray = JSON.parseArray(ids);
        for (Object object : jsonArray) {
            Integer id = Integer.valueOf(object.toString());
            boolean is_success = kcuInformationMapper.deleteKcuInformation(id);
            if (!is_success) {
                resultData.setMessage("删除知识点失败");
                resultData.setCode(ResultEmuns.ERROR.getCode());
            }
        }
        return resultData;
    }



    /**
    * @Description: 查询单条知识点
    * @Param: [id]
    * @return: com.manji.ackservice.common.model.ResultData
    * @Author: LuoYu
    * @Date: 2018/7/25
    */
    @Override
    public ResultData getKcuInformationById(Integer id) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询成功");
        KcuInformation result = kcuInformationMapper.getKcuInformationById(id);
        if (null == result) {
            resultData.setMessage("查询知识点失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
            return resultData;
        }
        KcuCategory kcuCategory = kcuCategoryMapper.getKcuCategory(result.getKcucategory_id());
        if (null == kcuCategory) {
            resultData.setMessage("查询知识点失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
            return resultData;
        } else {
            resultData.push("secondKcuCategoryTitle",kcuCategory.getTitle());
            KcuCategory kcuCategory1 = kcuCategoryMapper.getKcuCategory(kcuCategory.getPid());
            if (null == kcuCategory1) {
                resultData.setMessage("查询知识点失败");
                resultData.setCode(ResultEmuns.ERROR.getCode());
                return resultData;
            } else {
                resultData.push("firstKcuCategoryTitle",kcuCategory1.getTitle());
            }
        }
        resultData.push("data",result);
        return resultData;
    }

    /**
    * @Description: 根据一级二级目录查询知识点列表以及所有目录对应的知识点列表
    * @Param: [id, level, kcusystem_code, title]
    * @return: com.manji.ackservice.common.model.ResultData
    * @Author: LuoYu
    * @Date: 2018/8/3
    */
    @Override
    public ResultData listKcuInformation(Integer pageNum, Integer pageSize,Integer id,Integer level,Integer state,Integer type, String kcusystem_code,String title) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询成功");
        PageHelper.startPage(pageNum, pageSize);
        List<KcuInformation> listresult = new ArrayList<>();
        if (null == id || id == 0) {
            listresult = kcuInformationMapper.listKcuInformation(id,state,type,kcusystem_code,title);
        }
        else {
            if (null == level) {
                resultData.setCode("00001");
                resultData.setMessage("level不能为空");
                return resultData;
            } else {
                if (level == 1) {
                    listresult = kcuInformationMapper.listKcuInformationByFirst(id,state,type,kcusystem_code,title);
                }
                if (level == 2) {
                    listresult = kcuInformationMapper.listKcuInformationBySecond(id,state,type,title);
                }
            }
        }
        PageInfo pageInfo = new PageInfo(listresult);
        resultData.push("data",pageInfo);
        return resultData;
    }



    private void addHotSearchWords(KcuInformation kcuInformation) throws Exception {
        BaseModel baseModel = new BaseModel();
        baseModel.setId(kcuInformation.getId().toString());
        baseModel.setContent(kcuInformation.getContent());
        baseModel.setTitle(kcuInformation.getTitle());
        long addtime = kcuInformation.getAdd_time().getTime();
        baseModel.setAdd_timestamp(addtime);
        long updatetime = System.currentTimeMillis();
        baseModel.setUpd_timestamp(updatetime);
        baseModel.setView_number(kcuInformation.getView_number().toString());
        baseModel.setState(kcuInformation.getState().toString());
        baseModel.setType(kcuInformation.getType().toString());
        String indexType = "";
        Integer id = kcuInformation.getKcucategory_id();
        List<KcuCASConnection> listCAIConnection = kcuConnectionMapper.listKcuCASConnectionByKcuCategoryId(id);
        if (null != listCAIConnection && listCAIConnection.size()>0) {
            for (KcuCASConnection kcuCASConnection1 : listCAIConnection) {
                indexType = indexType + kcuCASConnection1.getKcusystem_code()+";";
            }
        }
        baseModel.setIndexType(indexType);
        BaseSearchAddBiz.addHotSearchWords(baseModel);
    }
}
