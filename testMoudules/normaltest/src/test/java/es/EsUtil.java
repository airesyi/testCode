package es;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Date;

/**
 * auth: shi yi
 * create date: 2018/9/18
 */
public class EsUtil {
    /**
     * 获取client句柄
     *
     * @return
     * @throws Exception
     */
    private static Client getClient() throws Exception {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("172.16.38.30"), 9300));
        return client;
    }

    /**
     * 创建索引文件
     */
    @Test
    public void createIndexFile() throws Exception {
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("settings")
                .field("number_of_shards", 1)
                .field("number_of_reolicas", 0)
                .endObject()
                .endObject()
                .startObject()
                .startObject("type_name")
                .startObject("properties")
                .startObject("type").field("type", "string").field("store", "yes").endObject()
                .startObject("eventCount").field("type", "long").field("store", "yes").endObject()
                .startObject("eventDate").field("type", "date").field("format", "dateOptionalTime").field("store", "yes").endObject()
                .startObject("message").field("type", "string").field("index", "not_analyzed").field("store", "yes").endObject()
                .endObject()
                .endObject()
                .endObject();

        CreateIndexRequestBuilder builder = getClient().admin().indices().prepareCreate("index_name").setSource(mapping);

        CreateIndexResponse response = builder.execute().actionGet();
        if (response.isAcknowledged()) {
            System.out.println("创建索引文档成功！");
        } else {
            System.out.println("创建索引文档失败！");
        }
    }

    /**
     * 增加文档
     *
     * @throws Exception
     */
    public static void addIndexFile() throws Exception {
        IndexResponse response = getClient().prepareIndex("index_name_second", "type_name_second", "1")
                .setSource(XContentFactory.jsonBuilder().startObject()
                        .field("type", "liuyazhuang")
                        .field("eventCount", 1)
                        .field("eventDate", new Date())
                        .field("message", "my name is liuyazhuang").endObject()).get();
        System.out.println("index: " + response.getIndex() + " insert doc id: " + response.getId());
    }


    /**
     * 修改文档方式1
     *
     * @throws Exception
     */
    public static void updateIndexFile01() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("index_name_second");
        updateRequest.type("type_name_second");
        updateRequest.id("1");
        updateRequest.doc(XContentFactory.jsonBuilder().startObject().field("type", "lyz").endObject());
        System.out.println(getClient().update(updateRequest).get());
    }


    /**
     * 修改文档方式2
     *
     * @throws Exception
     */
    public static void updateIndexFile02() throws Exception {
        IndexRequest indexRequest = new IndexRequest("index_name_second", "type_name_second", "3")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("type", "liuyazhuang")
                        .field("eventCount", 1)
                        .field("eventDate", new Date())
                        .field("message", "my name is liuyazhuang")
                        .endObject());
        UpdateRequest request = new UpdateRequest("index_name_second", "type_name_second", "3")
                .doc(XContentFactory.jsonBuilder().startObject().field("type", "lyz").endObject()).upsert(indexRequest);
        System.out.println(getClient().update(request).get());
    }

    /**
     * 查询文档
     *
     * @throws Exception
     */
    public static void queryIndexFile() throws Exception {
        GetResponse response = getClient().prepareGet("index_name_second", "type_name_second", "1").get();
        String source = response.getSource().toString();
        long version = response.getVersion();
        String indexName = response.getIndex();
        String type = response.getType();
        String id = response.getId();

        System.out.println("source===>>> " + source + ",  version====>>> " + version + ", indexName=====>>> " + indexName + ", type====>>> " + type + ", id====>>> " + id);
    }

    @Test
    public void queryIndexFile1() throws Exception {
        GetResponse response = getClient().prepareGet("people", "man", "1").get();
        String source = response.getSource().toString();
        long version = response.getVersion();
        String indexName = response.getIndex();
        String type = response.getType();
        String id = response.getId();

        System.out.println("source===>>> " + source + ",  version====>>> " + version + ", indexName=====>>> " + indexName + ", type====>>> " + type + ", id====>>> " + id);
    }

    /**
     * 删除文档
     *
     * @throws Exception
     */
    public static void deleteIndexFile() throws Exception {
        DeleteResponse response = getClient().prepareDelete("index_name_second", "type_name_second", "1").get();
//        System.out.println(response.isFound());  //文档存在返回true, 不存在返回false
    }


    /**
     * 删除索引
     *
     * @throws Exception
     */
    public static void deleteIndex() throws Exception {
        DeleteIndexRequest delete = new DeleteIndexRequest("index_name_second");
        System.out.println(getClient().admin().indices().delete(delete));
    }

}
