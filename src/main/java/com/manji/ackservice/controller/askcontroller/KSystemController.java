package com.manji.ackservice.controller.askcontroller;


import com.manji.ackservice.Service.IKSystemService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.KSystem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/KSystem")
@Api( description ="一个用来系统管理的控制器")
public class KSystemController {
    @Autowired
    private IKSystemService kSystemService;


    @GetMapping("/getAllSystem")
    @ApiOperation("获取所有系统分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页数", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, paramType = "query", dataType = "Integer"),
    })
    public ResultData getSystemListPage(Integer pageIndex,Integer pageSize){

        return kSystemService.getSystemListPage(pageIndex, pageSize);
    }



    @PostMapping("/add")
    @ApiOperation("增加系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sys_code", value = "系统编号", required = true,paramType = "query",  dataType = "String"),
            @ApiImplicitParam(name = "sys_name", value = "系统名字", required = true,paramType = "query",  dataType = "String"),
            @ApiImplicitParam(name = "web_url", value = "web地址", required = true,paramType = "query",  dataType = "String"),
            @ApiImplicitParam(name = "is_delete", value = "删除状态", required = true, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "remark", value = "备注", required = true,paramType = "query",  dataType = "String")
    })
    public ResultData addKSystem(@RequestParam("sys_code") String sys_code,@RequestParam("sys_name") String sys_name,@RequestParam("web_url") String web_url,@RequestParam("is_delete") Boolean is_delete,@RequestParam("remark") String remark){
        KSystem kSystem= new KSystem();
        Date date=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String add_time = format.format(date);
        kSystem.setAdd_time(add_time);
        kSystem.setSys_code(sys_code);
        kSystem.setSys_name(sys_name);
        kSystem.setWeb_url(web_url);
        kSystem.setIs_delete(is_delete);
        kSystem.setRemark(remark);
        return kSystemService.add(kSystem);
    }


    @GetMapping("/update")
    @ApiOperation("修改系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "系统ID", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "sys_name", value = "系统名字", required = true,paramType = "query",  dataType = "String"),
            @ApiImplicitParam(name = "web_url", value = "web地址", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = true, paramType = "query", dataType = "String")

    })
    public ResultData updateKSystem(Integer id,String sys_name, String web_url, String remark){
        KSystem kSystem= new KSystem();
        kSystem.setId(id);
        kSystem.setSys_name(sys_name);
        kSystem.setWeb_url(web_url);
        kSystem.setRemark(remark);
        return kSystemService.update(kSystem);
    }

    @GetMapping("/delete")
    @ApiOperation("删除系统")
    @ApiImplicitParam(name = "id", value = "系统ID", required = true,paramType = "query",  dataType = "Integer")

    public ResultData deleteKSystem(Integer id){
        return kSystemService.delete(id);
    }
}
