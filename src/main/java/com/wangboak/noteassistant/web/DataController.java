package com.wangboak.noteassistant.web;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangboak.noteassistant.integration.openai.OpenAIClient;
import com.wangboak.noteassistant.integration.openai.OpenAIClientV2;
import com.wangboak.noteassistant.integration.openai.dto.EmbeddingReq;
import com.wangboak.noteassistant.integration.openai.dto.EmbeddingRes;
import com.wangboak.noteassistant.util.JsonUtil;
import com.wangboak.noteassistant.web.dto.TopicDTO;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: wangbo
 * @date: 2023-04-15 18:18
 * @
 **/
@Slf4j
@RestController
@RequestMapping("/data")
public class DataController {


    @Resource
    OpenAIClient openAIClient;

    @Resource
    OpenAIClientV2 openAIClientv2;

    @PostMapping("/set")
    public String putData(@RequestBody TopicDTO dto) throws IOException {

        EmbeddingReq req = new EmbeddingReq();
        req.setInput(dto.getContent());
        EmbeddingRes embeddings = openAIClientv2.createEmbedding(req);

        log.info("response: {}", embeddings);

        return JsonUtil.toJson(embeddings);
    }


}
