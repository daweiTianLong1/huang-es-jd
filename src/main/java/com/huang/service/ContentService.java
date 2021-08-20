package com.huang.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//业务编写
@Service
public class ContentService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 获取这些数据实现搜索功能
    public List<Map<String, Object>> searchPage(String keyword, int pageNo, int pageSize) throws IOException {
//    public List<Map<String, Object>> searchPage(String keyword) throws IOException {
        if (pageNo <= 1) {
            pageNo = 1;
        }

        //条件搜索
        SearchRequest searchRequest = new SearchRequest("hibysong_index");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);
//        精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        QueryBuilder queryBuilders = QueryBuilders.boolQuery()
                .should(QueryBuilders.wildcardQuery("title","*" + keyword + "*"))
                .should(QueryBuilders.matchQuery("artist",keyword))
                .should(QueryBuilders.matchQuery("album",keyword))
                .should(QueryBuilders.matchQuery("lyric",keyword));
        sourceBuilder.query(queryBuilders);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            list.add(documentFields.getSourceAsMap());
        }
        return list;
    }


    // 2.获取这些数据实现搜索高亮功能
//    public List<Map<String, Object>> searchPageHighlightBuilder(String keyword, int pageNo, int pageSize) throws IOException {
    public List<Map<String, Object>> searchPageHighlightBuilder(String keyword) throws IOException {
//        if (pageNo <= 1) {
//            pageNo = 1;
//        }

        //条件搜索
        SearchRequest searchRequest = new SearchRequest("hibysong_index");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
//        sourceBuilder.from(pageNo);
//        sourceBuilder.size(pageSize);
        //精准匹配
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        QueryBuilder queryBuilders = QueryBuilders.boolQuery()
                .should(QueryBuilders.wildcardQuery("title","*" + keyword + "*"))
                .should(QueryBuilders.wildcardQuery("artist",keyword))
                .should(QueryBuilders.wildcardQuery("album","*" + keyword + "*"))
                .should(QueryBuilders.matchQuery("lyric",keyword));
        sourceBuilder.query(queryBuilders);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false); //多个高亮显示
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);


        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //解析结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap(); //原来的结果!
            //解析高亮的字段，将原来的字段换为我们高亮的字段即可!
            if (title != null) {
                Text[] fragments = title.fragments();
                String n_title = "";
                for (Text text : fragments) {
                    n_title += text;
                    sourceAsMap.put("title", n_title); //高亮字段替换掉原来的内容即可!
                }
                list.add(sourceAsMap);
            }
        }
        return list;
    }






}
