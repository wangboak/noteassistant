package com.wangboak.noteassistant.integration.openai;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wangboak.noteassistant.integration.openai.model.ChatCompletionResult;
import com.wangboak.noteassistant.integration.openai.model.CreateChatCompletionReq;
import com.wangboak.noteassistant.integration.openai.model.EmbeddingReq;
import com.wangboak.noteassistant.integration.openai.model.EmbeddingRes;

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

    /**
     * Given a prompt, the model will return one or more predicted completions, and can also return the probabilities of alternative tokens at each position.
     * 给定一个提示，该模型将返回一个或多个预测的完成，并且还可以返回每个位置的替代标记的概率。
     * @return
     */
    @PostMapping(value = "/v1/chat/completions", headers = {"Content-Type=application/json;charset=UTF-8", "Authorization=Bearer ${OpenAI.apikey}"})
    ChatCompletionResult createChatCompletion(@RequestBody CreateChatCompletionReq req);
}
