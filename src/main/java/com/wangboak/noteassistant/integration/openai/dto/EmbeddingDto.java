package com.wangboak.noteassistant.integration.openai.dto;

import java.util.List;

import lombok.Data;

/**
 *
 * @author: wangbo
 * @date: 2023-04-15 18:48
 * @
 **/
@Data
public class EmbeddingDto {

    List<Double> embedding;

    Integer index;

}
