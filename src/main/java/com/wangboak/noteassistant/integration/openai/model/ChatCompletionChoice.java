package com.wangboak.noteassistant.integration.openai.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *
 * @author: wangbo
 * @date: 2023-04-16 02:29
 * @
 **/
@Data
public class ChatCompletionChoice {

    Integer index;

    @JsonAlias({"delta"})
    ChatMessage message;

    @JsonProperty("finish_reason")
    String finishReason;
}
