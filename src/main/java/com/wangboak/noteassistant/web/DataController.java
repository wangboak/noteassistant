package com.wangboak.noteassistant.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangboak.noteassistant.integration.openai.OpenAIClient;
import com.wangboak.noteassistant.integration.openai.dto.EmbeddingReq;
import com.wangboak.noteassistant.integration.openai.dto.EmbeddingRes;
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

    @PostMapping("/set")
    public EmbeddingRes putDataV2(@RequestBody TopicDTO dto) {
        EmbeddingReq req = new EmbeddingReq();
        req.setInput(dto.getContent());
        EmbeddingRes embedding = openAIClient.createEmbedding(req);
        log.info("response: {}", embedding);
        return embedding;
    }


}
