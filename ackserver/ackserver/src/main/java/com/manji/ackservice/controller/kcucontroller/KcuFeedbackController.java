package com.manji.ackservice.controller.kcucontroller;

import com.manji.ackservice.Service.kcuservice.IKcuFeedbackService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.kcumodel.KcuFeedback;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:14:43
 */
@RestController
@RequestMapping("/kcufeedback")
@Api(description = "帮助中心反馈管理的控制器")
public class KcuFeedbackController {
    @Autowired
    private IKcuFeedbackService kcuFeedbackService;


    @PostMapping("/listkcufeedback")
    @ApiOperation("条件查询反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true,paramType = "query",  dataType = "Integer"),
            @ApiImplicitParam(name ="system_code", value = "系统code", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "feedback_content", value = "反馈内容", required = false,paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "kcuinformation_title", value = "知识点标题", required = false,paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "state", value = "解决状态", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "start_time", value = "起止时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "end_time", value = "终止时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "kcuinformation_id", value = "知识点id", required = false, paramType = "query", dataType = "Integer")
    })
    public ResultData listKcuCategoryAll(@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize") Integer pageSize, String kcuinformation_title, Integer state, String system_code, String feedback_content, String start_time, String end_time, Integer kcuinformation_id){
        return kcuFeedbackService.listKcuFeedback(pageNum,pageSize,kcuinformation_title,state,system_code,feedback_content,start_time,end_time,kcuinformation_id);
    }
}
