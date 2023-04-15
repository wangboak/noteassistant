package com.wangboak.noteassistant.integration.openai.util;

import java.util.ArrayList;
import java.util.List;

import com.wangboak.noteassistant.integration.openai.model.ChatMessage;

/**
 * @author: wangboak
 * @
 **/
public class ChatMessageUtil {

    private List<ChatMessage> list;

    public static ChatMessageUtil builder() {
        ChatMessageUtil util = new ChatMessageUtil();
        util.list = new ArrayList<>();
        return util;
    }

    public List<ChatMessage> build() {
        return list;
    }

    public ChatMessageUtil addSystem(String content) {
        ChatMessage system = new ChatMessage();
        system.setRole("system");
        system.setContent(content);
        this.list.add(system);
        return this;
    }

    public ChatMessageUtil addUser(String content) {
        ChatMessage user = new ChatMessage();
        user.setRole("user");
        user.setContent(content);
        this.list.add(user);
        return this;
    }

    public ChatMessageUtil addAssistant(String content) {
        ChatMessage assistant = new ChatMessage();
        assistant.setRole("assistant");
        assistant.setContent(content);
        this.list.add(assistant);
        return this;
    }


}
