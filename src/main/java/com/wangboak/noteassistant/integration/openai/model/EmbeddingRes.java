package com.wangboak.noteassistant.integration.openai.model;

import java.util.List;

import lombok.Data;

/**
 *
 **/
@Data
public class EmbeddingRes {

    List<EmbeddingDto> data;

    Usage usage;

}
