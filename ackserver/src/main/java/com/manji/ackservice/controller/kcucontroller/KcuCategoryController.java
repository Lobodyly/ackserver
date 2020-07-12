package com.manji.ackservice.controller.kcucontroller;

import com.manji.ackservice.Service.kcuservice.IKcuCategoryService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.model.kcumodel.KcuCategory;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/kcucategory")
@Api(description = "帮助中心分类管理的控制器")
public class KcuCategoryController {
    @Autowired
    IKcuCategoryService kcuCategoryService;


    @PostMapping("/add")
    @ApiOperation("增加分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="system_codelist", value = "系统codelist[\"Buyer\",\"Shop\"]", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pid", value = "上级pid", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "分类名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false,paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "热门状态", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "leaf", value = "叶子节点状态", required = true, paramType = "query", dataType = "Integer")
    })
    public ResultData addKcuCategory(String system_codelist,Integer pid,String title,String remark,Integer sort,Integer type,Integer leaf) {
        ResultData resultData = new ResultData();
        KcuCategory kcuCategory = new KcuCategory();
        kcuCategory.setPid(pid);
        kcuCategory.setTitle(title);
        kcuCategory.setRemark(remark);
        Date date = new Date();
        kcuCategory.setAdd_time(date);
        kcuCategory.setSort(sort);
        kcuCategory.setType(type);
        kcuCategory.setLeaf(leaf);
        try {
            resultData = kcuCategoryService.addKcuCategory(kcuCategory,system_codelist);
        } catch (Exception e) {
            resultData.setMessage(e.getMessage());
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }

    @PostMapping("/update")
    @ApiOperation("修改分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="id", value = "分类id", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name ="system_codelist", value = "系统codelist[\"Buyer\",\"Shop\"]", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pid", value = "上级pid", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "分类名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false,paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sort", value = "排序", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "热门状态", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "leaf", value = "叶子节点状态", required = false, paramType = "query", dataType = "Integer")
    })
    public ResultData updateKcuCategory(@RequestParam("id")Integer id, @RequestParam("system_codelist") String system_codelist, Integer pid, String title, String remark, Integer sort, Integer type, Integer leaf){
        ResultData resultData = new ResultData();
        KcuCategory kcuCategory = new KcuCategory();
        kcuCategory.setId(id);
        kcuCategory.setPid(pid);
        kcuCategory.setTitle(title);
        kcuCategory.setRemark(remark);
        kcuCategory.setSort(sort);
        kcuCategory.setType(type);
        kcuCategory.setLeaf(leaf);
        try {
            resultData = kcuCategoryService.updateKcuCategory(kcuCategory,system_codelist);
        } catch (Exception e) {
            resultData.setMessage(e.getMessage());
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }

    @GetMapping("/delete")
    @ApiOperation("删除分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="id", value = "分类id", required = true, paramType = "query", dataType = "Integer")
    })
    public ResultData deleteKcuCategory(Integer id){
        ResultData resultData = new ResultData();
        try {
            resultData = kcuCategoryService.deleteKcuCategory(id);
        } catch (Exception e) {
            resultData.setMessage(e.getMessage());
            resultData.setCode(ResultEmuns.ERROR.getCode());
        }
        return resultData;
    }

    @GetMapping("/listkcucategory")
    @ApiOperation("查询子一级分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "上级pid", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String")
    })
    public ResultData listKcuCategory(Integer pid,String system_code){
        return kcuCategoryService.listKcuCategoryByPidAndSystemCode(pid,system_code);
    }

    @GetMapping("/listkcucategoryall")
    @ApiOperation("查询所有分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "上级pid", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String")
    })
    public ResultData listKcuCategoryAll(Integer pid,String system_code){
        return kcuCategoryService.listKcuCategoryAll(pid,system_code);
    }

 /*   @GetMapping("/listkcucategoryidall")
    @ApiOperation("查询所有叶子目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "上级pid", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String")
    })
    public ResultData listKcuCategoryIdByLeaf(Integer pid,String system_code){
        return kcuCategoryService.listKcuCategoryIdByLeaf(pid,system_code);
    }*/

    @GetMapping("/getkcucategory")
    @ApiOperation("查询分类详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "分类id", required = true, paramType = "query", dataType = "Integer")
    })
    public ResultData getKcuCategory(Integer id){
        return kcuCategoryService.getKcuCategory(id);
    }


    @GetMapping("/listkcucategorybyConditions")
    @ApiOperation("条件查询分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "pid", value = "上级pid", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "热门类型", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name ="title", value = "分类标题", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String")
    })
    public ResultData listKcuCategoryByConditions(Integer pageNum, Integer pageSize,@RequestParam("pid") Integer pid,@RequestParam("system_code") String system_code,Integer type,String title){
        return kcuCategoryService.listKcuCategoryByConditions(pageNum,pageSize,pid,system_code,type,title);
    }

}
