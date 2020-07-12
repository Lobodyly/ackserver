package com.manji.ackservice.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manji.ackservice.Service.ICategoryService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.mapper.CategoryMapper;
import com.manji.ackservice.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  on 2018-6-21.
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResultData getCategoryList(Integer pid,String system_code) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"成功");

        List<Category> data = categoryMapper.getCategoryListByPid(pid);
        PageInfo pageInfo = new PageInfo(data);
        resultData.push("data",pageInfo);

        return resultData;
    }

    @Override
    public ResultData add(Category category) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"新增分类成功");
        Category categories = categoryMapper.getCategoryListByPidAndTitle(category.getPid(),category.getTitle());
        if(null != categories){
            resultData.setMessage("新增分类失败,数据库已有该数据");
            resultData.setCode(ResultEmuns.ERROR.getCode());
            return resultData;
        }
        boolean is_success = categoryMapper.add(category);
        if(!is_success){
            resultData.setMessage("新增分类失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }

    @Override
    public ResultData update(Category category) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"编辑分类成功");
        Category categories = categoryMapper.getCategoryListByPidAndTitle(category.getPid(),category.getTitle());
        if(null != categories){
            if(categories.getId()==category.getId()){
                return resultData;
            }
            else{
            resultData.setMessage("修改分类失败,数据库已有该数据");
            resultData.setCode(ResultEmuns.ERROR.getCode());
            return resultData;
            }
        }
        boolean is_success = categoryMapper.update(category);
        if(!is_success){
            resultData.setMessage("编辑分类失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultData delete(Integer id) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"删除分类成功");
        List<Category> categories = categoryMapper.getCategoryListByPid(id);
        if(null != categories && categories.size()>0){
            resultData.setMessage("删除分类失败,请先删除子分类");
            resultData.setCode(ResultEmuns.ERROR.getCode());
            return resultData;
        }
        categoryMapper.delete(id);
        return resultData;
    }

    public ResultData getAllCategory(Integer pid,String system_code){
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"获取分类成功");
        List<Map> list=getCategoryByPid(pid,system_code);
        resultData.push("listmap",list);
        return  resultData;
    }

    public List<Map> getCategoryByPid(Integer pid,String system_code){
        List<Category> categorielist=categoryMapper.getCategoryListByPidAndSyscode(pid,system_code);
        List<Map> list=new ArrayList<Map>();
        if (!categorielist.isEmpty()&&categorielist.size()>0){
            for (Category category:categorielist){
                Map<String, Object> dataMap=new HashMap<String, Object>();
                dataMap.put("id",category.getId());
                dataMap.put("pid",category.getPid());
                dataMap.put("title",category.getTitle());
                if(categoryMapper.getCategoryListByPidAndSyscode(category.getId(),category.getSystem_code())!=null){
                dataMap.put("children",getCategoryByPid(category.getId(),system_code));}
                list.add(dataMap);
            }
        }
        return list;
    }

}
