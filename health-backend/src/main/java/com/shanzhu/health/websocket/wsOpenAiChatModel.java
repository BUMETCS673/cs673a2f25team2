package com.shanzhu.health.websocket;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class wsOpenAiChatModel {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    // Receive message msg and return streaming data
    public Flux<String> stream(String msg) {
        // Pass msg to openAiChatModel and return streaming data
        return openAiChatModel.stream(msg);
    }
}

