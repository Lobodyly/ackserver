package com.manji.ackservice.controller.kcucontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.manji.ackservice.Service.kcuservice.IKcuSelfService;
import com.manji.ackservice.common.model.ApiResultData;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.model.kcumodel.KcuSelfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/31
 * Time:14:42
 */
@RestController
@RequestMapping("/kcuselfservice")
@Api(description = "自助服务配置管理的控制器")
public class KcuSelfServiceController {
    @Autowired
    private IKcuSelfService kcuSelfServiceImpl;

    @PostMapping("/set")
    @ApiOperation(value="自助服务配置", notes="")
    public ResultData setKcuSelfService(@RequestBody List<KcuSelfService> model) {
        ResultData resultData = new ResultData();
        try {
            return kcuSelfServiceImpl.setKcuSelfService(model);
        } catch (Exception e) {
            resultData.setCode(ResultEmuns.ERROR.getCode());
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/delete")
    @ApiOperation(value="状态禁用自助服务配置", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "id",required = true,paramType = "query",dataType = "Integer"),
    })
    public ResultData deleteKcuSelfService(Integer id) {
        return kcuSelfServiceImpl.deleteKcuSelfService(id,1);
    }


    @GetMapping("/listkcuselfservice")
    @ApiOperation("根据系统code查询自助服务配置内容")
    public ResultData listKcuSelfService(){
        return kcuSelfServiceImpl.listKcuSelfService();
    }
}
