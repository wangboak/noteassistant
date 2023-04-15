package com.wangboak.noteassistant.integration.openai.model;

import lombok.Data;

/**
 * @
 **/
@Data
public class EmbeddingReq {

    private String input;

    private String model = "text-embedding-ada-002";

}
