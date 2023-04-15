package com.wangboak.noteassistant.integration.openai.model;

import java.util.List;

import lombok.Data;

/**
 * @
 **/
@Data
public class CreateChatCompletionReq {

    private String model;

    private List<ChatMessage> messages;

    private Double temperature;

}
