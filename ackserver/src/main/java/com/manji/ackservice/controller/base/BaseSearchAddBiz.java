package com.manji.ackservice.controller.base;

import com.alibaba.fastjson.JSON;
import com.manji.ackservice.common.Es.global.Configure;
import com.manji.ackservice.common.Es.global.ElasticsearchClientUtils;
import com.manji.ackservice.controller.hotsearch.RecordModel;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

/**
 * Created by lutao on 2018/8/1.
 * 数据添加  只是库索引
 */
public class BaseSearchAddBiz {




   //添加  修改
    public static void addHotSearchWords(BaseModel model) throws Exception{

          //添加






            //完全匹配，判断是否存在，如果不存在，才存入
             BoolQueryBuilder qb1 = QueryBuilders.boolQuery();
            //关键字处理
        if(!StringUtils.isBlank(model.getId())) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
            qb1.must(QueryBuilders.matchPhraseQuery("id", model.getId()));
        }


//            if(!StringUtils.isBlank(model.getContent())) {
//                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
//                qb1.must(QueryBuilders.matchPhraseQuery("content", model.getContent()));
//            }
//            if(!StringUtils.isBlank(model.getTitle())) {
//                qb1.must(QueryBuilders.matchPhraseQuery("title",  model.getTitle()));
//            }
//            if(!StringUtils.isBlank(model.getState())) {
//                qb1.must(QueryBuilders.matchPhraseQuery("state", model.getState()));
//            }
//            if(!StringUtils.isBlank(model.getType())) {
//                qb1.must(QueryBuilders.matchPhraseQuery("type", model.getType()));
//            }
//            if(!StringUtils.isBlank(model.getView_number())) {
//                qb1.must(QueryBuilders.matchPhraseQuery("view_number", model.getView_number()));
//            }
//            if(!StringUtils.isBlank(String.valueOf(model.getAdd_timestamp()))){
//                qb1.must(QueryBuilders.matchPhraseQuery("add_timestamp", model.getAdd_timestamp()));
//            }
//            if(!StringUtils.isBlank(String.valueOf(model.getUpd_timestamp()))) {
//                qb1.must(QueryBuilders.matchPhraseQuery("upd_timestamp", model.getUpd_timestamp()));
//            }
//            if(!StringUtils.isBlank(model.getIndexType().trim())){
//                qb1.must(QueryBuilders.matchPhraseQuery("indexType", model.getIndexType().trim()));
//            }
//            qb1.must(QueryBuilders.matchPhraseQuery("title",  model.getTitle()));
//            qb1.must(QueryBuilders.matchPhraseQuery("state", model.getState()));
//            qb1.must(QueryBuilders.matchPhraseQuery("type", model.getType()));
//            qb1.must(QueryBuilders.matchPhraseQuery("view_number", model.getView_number()));
//            qb1.must(QueryBuilders.matchPhraseQuery("add_timestamp", model.getAdd_timestamp()));
//            qb1.must(QueryBuilders.matchPhraseQuery("upd_timestamp", model.getUpd_timestamp()));
//            qb1.must(QueryBuilders.matchPhraseQuery("indexType", model.getIndexType().trim()));
            //创建连接
            TransportClient client = ElasticsearchClientUtils.getTranClinet();
            SearchRequestBuilder requestBuider = client.prepareSearch(Configure.getES_Ack());
            requestBuider.setTypes("info");
            requestBuider.setQuery(qb1);
            SearchResponse res = requestBuider.get();
            SearchHits hits = res.getHits();

               String id=model.getId();

                //连接服务端录入
                IndexRequestBuilder requestBuilder = client.prepareIndex(Configure.getES_Ack(), "info",id);//设置索引名称，索引类型，id
                requestBuilder.setSource(JSON.toJSONString(model), XContentType.JSON).execute().actionGet();//创建索引


    }


    }












