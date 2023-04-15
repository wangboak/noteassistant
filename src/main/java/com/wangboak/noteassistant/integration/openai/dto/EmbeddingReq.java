package com.wangboak.noteassistant.integration.openai.dto;

import lombok.Data;

/**
 *
 * @author: wangbo
 * @date: 2023-04-15 18:39
 * @
 **/
@Data
public class EmbeddingReq {

    private String input;

    private String model = "text-embedding-ada-002";

}
