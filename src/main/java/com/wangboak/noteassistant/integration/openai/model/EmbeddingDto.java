package com.wangboak.noteassistant.integration.openai.model;

import java.util.List;

import lombok.Data;

/**
 *
 **/
@Data
public class EmbeddingDto {

    List<Float> embedding;

    Integer index;

}
