package com.manji.ackservice.controller.base;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.manji.ackservice.common.Es.global.Configure;
import com.manji.ackservice.common.Es.global.ElasticsearchClientUtils;
import com.manji.ackservice.common.Es.global.enums.CodeEnum;
import com.manji.ackservice.common.exception.BusinessDealException;
import com.manji.ackservice.common.model.BaseResult;
import com.manji.ackservice.common.utils.AppArticleQuery;
import com.manji.ackservice.common.utils.HttpClientUtil;
import com.manji.ackservice.common.utils.KeySerchBuider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;

/**
 * @author Administrator
 *
 */
@Controller
@Api(value = "/api-base", description = "base")
@RequestMapping("/api/base/")
public class BaseApiController {

	private final Logger logger1 = LoggerFactory.getLogger(this.getClass());


	public static Client client132 = null;

	public static Client client49 = null;


	@ResponseBody
	@ApiOperation(value = "", notes = "")
	@RequestMapping(value = "/startClient", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public BaseResult startClient(HttpServletRequest req) {
		BaseResult baseResult = new BaseResult(CodeEnum.SUCCESS.getCode(), "成功");
		try {
			ElasticsearchClientUtils.startClient();
		} catch (BusinessDealException e) {
			logger.error("业务处理异常， 错误信息：{}", e.getMessage());
			baseResult = new BaseResult(CodeEnum.BUSSINESS_HANDLE_ERROR.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常，{}", e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			baseResult = new BaseResult(CodeEnum.SYSTEM_ERROR.getCode(), "系统异常", sw.toString());
		}
		return baseResult;
	}


	@ResponseBody
	@ApiOperation(value = "", notes = "")
	@RequestMapping(value = "/stopClient", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public BaseResult stopClient(HttpServletRequest req) {
		BaseResult baseResult = new BaseResult(CodeEnum.SUCCESS.getCode(), "成功");
		try {
			ElasticsearchClientUtils.stopClient();
		} catch (BusinessDealException e) {
			logger.error("业务处理异常， 错误信息：{}", e.getMessage());
			baseResult = new BaseResult(CodeEnum.BUSSINESS_HANDLE_ERROR.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常，{}", e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			baseResult = new BaseResult(CodeEnum.SYSTEM_ERROR.getCode(), "系统异常", sw.toString());
		}
		return baseResult;
	}


	@ResponseBody
	@ApiOperation(value = "", notes = "")
	@RequestMapping(value = "/abc", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public BaseResult abc(HttpServletRequest req, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer size) {
		BaseResult baseResult = new BaseResult(CodeEnum.SUCCESS.getCode(), "成功");
		try {

			Settings settings = Settings.builder().put("cluster.name", "my-application")
					.put("xpack.security.transport.ssl.enabled", false)
					.put("xpack.security.user", "elastic:changeme")
					.put("client.transport.sniff", true).build();

			client132 = new PreBuiltXPackTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.0.49"), 9200));


			// 设置集群名称
			Settings settings49 = Settings.builder().put("cluster.name", "mj-es").build();

			// 创建client
			client49 = new PreBuiltTransportClient(settings49)
					.addTransportAddress(new InetSocketTransportAddress(
							InetAddress.getByName("192.168.0.49"), 9333));

			BoolQueryBuilder qb1 = QueryBuilders.boolQuery();
			qb1.must(KeySerchBuider.getChniseBulider("article_title", ""));

			//创建搜索条件
			SearchRequestBuilder requestBuider = client49.prepareSearch("article");
			requestBuider.setTypes("info");
			requestBuider.setSearchType(SearchType.QUERY_THEN_FETCH);
			//requestBuider.setQuery(qb1);

			requestBuider.setFrom((pageNum - 1) * size).setSize(size);

			System.out.println("参数json:{}" + requestBuider.toString());

			//执行查询结果
			SearchResponse searchResponse = requestBuider.get();
			SearchHits hits = searchResponse.getHits();
			System.out.println("结果:" + JSON.toJSONString(hits).toString());

			//录入到 132
			for (SearchHit searchHit : hits) {
				IndexRequestBuilder requestBuilder = client132.prepareIndex("article", "info", null);//设置索引名称，索引类型，id
				requestBuilder.setSource(JSON.toJSONString(searchHit.getSource()), XContentType.JSON).execute().actionGet();//创建索引
			}
		} catch (BusinessDealException e) {
			logger.error("业务处理异常， 错误信息：{}", e.getMessage());
			baseResult = new BaseResult(CodeEnum.BUSSINESS_HANDLE_ERROR.getCode(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常，{}", e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			baseResult = new BaseResult(CodeEnum.SYSTEM_ERROR.getCode(), "系统异常", sw.toString());
		}
		return baseResult;
	}


	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
	 * 商品查询
	 *
	 * @param req
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value = "商品查询", notes = "商品查询")
	@RequestMapping(value = "/queryArticle", method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Object queryArticle(HttpServletRequest req, AppArticleQuery query) {
		try {
			int from = (query.getPageNum() - 1) * query.getSize();
			StringBuffer sb = new StringBuffer("{\"query\": {\"bool\": {\"must\": [");
			// 关键字+分类ID
			if (!"".equals(query.getQueryStr()) && !"".equals(query.getCate_id())) {

				sb.append("{ \"match\": { \"article_category_index\": \"" + query.getQueryStr()
						+ "\" } },{ \"match\": { \"class_list\": \"" + query.getCate_id() + "\" } }");
			} else {
				if (!"".equals(query.getQueryStr())) {
					sb.append("{ \"match\": { \"article_category_index\": \"" + query.getQueryStr() + "\" } }");
				}
				if (!"".equals(query.getCate_id())) {

					sb.append("{ \"match\": { \"class_list\": \"" + query.getCate_id() + "\" } }");
				}
			}
			if (query.getShip_flag() != 0) {

				sb.append(",{ \"match\": { \"is_free\": \"" + query.getShip_flag() + "\" } }");
			}
			if (query.getSale_flag() != 0) {

				sb.append(",{ \"match\": { \"case_article_activity_type\": \"" + query.getSale_flag() + "\" } }");
			}
			if (!"".equals(query.getArea_code())) {

				sb.append(",{ \"match\": { \"article_distribution_area\": \"" + "1 " + query.getArea_code() + "\" } }");
			}
			sb.append("]");
			// 商品价格过滤
			sb.append(",\"filter\": [{ \"range\": { \"article_sell_price\": { \"gte\": \"" + query.getPrice_start() + "\"");
			if (0 != query.getPrice_end()) {
				sb.append(",\"lte\":\"" + query.getPrice_end() + "\" }}}]");
			} else {
				sb.append("}}}]");
			}
			sb.append("}}");
			// 排序方式
			sb.append(",\"sort\": {");
			switch (query.getSort_flag()) {
				case 1:
					sb.append("\"article_order_times\": \"desc\"");
					break;
				case 2:
					sb.append("\"article_sell_price\":\"asc\"");
					break;
				case 3:
					sb.append("\"article_sell_price\":\"desc\"");
					break;
				default:
					sb.append("\"article_review_score\": \"desc\"");
					break;
			}
			sb.append("}");
			// 分页
			sb.append(",\"size\": " + query.getSize() + ",\"from\": " + from + "}}");


			System.out.println(sb.toString() + "++++++++++++++++++++++");


			String esReturn = HttpClientUtil.post(Configure.getEsUrl() + "article" + "/_search", sb.toString().replace("must\": [,", "must\": ["), "application/json", null);

			JSONObject jsonObj = JSON.parseObject(esReturn);
			JSONObject result = (JSONObject) jsonObj.get("hits");

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常，{}", e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
		}
		return null;
	}


	@PostMapping("/add")
	@ResponseBody
	public String addBase(@RequestBody BaseModel baseModel) {

		try {
			BaseSearchAddBiz.addHotSearchWords(baseModel);
		} catch (Exception e) {
			e.printStackTrace();
		}


		return null;
	}


	@RequestMapping(value = "/querytest", method = RequestMethod.GET)
	public String queryTest() {


		return null;
	}

}
