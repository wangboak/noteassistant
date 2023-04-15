package com.wangboak.noteassistant.integration.openai;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wangboak.noteassistant.integration.openai.dto.EmbeddingReq;
import com.wangboak.noteassistant.integration.openai.dto.EmbeddingRes;

/**
 *
 * @author: wangbo
 * @date: 2023-04-15 18:36
 * @
 **/
@FeignClient(name = "OPENAI-CLIENT", url = "https://api.openai.com")
public interface OpenAIClient {

    @PostMapping(value = "/v1/embeddings", headers = {"Content-Type=application/json;charset=UTF-8", "Authorization=Bearer ${OpenAI.apikey}"})
    EmbeddingRes createEmbedding(@RequestBody EmbeddingReq req);
}
