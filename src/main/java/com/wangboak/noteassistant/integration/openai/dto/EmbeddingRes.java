package com.wangboak.noteassistant.integration.openai.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *
 * @author: wangbo
 * @date: 2023-04-15 18:44
 * @
 **/
@Data
public class EmbeddingRes {

    List<EmbeddingDto> data;

    Usage usage;

    @Data
    public static final class Usage {

        @JsonProperty(value = "prompt_tokens")
        private Integer promptTokens;

        @JsonProperty(value = "total_tokens")
        private Integer totalTokens;
    }


}
