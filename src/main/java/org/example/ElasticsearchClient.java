package org.example;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class    ElasticsearchClient {
    private final RestHighLevelClient client;

    public ElasticsearchClient() {
        client = new RestHighLevelClient(
                RestClient.builder(new org.apache.http.HttpHost("localhost", 9200, "http"))
        );
    }

    public SearchResponse fetchLogs(String index, int size) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        sourceBuilder.size(size);
        searchRequest.source(sourceBuilder);

        return client.search(searchRequest, org.elasticsearch.client.RequestOptions.DEFAULT);
    }

    public void close() throws IOException {
        client.close();
    }
}
