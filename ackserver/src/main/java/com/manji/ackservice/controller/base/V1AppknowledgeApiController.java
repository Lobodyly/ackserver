package com.manji.ackservice.controller.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manji.ackservice.common.Es.global.Configure;
import com.manji.ackservice.common.model.ApiResultData;
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
 * Created by lutao on 2018/8/2.
 * app 知识中心搜索
 */
@Controller
@Api(value = "/app-knowledge", description = "一期接口APP")
@RequestMapping("/app/knowledge")
public class V1AppknowledgeApiController{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value = "query",method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE})
     public ApiResultData queryAppBase(HttpServletRequest req, PcBaseQuery query){

        ApiResultData apiResultData=new ApiResultData();
        try{
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


            // 商品价格过滤
//         sb.append(",\"filter\": [{ \"range\": { \"article_sell_price\": { \"gte\": \"" + query.getPrice_start() + "\"");
//         if (0 != query.getPrice_end()) {
//             sb.append(",\"lte\":\"" + query.getPrice_end() + "\" }}}]");
//         } else {
//             sb.append("}}}]");
//         }
//         sb.append("}}");
            // 排序方式
//        sb.append(",\"sort\": {");
//        switch (1) {
//            case 1:
//                sb.append("\"view_number\": \"desc\"");
//                break;
//             case 2:
//                 sb.append("\"view_number\":\"asc\"");
//                 break;
//            case 3:
//                 sb.append("\"view_number\":\"desc\"");
//                 break;
//            default:
//                 sb.append("\"view_number\": \"desc\"");
//                 break;
//         }
//         sb.append("}");
            sb.append("]}}]}}");
            // 分页
            sb.append(",\"size\": " + query.getSize() + ",\"from\": " + from + "}");


            System.out.println(sb.toString()+"+________________________________");

            String esReturn = HttpClientUtil.post(Configure.getEsUrl()+"knowledge_base_v20180731/info"+"/_search", sb.toString().replace("must\": [,", "must\": ["), "application/json", null);
            System.out.println(esReturn+"----------------------------");
            JSONObject jsonObj = JSON.parseObject(esReturn);
            JSONObject result = (JSONObject) jsonObj.get("hits");
            System.out.println(result+"++++++++++++++++++++++++++++++++++DSD+");
            if(query.getQueryStr().length() >= 2) {
                BaseRecordModel wordsModel = new BaseRecordModel();
                wordsModel.setContent(query.getQueryStr());
                wordsModel.setDevice("app");
                wordsModel.setIndexType(query.getIndexType());
                HotSearchAddBiz.addHotSearchWords(wordsModel);
            }

            apiResultData.setData(result);
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常非常，{}", e.getMessage());
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            apiResultData=new ApiResultData(954200,"BUG来了  不要在请求了    不要在请求了");
        }
        return apiResultData;
    }











}
