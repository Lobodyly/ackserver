package com.manji.ackservice.controller.askcontroller;

import com.manji.ackservice.Service.IInformationService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.Category;
import com.manji.ackservice.model.Information;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/Information")
@Api( description ="一个用来知识点管理的控制器")
public class InformationController {
    @Autowired
    private IInformationService informationService;

    @GetMapping("/getallinformation")
    @ApiOperation("获取所有信息分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "state", value = "状态", required = false,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "categoryid", value = "分类ID", required = false,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "标题", required = false,paramType = "query",  dataType = "String"),
    })
    public ResultData getallInformation(Integer pageNum, Integer pageSize,Integer state,Integer categoryid,String title){

        return informationService.getInformationListPage(pageNum, pageSize,state,categoryid,title);
    }

    @GetMapping("/getinformationbyid")
    @ApiOperation("查看知识点")
    @ApiImplicitParam(name = "id", value = "知识点ID", required = true,paramType = "query",  dataType = "Integer")
    public  ResultData getInformationById(Integer id){

        return informationService.getInformationById(id);
    }

    @PostMapping("/add")
    @ApiOperation("增加知识点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_id", value = "分类ID", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "知识点标题", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "system_code", value = "系统code", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "知识点具体内容", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "add_user_name", value = "新增人姓名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "state", value = "状态", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String")
    })
    public ResultData add(Integer category_id, String title, String system_code,String content, String add_user_name, Integer state,String remark) {
        Information information = new Information();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String add_time = format.format(date);
        information.setCategory_id(category_id);
        information.setTitle(title);
        information.setState(state);
        information.setSystem_code(system_code);
        information.setContent(content);
        information.setView_number(1);
        information.setAdd_user_name(add_user_name);
        information.setAdd_time(add_time);
        information.setRemark(remark);
        return informationService.add(information);
    }

    @GetMapping("/update")
    @ApiOperation("修改知识点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "知识点ID", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "category_id", value = "分类ID", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "知识点标题", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "知识点具体内容", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "update_user_name", value = "修改人姓名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "state", value = "状态", required = false,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String")

    })
    public ResultData updateKSystem(Integer id,Integer category_id,Integer state, String title, String content, String update_user_name, String remark) {
        Information information = new Information();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String update_time = format.format(date);
        information.setId(id);
        information.setCategory_id(category_id);
        information.setTitle(title);
        information.setContent(content);
        information.setUpdate_user_name(update_user_name);
        information.setUpdate_time(update_time);
        information.setState(state);
        information.setRemark(remark);
        return informationService.update(information);

    }


    @GetMapping("/delete")
    @ApiOperation("删除知识点")
    @ApiImplicitParam(name = "ids", value = "知识点ID", required = true,paramType = "query",  dataType = "String")

    public ResultData deleteKSystem(String ids){

        return informationService.delete(ids);
    }


    @GetMapping("/view")
    @ApiOperation("查看知识点")
    @ApiImplicitParam(name = "id", value = "知识点ID", required = true,paramType = "query",  dataType = "Integer")
    public  ResultData view(Integer id){

        return informationService.view(id);
    }

    @GetMapping("/changestate")
    @ApiOperation("改变知识点状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "知识点ID", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "state", value = "状态", required = false,paramType = "query",  dataType = "Integer"),
    })
    public  ResultData changeState(Integer id,Integer state){

        return informationService.changeState(id,state);
    }
}