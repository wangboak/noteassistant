package com.wangboak.noteassistant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wangboak.noteassistant.util.JsonUtil;
import com.wangboak.noteassistant.util.RandomUtil;
import com.wangboak.noteassistant.util.RedisUtil;
import com.wangboak.noteassistant.web.dto.TopicDTO;

/**
 *
 * @author: wangbo
 * @date: 2023-04-16 01:29
 * @
 **/
@Service
public class TopicService {

    static final String TOPIC_KEY = "note:topic:%s";


    public Long saveTopic(TopicDTO dto) {
        long id = RandomUtil.genId();
        dto.setId(id);
        String key = String.format(TOPIC_KEY, id);
        RedisUtil.set(key, JsonUtil.toJson(dto));
        return id;
    }

    public TopicDTO findById(Long id) {
        String key = String.format(TOPIC_KEY, id);
        TopicDTO topicDTO = RedisUtil.get(key, TopicDTO.class);
        return topicDTO;
    }

    public List<TopicDTO> findByIds(List<Long> ids) {
        List<TopicDTO> result = new ArrayList<>();
        for (Long id : ids) {
            TopicDTO byId = findById(id);
            if (byId != null) {
                result.add(byId);
            }
        }
        return result;
    }

    public List<String> findContentByIds(List<Long> ids) {
        List<String> result = new ArrayList<>();
        List<TopicDTO> list = findByIds(ids);
        for (TopicDTO dto : list) {
            result.add(dto.getContent());
        }
        return result;
    }
}
