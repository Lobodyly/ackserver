package com.manji.ackservice.controller.kcucontroller;

import com.manji.ackservice.Service.kcuservice.IKcuApiService;
import com.manji.ackservice.common.model.ApiResultData;
import com.manji.ackservice.model.kcumodel.Feedback;
import com.manji.ackservice.model.kcumodel.KcuFeedback;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:16:34
 */
@RestController
@RequestMapping("/api")
@Api(description = "对PC接口的控制器")
public class ApiPCController {
    @Autowired
    private IKcuApiService kcuApiService;

    @GetMapping("/listinformation")
    @ApiOperation("知识点问题列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name = "kcucategoryid", value = "目录ID", required = false,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name ="system_code", value = "系统code", required = false, paramType = "query", dataType = "String")
    })
    public ApiResultData listInformation(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, Integer kcucategoryid, String system_code){
        return kcuApiService.listKcuInformationNew(pageNum,pageSize,kcucategoryid,system_code);
    }


    @GetMapping("/listhotcategory")
    @ApiOperation("首页热门分类查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String")
    })
    public ApiResultData listHotCategory(String system_code){
        return kcuApiService.listHotCategory(system_code);
    }

    @GetMapping("/listcategoryall")
    @ApiOperation("查询所有目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String")
    })
    public ApiResultData listcategoryall(String system_code){
        return kcuApiService.listCategoryAll(system_code);
    }

    @GetMapping("/listcategorybypid")
    @ApiOperation("根据当前目录查询子一级目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="id", value = "目录id", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String")
    })
    public ApiResultData listCategoryByPid(Integer id,String system_code){
        return kcuApiService.listCategoryByPid(id,system_code);
    }

    @GetMapping("/getinformationbyid")
    @ApiOperation("根据知识点id查询知识点详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="sessionId", value = "sessionId", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name ="id", value = "知识点id", required = true, paramType = "query", dataType = "Integer")
    })
    public ApiResultData getInformationById(String sessionId,Integer id){
        return kcuApiService.getInformationById(sessionId,id);
    }

    @GetMapping("/getkcufeedbackset")
    @ApiOperation("根据系统code查询反馈配置内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String")
    })
    public ApiResultData getKcuFeedbackSet(String system_code){
        return kcuApiService.getKcuFeedbackSet(system_code);
    }

    @PostMapping("/addfeedback")
    @ApiOperation("新增反馈")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name ="sessionId", value = "sessionId", required = false, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "feedback_content", value = "反馈内容", required = false,paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "kcuinformation_title", value = "知识点标题", required = true,paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "kcuinformation_id", value = "知识点id", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "state", value = "解决状态", required = true, paramType = "query", dataType = "Integer")
//    })
    public ApiResultData addFeedback(String sessionId,Integer state, String feedback_content,String system_code,Integer kcuinformation_id,String kcuinformation_title){
        KcuFeedback kcuFeedback = new KcuFeedback();
        Date date = new Date();
        kcuFeedback.setAdd_time(date);
        kcuFeedback.setFeedback_content(feedback_content);
        kcuFeedback.setKcuinformation_id(kcuinformation_id);
        kcuFeedback.setKcuinformation_title(kcuinformation_title);
        kcuFeedback.setState(state);
        kcuFeedback.setSystem_code(system_code);
        return kcuApiService.addKcuFeedback(sessionId,kcuFeedback);
    }

    @PostMapping("/addfeedbackforpc")
    @ApiOperation("新增反馈")
    public ApiResultData addFeedback(@RequestBody Feedback feedback){
        KcuFeedback kcuFeedback = new KcuFeedback();
        Date date = new Date();
        kcuFeedback.setAdd_time(date);
        kcuFeedback.setFeedback_content(feedback.getFeedback_content());
        kcuFeedback.setKcuinformation_id(feedback.getKcuinformation_id());
        kcuFeedback.setKcuinformation_title(feedback.getKcuinformation_title());
        kcuFeedback.setState(feedback.getState());
        kcuFeedback.setSystem_code(feedback.getSystem_code());
        return kcuApiService.addKcuFeedback(feedback.getSessionId(),kcuFeedback);
    }

    @GetMapping("/listkcuselfservice")
    @ApiOperation("根据系统code查询自助服务配置内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String")
    })
    public ApiResultData listKcuSelfService(String system_code){
        return kcuApiService.listKcuSelfService(system_code);
    }


}
