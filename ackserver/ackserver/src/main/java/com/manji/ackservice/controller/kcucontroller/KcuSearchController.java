package com.manji.ackservice.controller.kcucontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manji.ackservice.common.Es.global.Configure;
import com.manji.ackservice.common.model.ApiResultData;
import com.manji.ackservice.common.model.ResultData;
import com.manji.ackservice.common.model.ResultEmuns;
import com.manji.ackservice.common.utils.HttpClientUtil;
import com.manji.ackservice.common.utils.PcBaseQuery;
import com.manji.ackservice.controller.hotsearch.BaseRecordModel;
import com.manji.ackservice.controller.hotsearch.HotSearchAddBiz;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created with IDEA
 * author:LuoYu
 * Date:2018/8/9
 * Time:11:17
 */
@Controller
@Api(value = "/search", description = "搜索接口")
@RequestMapping("/search")
public class KcuSearchController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResultData queryAppBase(HttpServletRequest req, PcBaseQuery query) {
        ResultData resultData = new ResultData(ResultEmuns.SUCCESS.getCode(),"查询成功");
        try {
            int from = (query.getPageNum() - 1) * query.getSize();
            StringBuffer sb = new StringBuffer("{\"query\": {\"bool\": {\"should\": [{\"bool\":{\"must\":[");

            // 内容+标题

            if (!"".equals(query.getQueryStr())) {

                sb.append("{ \"match\": { \"title\": \"" + query.getQueryStr() + "\" }}");
            }


            //索引类型 shop   Buyer
            if (!"".equals(query.getIndexType())) {

                sb.append(",{ \"match\": { \"indexType\": \"" + query.getIndexType() + "\" } }");
            }


            sb.append("]}},{\"bool\":{\"must\":[");


            if (!"".equals(query.getQueryStr())) {

                sb.append("{ \"match\": { \"content\": \"" + query.getQueryStr() + "\" }}");
            }


            //索引类型 shop   Buyer
            if (!"".equals(query.getIndexType())) {

                sb.append(",{ \"match\": { \"indexType\": \"" + query.getIndexType() + "\" } }");
            }

            sb.append("]}}]}}");
            // 分页
            sb.append(",\"size\": " + query.getSize() + ",\"from\": " + from + "}");


            System.out.println(sb.toString() + "+________________________________");

            String esReturn = HttpClientUtil.post(Configure.getEsUrl() + "knowledge_base_v20180731/info" + "/_search", sb.toString().replace("must\": [,", "must\": ["), "application/json", null);
            System.out.println(esReturn + "----------------------------");
            JSONObject jsonObj = JSON.parseObject(esReturn);
            JSONObject result = (JSONObject) jsonObj.get("hits");
            System.out.println(result + "++++++++++++++++++++++++++++++++++DSD+");
            if (query.getQueryStr().length() >= 2) {
                BaseRecordModel wordsModel = new BaseRecordModel();
                wordsModel.setContent(query.getQueryStr());
                wordsModel.setDevice("app");
                wordsModel.setIndexType(query.getIndexType());
                HotSearchAddBiz.addHotSearchWords(wordsModel);
            }

            resultData.push("result",result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常非常，{}", e.getMessage());
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            resultData = new ResultData("954200","BUG来了  不要在请求了    不要在请求了");
        }
        return resultData;
    }
}
