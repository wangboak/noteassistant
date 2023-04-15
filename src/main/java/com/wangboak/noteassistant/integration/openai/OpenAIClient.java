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
@FeignClient(name = "OpenAI-service", url = "https://api.openai.com")
public interface OpenAIClient {

    @PostMapping(value = "/v1/embeddings", headers = {"Content-Type: application/json", "Authorization: Bearer "})
    String createEmbeddings(@RequestBody EmbeddingReq req);


}
