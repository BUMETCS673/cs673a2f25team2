package com.shanzhu.health.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanzhu.health.entity.BodyNotes;
import com.shanzhu.health.service.IBodyNotesService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;


@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final wsOpenAiChatModel openAiChatModel;
    private final IBodyNotesService bodyNotesService;

    // 构造方法注入 HistoryService 和 wsOpenAiChatModel
    public ChatWebSocketHandler(wsOpenAiChatModel openAiChatModel, IBodyNotesService bodyNotesService) {
        this.openAiChatModel = openAiChatModel;
        this.bodyNotesService = bodyNotesService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        String payload = message.getPayload(); // 从客户端接收消息
        ChatRequest request = parseMessage(payload); // 定义一个方法解析 JSON
        String question = request.getText();

        String username = request.getUsername();
        if (question.equals("AI Health Advice")) {
            List<BodyNotes> bodyNotes = bodyNotesService.getBodyNotesByUsername(username);
            question = constructPrompt(bodyNotes);
        }


        // 初始化累积响应的 StringBuilder
        StringBuilder responseBuilder = new StringBuilder();

        // 使用 Flux<String> 流式返回数据给客户端
        Flux<String> aiResponse = openAiChatModel.stream(question)
                .doOnNext(chunk -> {
                    if (!"[DONE]".equalsIgnoreCase(chunk)) {
                        responseBuilder.append(chunk); // 累加 AI 响应内容
                    }
                })
                .map(chunk -> "data:" + chunk + "\n\n")
                .concatWith(Mono.just("data:[DONE]\n\n"));

        // 向客户端发送流式消息，同时监听完成事件
        aiResponse.subscribe(response -> {
            try {
                session.sendMessage(new TextMessage(response));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, error -> {
            // 异常处理
            error.printStackTrace();
        }, () -> {

        });
    }


    private ChatRequest parseMessage(String payload) {
        // 假设 payload Yes JSON 格式，可以使用库解析
        // 例如使用 Jackson ObjectMapper 解析为 ChatRequest 对象
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(payload, ChatRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid message format: " + payload, e);
        }
    }

    // 以下YesPrompt构建方法
    // 构造prompt的方法
    private String constructPrompt(List<BodyNotes> bodyNotes) {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Based on the following user health data, provide health advice and output as a txt document:\n");

        for (BodyNotes note : bodyNotes) {
            promptBuilder.append("User Information:\n");
            promptBuilder.append("Name:").append(note.getName()).append("\n");
            promptBuilder.append("Age:").append(note.getAge()).append("\n");
            promptBuilder.append("Gender:").append(note.getGender()).append("\n");
            promptBuilder.append("Height:").append(note.getHeight()).append("cm\n");
            promptBuilder.append("Weight:").append(note.getWeight()).append("kg\n");
            promptBuilder.append("Blood Sugar:").append(note.getBloodSugar()).append("mmol/L\n");
            promptBuilder.append("Blood Pressure:").append(note.getBloodPressure()).append("mmHg\n");
            promptBuilder.append("Blood Lipid:").append(note.getBloodLipid()).append("mmol/L\n");
            promptBuilder.append("Heart Rate:").append(note.getHeartRate()).append("次/分钟\n");
            promptBuilder.append("Vision:").append(note.getVision()).append("\n");
            promptBuilder.append("Sleep Duration:").append(note.getSleepDuration()).append("小时\n");
            promptBuilder.append("Sleep Quality:").append(note.getSleepQuality()).append("\n");
            promptBuilder.append("Smoking:").append(note.isSmoking() ? "Yes" : "No").append("\n");
            promptBuilder.append("Drinking:").append(note.isDrinking() ? "Yes" : "No").append("\n");
            promptBuilder.append("Exercise:").append(note.isExercise() ? "Yes" : "No").append("\n");
            promptBuilder.append("Food Preferences:").append(note.getFoodTypes()).append("\n");
            promptBuilder.append("Water Intake:").append(note.getWaterConsumption()).append("ml/天\n");
            promptBuilder.append("Record Time:").append(note.getDate()).append("\n\n");
        }

        promptBuilder.append("Please analyze the user's health status based on the above data and provide specific health advice.");

        return promptBuilder.toString();
    }
}
