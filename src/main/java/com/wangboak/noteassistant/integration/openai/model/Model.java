package com.wangboak.noteassistant.integration.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * OpenAI 的模型。
 **/
@Data
public class Model {

    private String id;

    private String object;

    @JsonProperty("owner_by")
    private String ownedBy;
}
