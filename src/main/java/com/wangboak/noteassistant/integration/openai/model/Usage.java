package com.wangboak.noteassistant.integration.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *
 * @author: wangbo
 * @date: 2023-04-16 02:28
 * @
 **/
@Data
public class Usage {

    @JsonProperty(value = "prompt_tokens")
    private Integer promptTokens;

    @JsonProperty(value = "total_tokens")
    private Integer totalTokens;
}
