package com.wangboak.noteassistant.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangboak.noteassistant.integration.openai.OpenAIClient;
import com.wangboak.noteassistant.integration.openai.util.ChatMessageUtil;
import com.wangboak.noteassistant.service.MilvusService;
import com.wangboak.noteassistant.service.OpenAIService;
import com.wangboak.noteassistant.service.TopicService;
import com.wangboak.noteassistant.web.dto.AnswerDTO;
import com.wangboak.noteassistant.web.dto.TopicDTO;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: wangboak
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
    OpenAIService openAIService;

    @Resource
    MilvusService milvusService;

    @Resource
    TopicService topicService;

    @PostMapping("/put")
    public String putData(@RequestBody TopicDTO dto) {
        List<Float> embedding = openAIService.embedding(dto.getContent());
        Long id = topicService.saveTopic(dto);
        milvusService.saveData(id, embedding);
        log.info("id:{}", id);
        return "success";
    }

    @GetMapping("/search")
    public List<String> search(String text) {
        List<Float> embedding = openAIService.embedding(text);
        List<Long> ids = milvusService.search(embedding, 3);
        List<String> result = topicService.findContentByIds(ids);
        return result;
    }

    @GetMapping("/answer")
    public AnswerDTO answer(String question) {
        List<Float> embedding = openAIService.embedding(question);
        List<Long> ids = milvusService.search(embedding, 3);
        List<String> result = topicService.findContentByIds(ids);

        ChatMessageUtil chatMessageUtil = ChatMessageUtil.builder()
                .addSystem("You are a smart assistant who can accurately answer relevant questions based on the information I give you.")
                .addUser("Here is the information I give you.")
                .addAssistant("Yes, I am a smart assistant, and I will give priority to answering questions accurately based on the information you provide.");

        for (String s : result) {
            chatMessageUtil.addUser(s);
        }

        chatMessageUtil.addUser("my question is: " + question);
        chatMessageUtil.addUser("Please answer in the same language as my question.");

        String answer = openAIService.chatCompletion(chatMessageUtil.build());
        AnswerDTO dto = new AnswerDTO();
        dto.setAnswer(answer);
        return dto;
    }


}
