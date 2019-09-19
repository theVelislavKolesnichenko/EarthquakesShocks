package com.example.repository.base;

import com.example.models.elasticsearch.Activity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Repository
public class ActivityRepository {

    private final String INDEX = "activity";
    private final String TYPE = "activity";

    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper;

    @Autowired
    public ActivityRepository(RestHighLevelClient restHighLevelClient, ObjectMapper objectMapper) {
        this.restHighLevelClient = restHighLevelClient;
        this.objectMapper = objectMapper;
    }

    public boolean add(Activity activity){

        boolean sucsessfull = false;

        try {
            activity.setId(UUID.randomUUID().toString());
            BulkRequest bulkRequest = new BulkRequest();
            IndexRequest indexRequest = new IndexRequest(INDEX,TYPE, activity.getId()).source(objectMapper.convertValue(activity, Map.class));
            bulkRequest.add(indexRequest);

            restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

            sucsessfull = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sucsessfull;
    }

    public ArrayList<Activity> findAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ArrayList<Activity> activites = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Gson gson = new Gson();
        for (SearchHit hit : searchHits) {
            Activity doc = gson.fromJson(hit.getSourceAsString(), Activity.class);
            doc.setId(hit.getId());
            activites.add(doc);
        }
        return activites;
    }

    public ArrayList<Activity> findByPage(int from, int size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchAllQuery()).from(from).size(size);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ArrayList<Activity> activites = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Gson gson = new Gson();
        for (SearchHit hit : searchHits) {
            Activity doc = gson.fromJson(hit.getSourceAsString(), Activity.class);
            doc.setId(hit.getId());
            activites.add(doc);
        }
        return activites;
    }

    public ArrayList<Activity> find(int from, int size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.commonTermsQuery("username", "tet@email.email")).from(from).size(size);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ArrayList<Activity> activites = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Gson gson = new Gson();
        for (SearchHit hit : searchHits) {
            Activity doc = gson.fromJson(hit.getSourceAsString(), Activity.class);
            doc.setId(hit.getId());
            activites.add(doc);
        }
        return activites;
    }

    public ArrayList<Activity> findFull(LocalDate dateFrom, LocalDate dateTo, String search, int from, int size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("date").from(dateFrom).to(dateTo))
                .must(QueryBuilders.simpleQueryStringQuery(search))).from(from).size(size);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ArrayList<Activity> activites = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Gson gson = new Gson();
        for (SearchHit hit : searchHits) {
            Activity doc = gson.fromJson(hit.getSourceAsString(), Activity.class);
            doc.setId(hit.getId());
            activites.add(doc);
        }

        return activites;
    }

    public ArrayList<Activity> findPerfect(LocalDate dateFrom, LocalDate dateTo, String search, int from, int size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);

        String newSearch = search.substring(1,search.length()-1);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("date").from(dateFrom).to(dateTo))
                .must(QueryBuilders.multiMatchQuery(newSearch).type(MatchQuery.Type.PHRASE))).from(from).size(size);
                //.must(QueryBuilders.matchPhraseQuery("type", newSearch))).from(from).size(size);
                //.must(QueryBuilders.matchQuery("_all", search))).from(from).size(size);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(newSearch);
        ArrayList<Activity> activites = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Gson gson = new Gson();
        for (SearchHit hit : searchHits) {
            Activity doc = gson.fromJson(hit.getSourceAsString(), Activity.class);
            doc.setId(hit.getId());
            activites.add(doc);
        }

        return activites;
    }

    public ArrayList<Activity> findFullKeyValue(LocalDate dateFrom, LocalDate dateTo, String field, String search, int from, int size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("date").from(dateFrom).to(dateTo))
                .must(QueryBuilders.commonTermsQuery(field, search))).from(from).size(size);
                //.must(QueryBuilders.termQuery(field, search))).from(from).size(size);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ArrayList<Activity> activites = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Gson gson = new Gson();
        for (SearchHit hit : searchHits) {
            Activity doc = gson.fromJson(hit.getSourceAsString(), Activity.class);
            doc.setId(hit.getId());
            activites.add(doc);
        }

        return activites;
    }

    public ArrayList<Activity> findPerfectKeyValue(LocalDate dateFrom, LocalDate dateTo, String field, String search, int from, int size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("date").from(dateFrom).to(dateTo))
                .must(QueryBuilders.termQuery(field ,search))).from(from).size(size);
                //.must(QueryBuilders.commonTermsQuery(field ,search))).from(from).size(size);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ArrayList<Activity> activites = new ArrayList<>();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        Gson gson = new Gson();
        for (SearchHit hit : searchHits) {
            Activity doc = gson.fromJson(hit.getSourceAsString(), Activity.class);
            doc.setId(hit.getId());
            activites.add(doc);
        }

        return activites;
    }
}
