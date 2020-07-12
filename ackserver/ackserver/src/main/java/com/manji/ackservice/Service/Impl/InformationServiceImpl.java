package com.manji.ackservice.Service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manji.ackservice.Service.IInformationService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.mapper.CategoryMapper;
import com.manji.ackservice.mapper.InformationMapper;
import com.manji.ackservice.model.Category;
import com.manji.ackservice.model.Information;
import com.manji.ackservice.model.KSystem;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class InformationServiceImpl implements IInformationService {
    @Autowired
    private InformationMapper informationMapper;
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public ResultData getInformationListPage(Integer pageNum, Integer pageSize,Integer state,Integer categoryid,String title) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"成功");
        List<Map> maplist = new ArrayList<Map>();
        PageHelper.startPage(pageNum, pageSize);
        List<Information> data = informationMapper.getInformationList(state,categoryid,title);
        if (data!=null&&data.size()>0) {
            for(Information information:data){
            Map<String, Object> taskmap = new HashMap<>();
                taskmap.put("id",information.getId());
                taskmap.put("title",information.getTitle());
                taskmap.put("add_time",information.getAdd_time());
                taskmap.put("state",information.getState());
                taskmap.put("category_id",information.getCategory_id());
                Category category=categoryMapper.getCategoryByid(information.getCategory_id());
                String categoryname=category.getTitle();
                while (category.getPid()!=0) {
                    category=categoryMapper.getCategoryByid(category.getPid());
                    categoryname=category.getTitle()+">"+categoryname;
                }
                taskmap.put("categoryname",categoryname);
                maplist.add(taskmap);
            }
        }
        PageInfo pageInfo = new PageInfo(data);
        pageInfo.setList(maplist);
        resultData.push("data",pageInfo);
        return resultData;
    }

    @Override
    public ResultData getInformationById(Integer id) {
        ResultData resultData=new ResultData(ResultEmuns.SUCCESS.getCode(),"查询成功");
        Information information=informationMapper.getInformationById(id);
        if (information==null) {
            resultData.setMessage("查询知识点失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
            return resultData;
        }
        resultData.push("data",information);
        return resultData;
    }

    @Override
    public ResultData add(Information information) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"新增知识点成功");
        Information information1=informationMapper.getInformationListByCidAndTitle(information.getCategory_id(),information.getTitle());
        if (null != information1) {
            resultData.setMessage("新增知识点失败,数据库相同的数据");
            resultData.setCode(ResultEmuns.ERROR.getCode());
            return resultData;
        }

        boolean is_success = informationMapper.add(information);
        if(!is_success){
            resultData.setMessage("新增知识点失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }

    @Override
    public ResultData update(Information information) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"编辑知识点成功");
        boolean is_success = informationMapper.update(information);
        if(!is_success){
            resultData.setMessage("编辑知识点失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }


    @Override
    public ResultData delete(String ids) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"删除知识点成功");
        if (StringUtils.isBlank(ids)) {
            resultData.setMessage("删除知识点失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
            return resultData;
        }
            boolean is_success =  informationMapper.delete(ids);
            if (!is_success) {
                resultData.setMessage("删除知识点失败");
                resultData.setCode(ResultEmuns.ERROR.getCode());
            }
        return resultData;
    }

    @Override
    public ResultData view(Integer id) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"修改浏览量成功");
        boolean is_success =  informationMapper.view(id);
        if (!is_success) {
            resultData.setMessage("修改浏览量失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }

    @Override
    public ResultData changeState(Integer id, Integer state) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"修改状态成功");
        boolean is_success = informationMapper.changeState(id,state);
        if (!is_success) {
            resultData.setMessage("修改状态失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }


}
