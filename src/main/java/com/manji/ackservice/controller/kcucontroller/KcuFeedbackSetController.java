package com.manji.ackservice.controller.kcucontroller;

import com.manji.ackservice.Service.kcuservice.IKcuFeedbackSetService;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.model.kcumodel.KcuFeedbackSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/25
 * Time:15:32
 */
@RestController
@RequestMapping("/kcufeedbackset")
@Api(description = "帮助中心反馈内容配置管理的控制器")
public class KcuFeedbackSetController {
    @Autowired
    private IKcuFeedbackSetService kcuFeedbackSetService;


    @PostMapping("/set")
    @ApiOperation("设置反馈内容配置")
    public ResultData updateKcuFeedbackSet(@RequestBody List<KcuFeedbackSet> kcuFeedbackSetList) {
        return kcuFeedbackSetService.setKcuFeedbackSet(kcuFeedbackSetList);
    }

    @PostMapping("/delete")
    @ApiOperation("删除反馈内容配置")
    @ApiImplicitParam(name = "id", value = "反馈内容配置id", required = true, paramType = "query", dataType = "Integer")
    public ResultData deleteKcuFeedbackSet(Integer id) {
        return kcuFeedbackSetService.deleteKcuFeedbackSet(id);
    }

    @PostMapping("/listKcuFeedbackset")
    @ApiOperation("条件反馈内容配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="system_code", value = "系统code", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "feedback_content", value = "反馈内容", required = false,paramType = "query", dataType = "String")
    })
    public ResultData listKcuFeedbackSet(String feedback_content,String system_code) {
        KcuFeedbackSet kcuFeedbackSet = new KcuFeedbackSet();
        kcuFeedbackSet.setFeedback_content(feedback_content);
        kcuFeedbackSet.setIs_delete(0);
        kcuFeedbackSet.setSystem_code(system_code);
        return kcuFeedbackSetService.listKcuFeedbackSet(kcuFeedbackSet);
    }
}
