package com.manji.ackservice.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manji.ackservice.Service.IKSystemService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.mapper.KSystemMapper;
import com.manji.ackservice.model.KSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KSystemServiceImpl implements IKSystemService{
    @Autowired
    private KSystemMapper kSystemMapper;

    @Override
    public ResultData getSystemListPage(Integer pageNum, Integer pageSize) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"成功");

        PageHelper.startPage(pageNum, pageSize);
        List<KSystem> data = kSystemMapper.getKSystemList();
        PageInfo pageInfo = new PageInfo(data);
        resultData.push("data",pageInfo);

        return resultData;
    }

    @Override
    public ResultData add(KSystem kSystem) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"新增系统成功");
        boolean is_success = kSystemMapper.add(kSystem);
        if(!is_success){
            resultData.setMessage("新增系统失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }

    @Override
    public ResultData update(KSystem kSystem) {
        ResultData resultData=new ResultData(ResultEmuns.SUCCESS.getCode(),"修改系统成功");
        boolean is_success=kSystemMapper.update(kSystem);
        if(!is_success){
            resultData.setMessage("修改系统失败");
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }

    @Override
    public ResultData delete(Integer id) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"删除分类成功");
        kSystemMapper.delete(id);
        return resultData;
    }
}
