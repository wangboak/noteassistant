package com.wangboak.noteassistant.integration.openai.model;

import java.util.List;

import lombok.Data;

/**
 *
 * @author: wangbo
 * @date: 2023-04-16 02:28
 * @
 **/
@Data
public class ChatCompletionResult {

    String id;

    String object;

    long created;

    String model;

    List<ChatCompletionChoice> choices;

    Usage usage;
}
