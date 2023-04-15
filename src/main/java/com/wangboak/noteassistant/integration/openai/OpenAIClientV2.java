package com.wangboak.noteassistant.integration.openai;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.wangboak.noteassistant.integration.openai.dto.EmbeddingReq;
import com.wangboak.noteassistant.integration.openai.dto.EmbeddingRes;
import com.wangboak.noteassistant.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author: wangbo
 * @date: 2023-04-15 19:09
 * @
 **/
@Slf4j
@Service
public class OpenAIClientV2 {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public EmbeddingRes createEmbedding(EmbeddingReq req) throws IOException {
        String url = "https://api.openai.com/v1/embeddings";
        RequestBody body = RequestBody.create(JsonUtil.toJson(req), JSON);

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer ")
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        Response res = client.newCall(request).execute();

        log.info("Response: {}", JsonUtil.toJson(res));

        return null;
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
