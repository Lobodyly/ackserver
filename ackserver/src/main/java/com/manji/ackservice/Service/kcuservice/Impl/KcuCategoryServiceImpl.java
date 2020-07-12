package com.manji.ackservice.Service.kcuservice.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manji.ackservice.Service.kcuservice.IKcuCategoryService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.mapper.kcumapper.KcuCategoryMapper;
import com.manji.ackservice.mapper.kcumapper.KcuConnectionMapper;
import com.manji.ackservice.mapper.kcumapper.KcuInformationMapper;
import com.manji.ackservice.model.kcumodel.KcuCASConnection;
import com.manji.ackservice.model.kcumodel.KcuCategory;
import com.manji.ackservice.model.kcumodel.KcuInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/23
 * Time:16:42
 */
@Service
public class KcuCategoryServiceImpl implements IKcuCategoryService{

    @Autowired
    private KcuCategoryMapper kcuCategoryMapper;
    @Autowired
    private KcuConnectionMapper kcuConnectionMapper;
    @Autowired
    private KcuInformationMapper kcuInformationMapper;
    
    /** 
    * @Description: 新建分类 
    * @Param: [kcuCategory] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/23 
    */ 
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public ResultData addKcuCategory(KcuCategory kcuCategory,String system_codelist) throws Exception{
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"新增分类成功");
        JSONArray jsonArray = JSON.parseArray(system_codelist);
        Integer pid = kcuCategory.getPid();
        if (pid != 0) {
            KcuCategory kcuCategory1 = new KcuCategory();
            kcuCategory1.setTitle(kcuCategory.getTitle());
            kcuCategory1.setPid(pid);
            List<KcuCategory> result=kcuCategoryMapper.listKcuCategory(kcuCategory1);
            if (null != result && result.size() > 0) {
                throw new Exception("新增分类失败，数据库已有相同数据");
            }
            KcuCategory kcuCategory2 = new KcuCategory();
            kcuCategory2.setId(pid);
            List<KcuCategory> result2=kcuCategoryMapper.listKcuCategory(kcuCategory2);
            if (null == result2) {
                throw new Exception("新增分类失败，上级目录不存在");
            } else {
                if (result2.size() == 0) {
                    throw new Exception("新增分类失败，上级目录不存在");
                }
            }
            boolean is_success = kcuCategoryMapper.addKcuCategory(kcuCategory);
            if (!is_success) {
                throw new Exception("新增分类失败");
            }
        }
        if (0 == pid) {
            if (null != jsonArray) {
                Integer number = 0;
                Integer total = jsonArray.size();
                for (int i = 0; i < total; i++) {
                    String system_code = jsonArray.getString(i);
                    KcuCategory kcuCategory1 = kcuCategoryMapper.getKcuCategoryByIdAndSystemCode(pid,system_code,kcuCategory.getTitle());
                    if (null != kcuCategory1) {
                        throw new Exception("新增分类失败，数据库已有相同数据");
                    }
                }
            }
            boolean is_success = kcuCategoryMapper.addKcuCategory(kcuCategory);
            if (!is_success) {
                throw new Exception("新增分类失败");
            }
            Integer kcuCategoryId = kcuCategory.getId();
            if (null != jsonArray) {
                Integer number = 0;
                Integer total = jsonArray.size();
                for (int i = 0; i < total; i++) {
                    String system_code = jsonArray.getString(i);
                    boolean success_result = kcuConnectionMapper.addSysAndCateConnetion(kcuCategoryId,system_code);
                    if (success_result) {
                        number = number+1;
                    } else {
                        throw new Exception("新增分类关联系统"+system_code+"失败");
                    }
                }
                resultData.setMessage("新增分类成功,新增分类关联系统成功"+number+"条");
            }
        }
        return resultData;
    }
    
    /** 
    * @Description: 更新分类 
    * @Param: [kcuCategory] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/23 
    */ 
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public ResultData updateKcuCategory(KcuCategory kcuCategory,String system_codelist) throws Exception{
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"更新分类成功");
        JSONArray jsonArray = JSON.parseArray(system_codelist);
        Integer pid = kcuCategory.getPid();
        if (pid != 0) {
            KcuCategory kcuCategory1 = new KcuCategory();
            kcuCategory1.setPid(kcuCategory.getPid());
            kcuCategory1.setTitle(kcuCategory.getTitle());
            List<KcuCategory> result=kcuCategoryMapper.listKcuCategory(kcuCategory1);
            if (null != result && result.size() > 0) {
                if(result.size() == 1 && result.get(0).getId().equals(kcuCategory.getId())){
                    kcuCategoryMapper.updateKcuCategory(kcuCategory);
                }
                else{
                    throw new Exception("修改分类失败,数据库已有该数据");
                }
            } else {
                kcuCategoryMapper.updateKcuCategory(kcuCategory);
            }
            kcuCategoryMapper.updateKcuCategory(kcuCategory);
        }
        if (0 == pid) {
            if (null != jsonArray) {
                Integer number = 0;
                Integer total = jsonArray.size();
                for (int i = 0; i < total; i++) {
                    String system_code = jsonArray.getString(i);
                    KcuCategory kcuCategory1 = kcuCategoryMapper.getKcuCategoryByIdAndSystemCode(pid,system_code,kcuCategory.getTitle());
                    if (null != kcuCategory1) {
                        throw new Exception("修改分类失败，数据库已有相同数据");
                    }
                }
            }
            Integer kcuCategoryId = kcuCategory.getId();
            boolean is_success1= kcuConnectionMapper.deleteByKcucategoryId(kcuCategoryId);
            if (!is_success1) {
                throw new Exception("修改分类与系统关联失败");
            }
            if (null != jsonArray) {
                Integer number = 0;
                Integer total = jsonArray.size();
                for (int i = 0; i < total; i++) {
                    String system_code = jsonArray.getString(i);
                    boolean success_result = kcuConnectionMapper.addSysAndCateConnetion(kcuCategoryId,system_code);
                    if (success_result) {
                        number = number+1;
                    } else {
                        throw new Exception("修改分类关联系统"+system_code+"失败");
                    }
                }
                resultData.setMessage("修改分类成功,修改分类关联系统成功"+number+"条");
            }
        }
        return resultData;
    }
    
    /** 
    * @Description: 根据分类ID删除分类 
    * @Param: [id] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/23 
    */ 
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public ResultData deleteKcuCategory(Integer id) throws Exception{
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"删除分类成功");
        KcuCategory kcuCategory = new KcuCategory();
        kcuCategory.setPid(id);
        List<KcuCategory> result = kcuCategoryMapper.listKcuCategory(kcuCategory);
        if (null != result && result.size() > 0){
            throw new Exception("删除分类失败,请先删除子分类");
        }
        KcuCategory kcuCategory1 = kcuCategoryMapper.getKcuCategory(id);
        if (kcuCategory1.getLeaf() == 0) {
            kcuCategoryMapper.deleteKcuCategory(id);
        } else {
            KcuInformation kcuInformation = new KcuInformation();
            kcuInformation.setKcucategory_id(id);
            List<KcuInformation> kcuInformationlist = kcuInformationMapper.listKcuInformationAll(kcuInformation);
            if (null != kcuInformationlist && kcuInformationlist.size() > 0 ) {
                throw new Exception("删除分类失败,请先删除或者移除该分类关联的知识点");
            } else {
                kcuCategoryMapper.deleteKcuCategory(id);
            }
        }
        kcuConnectionMapper.deleteByKcucategoryId(id);
        if (kcuCategory1.getPid() == 0) {
            try {
                kcuConnectionMapper.deleteByKcucategoryId(id);
            } catch (Exception e) {
                throw new Exception("删除系统与分类的关联信息失败");
            }
        }
        return resultData;
    }


    /**
    * @Description: 根据系统code和pid查询单级分类
    * @Param: [pid, system_code] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/23 
    */     
    @Override
    public ResultData listKcuCategoryByPidAndSystemCode(Integer pid, String system_code) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询单级分类成功");
        try {
            if (0 == pid) {
                List<KcuCategory> kcuCategoryList = kcuCategoryMapper.listKcuCategoryByPidAndSystemCode(pid,system_code);
                resultData.push("KcuCategoryList",kcuCategoryList);
            } else {
                List<KcuCategory> kcuCategoryList = kcuCategoryMapper.listKcuCategoryByPid(pid);
                resultData.push("KcuCategoryList",kcuCategoryList);
            }
        } catch (Exception e) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("查询单级分类失败");
        }
        return resultData;   
    }

    /**
    * @Description: 根据系统code和pid查询所有分类
    * @Param: [pid, system_code]
    * @return: com.manji.ackservice.common.model.ResultData
    * @Author: LuoYu
    * @Date: 2018/7/23
    */
    @Override
    public ResultData listKcuCategoryAll(Integer pid, String system_code) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询所有分类成功");
        List<Map> result = getKcuCategoryByPid(pid,system_code);
        resultData.push("listmap",result);
        return resultData;
    }

    private List<Map> getKcuCategoryByPid(Integer pid,String system_code) {
        List<KcuCategory> kcuCategoryList = new ArrayList<>();
        if (0 == pid) {
            kcuCategoryList = kcuCategoryMapper.listKcuCategoryByPidAndSystemCode(pid,system_code);
        } else {
            kcuCategoryList = kcuCategoryMapper.listKcuCategoryByPid(pid);
        }
        List<Map> mapList = new ArrayList<>();
        if (null != kcuCategoryList && kcuCategoryList.size() > 0) {
            for (KcuCategory kcuCategory : kcuCategoryList) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("id",kcuCategory.getId());
                dataMap.put("pid",kcuCategory.getPid());
                dataMap.put("title",kcuCategory.getTitle());
                if (kcuCategory.getLeaf() == 0) {
                    KcuCategory kcuCategory1 = new KcuCategory();
                    kcuCategory1.setPid(kcuCategory.getId());
                    List<KcuCategory> resultlist = kcuCategoryMapper.listKcuCategory(kcuCategory1);
                    if (null != resultlist && resultlist.size()>0) {
                        dataMap.put("children",getKcuCategoryByPid(kcuCategory.getId(),system_code));
                    }
                }
                mapList.add(dataMap);
            }
        }
        return mapList;
    }

    /**
    * @Description: 根据pid查询和系统code查询所有叶子节点目录(嵌套查询)
    * @Param: [pid, system_code] 
    * @return: com.manji.ackservice.common.model.ResultData 
    * @Author: LuoYu 
    * @Date: 2018/7/26 
    */
   /* @Override
    public ResultData listKcuCategoryIdByLeaf(Integer pid,String system_code) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询所有分类成功");
        List<Integer> result = getKcuCategoryIdByLeaf(pid,system_code);
        resultData.push("idlist",result);
        return resultData;
    }*/

 /*   private List<Integer> getKcuCategoryIdByLeaf(Integer pid,String system_code){
        List<KcuCategory> kcuCategoryList = new ArrayList<>();
        if (pid == 0) {
            kcuCategoryList = kcuCategoryMapper.listKcuCategoryByPidAndSystemCode(pid,system_code);
        }
        else {
            kcuCategoryList = kcuCategoryMapper.listKcuCategoryByPid(pid);
        }
        List<Integer> idList = new ArrayList<>();
        if (null != kcuCategoryList && kcuCategoryList.size() > 0) {
            for (KcuCategory kcuCategory : kcuCategoryList) {
                KcuCategory kcuCategory1 = new KcuCategory();
                kcuCategory1.setPid(kcuCategory.getId());
                List<KcuCategory> resultlist = kcuCategoryMapper.listKcuCategory(kcuCategory1);
                if (null != resultlist && resultlist.size()>0) {
                    idList.addAll(getKcuCategoryIdByLeaf(kcuCategory.getId(),system_code));
                } else {
                    if (kcuCategory.getLeaf() != 0) {
                        idList.add(kcuCategory.getId());
                    }
                }
            }
        }
        return idList;
    }
*/
    /**
    * @Description: 查询分类详情
    * @Param: [id]
    * @return: com.manji.ackservice.common.model.ResultData
    * @Author: LuoYu
    * @Date: 2018/8/1
    */
    @Override
    public ResultData getKcuCategory(Integer id) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询所有分类成功");
        KcuCategory kcuCategory = kcuCategoryMapper.getKcuCategory(id);
        if (null != kcuCategory) {
            resultData.push("kcuCategory",kcuCategory);
        } else {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage("查询分类详情失败");
        }
        return resultData;
    }

    /**
    * @Description: 条件查询分类列表
    * @Param: [pageNum, pageSize, pid, system_code, type, title]
    * @return: com.manji.ackservice.common.model.ResultData
    * @Author: LuoYu
    * @Date: 2018/8/6
    */
    @Override
    public ResultData listKcuCategoryByConditions(Integer pageNum, Integer pageSize,Integer pid,String system_code,Integer type,String title) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询分类列表成功");
        PageHelper.startPage(pageNum, pageSize);
        List<KcuCategory> kcuCategoryList = new ArrayList<>();
        if (0 == pid) {
            kcuCategoryList = kcuCategoryMapper.listKcuCategoryByConditionsFirst(pid,type,system_code,title);
        } else {
            kcuCategoryList = kcuCategoryMapper.listKcuCategoryByConditionsSeconde(pid,type,title);
        }
        PageInfo pageInfo = new PageInfo(kcuCategoryList);
        resultData.push("data",pageInfo);
        return resultData;
    }


}
