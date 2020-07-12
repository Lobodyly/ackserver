package com.manji.ackservice.controller.askcontroller;


import com.manji.ackservice.Service.ICategoryService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.Category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/Category")
@Api( description ="一个用来知识中心分类管理的控制器")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/getcategory")
    @ApiOperation("获取所有分类")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "pid", value = "上级分类ID", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "system_code", value = "系统code", required = false,paramType = "query",  dataType = "String")
    })
    public ResultData getCategory(Integer pid,String system_code){

        return categoryService.getCategoryList(pid,system_code);
    }

    @GetMapping("/getAllcategory")
    @ApiOperation("获取所有分类包括子级")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "pid", value = "上级分类ID", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "system_code", value = "系统code", required = false,paramType = "query",  dataType = "String")
    })
    public ResultData getAllcategory(Integer pid,String system_code){

        return categoryService.getAllCategory(pid,system_code);
    }


    @PostMapping("/add")
    @ApiOperation("增加分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "system_code", value = "系统code", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pid", value = "上级分类ID", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "分类名称", required = true,paramType = "query",  dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String")
    })
    public ResultData addCategory(String system_code,Integer pid,String title,String remark){
        Category category=new Category();
        Date date=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String add_time = format.format(date);
        category.setAdd_time(add_time);
        category.setSystem_code(system_code);
        category.setTitle(title);
        category.setRemark(remark);
        category.setPid(pid);
        return categoryService.add(category);
    }


    @GetMapping("/update")
    @ApiOperation("修改分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "分类ID", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "system_code", value = "系统ID", required = true,paramType = "query",  dataType = "String"),
            @ApiImplicitParam(name = "title", value = "分类名称", required = true,paramType = "query",  dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pid", value = "上级分类ID", required = true, paramType = "query", dataType = "Integer")

    })
    public ResultData updateCategory(Integer id,String system_code, String title, String remark,Integer pid){
        Category category=new Category();
        category.setId(id);
        category.setPid(pid);
        category.setSystem_code(system_code);
        category.setTitle(title);
        category.setRemark(remark);
        return categoryService.update(category);
    }

    @GetMapping("/delete")
    @ApiOperation("删除分类")
    @ApiImplicitParam(name = "id", value = "分类ID", required = true, paramType = "query", dataType = "Integer")

    public ResultData deleteCategory(int id){
        return categoryService.delete(id);
    }
}
