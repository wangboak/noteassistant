package com.wangboak.noteassistant.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.wangboak.noteassistant.util.JsonUtil;

import io.milvus.client.MilvusServiceClient;
import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.grpc.IDs;
import io.milvus.grpc.LongArray;
import io.milvus.grpc.MutationResult;
import io.milvus.grpc.SearchResultData;
import io.milvus.grpc.SearchResults;
import io.milvus.param.ConnectParam;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: wangbo
 * @date: 2023-04-15 20:00
 **/
@Slf4j
@Configuration
@Service
public class MilvusService {

    @Value("${milvus.host}")
    private String milvusHost;

    @Value("${milvus.port}")
    private int milvusPort;

    @Value("${milvus.collectionName}")
    private String collectionName;

    private MilvusServiceClient milvusClient;

    @PostConstruct
    void init() {
        milvusClient = new MilvusServiceClient(ConnectParam.newBuilder().withHost(milvusHost).withPort(milvusPort).build());
    }

    /**
     * 保存一条数据。
     * @param id
     * @param data
     */
    public void saveData(Long id, List<Float> data) {

        List<Long> idList = new ArrayList<>();
        idList.add(id);

        List<List<Float>> dataList = new ArrayList<>();
        dataList.add(data);


        List<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field("id", idList));
        fields.add(new InsertParam.Field("embed", dataList));

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName("note")
                .withPartitionName("_default")
                .withFields(fields).build();

        R<MutationResult> insert = milvusClient.insert(insertParam);

        log.info("Result insert: {}", JsonUtil.toJson(insert));
    }

    /**
     * 根据 向量搜素 近邻TopK
     * @param data
     * @return
     */
    public List<Long> search(List<Float> data, int topk) {

        String SEARCH_PARAM = "{\"nprobe\":10, \"offset\":5}";

        List<String> search_output_fields = Arrays.asList("id");
        List<List<Float>> search_vectors = Arrays.asList(data);

        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(collectionName)
                .withConsistencyLevel(ConsistencyLevelEnum.STRONG)
                .withMetricType(MetricType.L2)
                .withOutFields(search_output_fields)
                .withTopK(topk)
                .withVectors(search_vectors)
                .withVectorFieldName("embed")
                //.withParams(SEARCH_PARAM)
                .build();
        R<SearchResults> respSearch = milvusClient.search(searchParam);
        SearchResults result = respSearch.getData();
        SearchResultData results = result.getResults();
        log.info("Result search: {}", JsonUtil.toJson(respSearch));

        IDs ids = results.getIds();
        LongArray intId = ids.getIntId();
        List<Long> dataList = intId.getDataList();
        return dataList;

    }

}
