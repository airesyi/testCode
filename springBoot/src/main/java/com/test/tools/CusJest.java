package com.test.tools;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.indices.DeleteIndex;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * auth: shi yi
 * create date: 2018/9/28
 */
@Slf4j
public class CusJest {
    @Autowired
    JestClient jestClient;

    public Object saveGoods(String indexName, String typeName, List<Object> object) {
        List<Index> indexList = transToBulkList(object);
        Bulk bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName).addAction(indexList).build();
        try {
            JestResult jestResult = jestClient.execute(bulk);
            String result = jestResult.getJsonString();
        } catch (IOException e) {
            log.error("error save to ES server", e);
        }
        return null;
    }

    public Object getGoods(String indexName, String typeName, String id) {
        Get get = new Get.Builder(indexName, id).type(typeName).build();
        try {
            JestResult result = jestClient.execute(get);
            if (result.isSucceeded()) {
                Object obj = result.getSourceAsObject(Object.class);
                return obj;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object searchGoods(String indexName, String typeName, String query) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);


        String from = "2016-09-01T00:00:00";
        String to = "2019-10-01T00:00:00";
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
//                .must(QueryBuilders.matchQuery())
//                .must(QueryBuilders.termsQuery("name", name))
                .must(QueryBuilders.rangeQuery("date").gte(from).lte(to));
        searchSourceBuilder.query(queryBuilder);
        String query1 = searchSourceBuilder.toString();


        Count count = new Count.Builder()
                .addIndex(indexName)
                .addType(typeName)
                .query(query)
                .build();
        Double counts = null;
        try {
            CountResult results = jestClient.execute(count);
            counts = results.getCount();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Count:{}",counts);

        return null;
    }

    public boolean deleteGoods(String indexName, String typeName, String id) {
        DocumentResult dr = null;
        try {
            Delete delete = new Delete.Builder(id).index(indexName).type(typeName).build();
            dr = jestClient.execute(delete);
            return dr.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteIndex(String indexName) {
        JestResult jr = null;
        try {
            DeleteIndex delete = new DeleteIndex.Builder(indexName).build();
            jr = jestClient.execute(delete);
            return jr.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteType(String indexName, String typeName) {
        JestResult jr = null;
        try {
            DeleteIndex delete = new DeleteIndex.Builder(indexName).type(typeName).build();
            jr = jestClient.execute(delete);
            return jr.isSucceeded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Index> transToBulkList(List<Object> objectList) {
        List<Index> indexList = new ArrayList<>(objectList.size());
        for (int i = 0; i < objectList.size(); i++) {
            Object obj = objectList.get(i);
            indexList.add(new Index.Builder(obj).build());
        }
        return indexList;
    }

}
