package com.wangboak.noteassistant.integration.openai.dto;

import java.util.List;

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
    private static final class Usage {
        private Integer promptTokens;
        private Integer totalTokens;
    }


}
