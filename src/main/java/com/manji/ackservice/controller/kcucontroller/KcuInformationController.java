package com.manji.ackservice.controller.kcucontroller;


import com.manji.ackservice.Service.kcuservice.IKcuInformationService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/24
 * Time:9:42
 */
@RestController
@RequestMapping("/kcuinformation")
@Api(description = "帮助中心知识点管理的控制器")
public class KcuInformationController {
    @Autowired
    private IKcuInformationService kcuInformationService;

    @PostMapping("/add")
    @ApiOperation(value="添加知识点", notes="添加知识点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kcucategory_id",value = "分类id",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "title",value = "知识点标题",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "content",value = "知识点内容",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "add_user_name",value = "添加人",required = false,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "state",value = "启动状态",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "remark",value = "备注",required = false,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "type",value = "热门状态",required = true,paramType = "query",dataType = "Integer")
    })
    public ResultData addKcuInformation (Integer kcucategory_id, String title, String content, String add_user_name, Integer state,String remark,Integer type) {
        ResultData resultData = new ResultData(ResultEmuns.ERROR.getCode(),"添加成功");
        try {
            return kcuInformationService.addKcuInformation(kcucategory_id,title,content,add_user_name,state,remark,type);
        } catch (Exception e) {
            resultData.setCode("00001");
            resultData.setMessage("添加失败,"+e.getMessage());
        }
        return resultData;
    }

    @PostMapping("/update")
    @ApiOperation(value="修改知识点", notes="修改知识点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "知识点id",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "kcucategory_id",value = "分类id",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "title",value = "知识点标题",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "content",value = "知识点内容",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "update_user_name",value = "修改人",required = false,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "state",value = "启动状态",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "remark",value = "备注",required = false,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "type",value = "热门状态",required = true,paramType = "query",dataType = "Integer")
    })
    public ResultData updateKcuInformation (Integer id,Integer kcucategory_id, String title, String content, String update_user_name, Integer state,String remark,Integer type) {
        ResultData resultData = new ResultData(ResultEmuns.ERROR.getCode(),"修改成功");
        try {
            return kcuInformationService.updateKcuInformation(id,kcucategory_id,title,content,update_user_name,state,remark,type);
        } catch (Exception e) {
            resultData.setCode("00001");
            resultData.setMessage("修改失败,"+e.getMessage());
        }
        return resultData;
    }

    @GetMapping("/updatestate")
    @ApiOperation(value="修改启动状态", notes="修改启动状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "知识点id",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "update_user_name",value = "修改人",required = false,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "state",value = "启动状态",required = true,paramType = "query",dataType = "Integer"),
    })
    public ResultData updateKcuInformationState (Integer id,String update_user_name, Integer state) {
        ResultData resultData = new ResultData(ResultEmuns.ERROR.getCode(),"修改成功");
        try {
            return kcuInformationService.updateState(id,state,update_user_name);
        } catch (Exception e) {
            resultData.setCode("00001");
            resultData.setMessage("修改失败,"+e.getMessage());
        }
        return resultData;
    }

    @GetMapping("/updatetype")
    @ApiOperation(value="修改热门状态", notes="修改热门状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "知识点id",required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "update_user_name",value = "修改人",required = false,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "type",value = "热门状态",required = true,paramType = "query",dataType = "Integer"),
    })
    public ResultData updateKcuInformationType (Integer id,String update_user_name, Integer type) {
        ResultData resultData = new ResultData(ResultEmuns.ERROR.getCode(),"修改成功");
        try {
            return kcuInformationService.updateType(id,type,update_user_name);
        } catch (Exception e) {
            resultData.setCode("00001");
            resultData.setMessage("修改失败,"+e.getMessage());
        }
        return resultData;
    }

    @GetMapping("/delete")
    @ApiOperation("删除知识点")
    @ApiImplicitParam(name = "ids", value = "知识点ID", required = true,paramType = "query",  dataType = "String")

    public ResultData deleteKSystem(String ids){
        return kcuInformationService.deleteKcuInformation(ids);
    }


    @GetMapping("/getkcuinformationbyid")
    @ApiOperation("查看知识点")
    @ApiImplicitParam(name = "id", value = "知识点ID", required = true,paramType = "query",  dataType = "Integer")
    public  ResultData getInformationById(Integer id){
        return kcuInformationService.getKcuInformationById(id);
    }


    @PostMapping("/listkcuinformation")
    @ApiOperation("获取所有信息分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "level", value = "级别", required = false,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "kcucategoryid", value = "分类ID", required = false,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "title", value = "标题", required = false,paramType = "query",  dataType = "String"),
            @ApiImplicitParam(name = "state", value = "启动状态", required = false,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "热门状态", required = false,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "system_code", value = "系统code", required = true,paramType = "query",  dataType = "String")
    })
    public ResultData ListInformation(Integer pageNum, Integer pageSize,Integer kcucategoryid,Integer level,Integer state,Integer type,String title,String system_code){
        return kcuInformationService.listKcuInformation(pageNum, pageSize,kcucategoryid,level,state,type,system_code,title);
    }
}
