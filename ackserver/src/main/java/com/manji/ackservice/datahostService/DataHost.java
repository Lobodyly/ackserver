package com.manji.ackservice.datahostService;

import com.manji.ackservice.common.model.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/7/31
 * Time:16:15
 */
@FeignClient(value = "DATAHOST")
public interface DataHost {

    @GetMapping("/other/getSessionInfo")
    @ApiOperation(value = "根据sessionId查询信息")
    public ResultData getSessionInfo(@RequestParam("sessionId") String sessionId);
}
