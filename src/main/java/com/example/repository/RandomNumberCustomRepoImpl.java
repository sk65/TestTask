package com.example.repository;


import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.ValueCount;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by Yefimov Yevhen on 10.11.2020
 * A repository class that implements {@link RandomNumberCustomRepo}.
 */
@Repository
public class RandomNumberCustomRepoImpl implements RandomNumberCustomRepo {

    private final RestHighLevelClient client;

    public RandomNumberCustomRepoImpl(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     *  @see RandomNumberCustomRepo#findAvgLatency()
     */
    @Override
    public double findAvgLatency() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .aggregation(AggregationBuilders
                        .avg("agg")
                        .field("latency"));

        SearchRequest searchRequest = new SearchRequest("numbers").source(searchSourceBuilder);

        double result = 0;
        try {
            SearchResponse sr = client.search(searchRequest, RequestOptions.DEFAULT);
            Avg agg = sr.getAggregations().get("agg");
            result = agg.getValue();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     *  @see RandomNumberCustomRepo#findCountRecords()
     */
    @Override
    public double findCountRecords() {
        ValueCountAggregationBuilder aggregation =
                AggregationBuilders
                        .count("agg")
                        .field("found");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .aggregation(aggregation);

        SearchRequest searchRequest = new SearchRequest("numbers").source(searchSourceBuilder);

        double result = 0;
        try {
            SearchResponse sr = client.search(searchRequest, RequestOptions.DEFAULT);
            ValueCount agg = sr.getAggregations().get("agg");
            result = agg.getValue();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *  @see RandomNumberCustomRepo#findCountRecordsByFound(boolean)
     */
    @Override
    public double findCountRecordsByFound(boolean found) {
        ValueCountAggregationBuilder aggregation =
                AggregationBuilders
                        .count("agg")
                        .field("found");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.matchQuery("found", found))
                .aggregation(aggregation);

        SearchRequest searchRequest = new SearchRequest("numbers").source(searchSourceBuilder);

        double result = 0;
        try {
            SearchResponse sr = client.search(searchRequest, RequestOptions.DEFAULT);
            ValueCount agg = sr.getAggregations().get("agg");
            result = agg.getValue();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *  @see RandomNumberCustomRepo#findPopularNumbers()
     */
    @Override
    public List<Long> findPopularNumbers() {
        List<Long> result = new ArrayList<>(10);
        TermsAggregationBuilder globalAggregationBuilder = AggregationBuilders
                .terms("numbers")
                .field("number").size(10);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .aggregation(globalAggregationBuilder);

        SearchRequest searchRequest = new SearchRequest("numbers").source(searchSourceBuilder);
        try {
            SearchResponse sr = client.search(searchRequest, RequestOptions.DEFAULT);

            Terms numbers = sr.getAggregations().get("numbers");

            result = numbers.getBuckets()
                    .stream()
                    .flatMapToLong(x -> LongStream.of((Long) x.getKey()))
                    .boxed()
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
