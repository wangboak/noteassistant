package com.wangboak.noteassistant.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangboak.noteassistant.integration.openai.OpenAIClient;
import com.wangboak.noteassistant.integration.openai.model.ChatCompletionResult;
import com.wangboak.noteassistant.integration.openai.model.ChatMessage;
import com.wangboak.noteassistant.integration.openai.model.CreateChatCompletionReq;
import com.wangboak.noteassistant.integration.openai.model.EmbeddingReq;
import com.wangboak.noteassistant.integration.openai.model.EmbeddingRes;

/**
 *
 * @author: wangbo
 * @date: 2023-04-15 20:56
 * @
 **/
@Service
public class OpenAIService {

    @Resource
    OpenAIClient openAIClient;

    public List<Float> embedding(String text) {
        EmbeddingReq req = new EmbeddingReq();
        req.setInput(text);
        EmbeddingRes embedding = openAIClient.createEmbedding(req);

        return embedding.getData().get(0).getEmbedding();
    }

    public String chatCompletion(List<ChatMessage> messages) {
        CreateChatCompletionReq req = new CreateChatCompletionReq();
        req.setModel("gpt-3.5-turbo");
        req.setMessages(messages);
        req.setTemperature(0.7);

        ChatCompletionResult chatCompletion = openAIClient.createChatCompletion(req);

        return chatCompletion.getChoices().get(0).getMessage().getContent();

    }


}
