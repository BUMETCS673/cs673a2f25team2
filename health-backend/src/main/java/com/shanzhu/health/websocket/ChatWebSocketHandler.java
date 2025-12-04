package com.shanzhu.health.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shanzhu.health.entity.BodyNotes;
import com.shanzhu.health.entity.User;
import com.shanzhu.health.mapper.UserMapper;
import com.shanzhu.health.service.IBodyNotesService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final wsOpenAiChatModel openAiChatModel;
    private final IBodyNotesService bodyNotesService;
    private final UserMapper userMapper;
    private final Map<String, StringBuilder> sessionContext = new ConcurrentHashMap<>();

    // Constructor injection of HistoryService and wsOpenAiChatModel
    public ChatWebSocketHandler(wsOpenAiChatModel openAiChatModel, IBodyNotesService bodyNotesService, UserMapper userMapper) {
        this.openAiChatModel = openAiChatModel;
        this.bodyNotesService = bodyNotesService;
        this.userMapper = userMapper;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        String payload = message.getPayload(); // Receive message from client
        ChatRequest request = parseMessage(payload); // Define a method to parse JSON
        String question = request.getText();
        String sessionKey = getSessionKey(session, request);

        if (!hasActiveAccess(request.getUsername())) {
            streamSingleMessage(session, "Access denied. Please purchase a plan to use the AI Chatbox.");
            return;
        }

        if ("upload_context".equalsIgnoreCase(request.getType())) {
            handleUploadContext(session, sessionKey, request);
            return;
        }

        String username = request.getUsername();
        if (question.equals("AI Health Advice")) {
            if (username == null || username.isEmpty()) {
                streamSingleMessage(session, "请先输入个人基本健康信息后再点击 Get Health Advice。");
                return;
            }

            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, username);
            User user = userMapper.selectOne(wrapper);

            if (user == null) {
                streamSingleMessage(session, "请先输入个人基本健康信息后再点击 Get Health Advice。");
                return;
            }

            List<BodyNotes> bodyNotes = bodyNotesService.getBodyNotes(user.getId());
            if (bodyNotes == null || bodyNotes.isEmpty()) {
                streamSingleMessage(session, "请先输入个人基本健康信息后再点击 Get Health Advice。");
                return;
            }
            question = constructPrompt(bodyNotes);
        }

        String contextualQuestion = appendContext(question, sessionKey);


        // Initialize StringBuilder for accumulating responses
        StringBuilder responseBuilder = new StringBuilder();

        // Use Flux<String> to stream data back to client
        Flux<String> aiResponse = openAiChatModel.stream(contextualQuestion)
                .doOnNext(chunk -> {
                    if (!"[DONE]".equalsIgnoreCase(chunk)) {
                        responseBuilder.append(chunk); // Accumulate AI response content
                    }
                })
                .map(chunk -> "data:" + chunk + "\n\n")
                .concatWith(Mono.just("data:[DONE]\n\n"));

        // Send streaming messages to client, while listening for completion events
        aiResponse.subscribe(response -> {
            try {
                session.sendMessage(new TextMessage(response));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, error -> {
            // Exception handling
            error.printStackTrace();
        }, () -> {

        });
    }


    private ChatRequest parseMessage(String payload) {
        // Assume payload is JSON format, can use library to parse
        // For example, use Jackson ObjectMapper to parse into ChatRequest object
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(payload, ChatRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid message format: " + payload, e);
        }
    }

    private String getSessionKey(WebSocketSession session, ChatRequest request) {
        if (request.getSessionId() != null && !request.getSessionId().isEmpty()) {
            return request.getSessionId();
        }
        return session.getId();
    }

    private void handleUploadContext(WebSocketSession session, String sessionKey, ChatRequest request) {
        StringBuilder builder = sessionContext.computeIfAbsent(sessionKey, key -> new StringBuilder());

        String fileLabel = request.getFileName() != null ? request.getFileName() : "Uploaded file";
        String fileType = request.getFileType() != null ? request.getFileType() : "text/plain";
        String content = request.getFileContent() != null ? request.getFileContent() : "";

        String normalizedContent = normalizeContent(content, fileType);
        builder.append("File: ").append(fileLabel).append(" (type: ").append(fileType).append(")\n")
                .append(normalizedContent)
                .append("\n\n");

        streamSingleMessage(session, "Context uploaded and will be used to enhance subsequent answers.");
    }

    private String normalizeContent(String content, String fileType) {
        if (fileType.startsWith("image/")) {
            String prefix = content.length() > 150 ? content.substring(0, 150) + "..." : content;
            return "Image (base64 preview): " + prefix;
        }
        if (content.length() > 4000) {
            return content.substring(0, 4000) + "...";
        }
        return content;
    }

    private String appendContext(String question, String sessionKey) {
        StringBuilder context = sessionContext.get(sessionKey);
        if (context == null || context.length() == 0) {
            return question;
        }

        return "Use the following user-provided documents as additional context when answering." +
                "\nContext:\n" + context +
                "\nUser question: " + question;
    }

    private void streamSingleMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage("data:" + message + "\n\n"));
            session.sendMessage(new TextMessage("data:[DONE]\n\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasActiveAccess(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return false;
        }

        LocalDateTime expiry = user.getAccessExpiry();
        if (expiry == null || expiry.isBefore(LocalDateTime.now())) {
            return false;
        }

        return "ACTIVE".equalsIgnoreCase(user.getPaymentStatus());
    }

    // Prompt construction method below
    // Method to construct prompt
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
            promptBuilder.append("Heart Rate:").append(note.getHeartRate()).append(" beats/minute\n");
            promptBuilder.append("Vision:").append(note.getVision()).append("\n");
            promptBuilder.append("Sleep Duration:").append(note.getSleepDuration()).append(" hours\n");
            promptBuilder.append("Sleep Quality:").append(note.getSleepQuality()).append("\n");
            promptBuilder.append("Smoking:").append(note.isSmoking() ? "Yes" : "No").append("\n");
            promptBuilder.append("Drinking:").append(note.isDrinking() ? "Yes" : "No").append("\n");
            promptBuilder.append("Exercise:").append(note.isExercise() ? "Yes" : "No").append("\n");
            promptBuilder.append("Food Preferences:").append(note.getFoodTypes()).append("\n");
            promptBuilder.append("Water Intake:").append(note.getWaterConsumption()).append("ml/day\n");
            promptBuilder.append("Record Time:").append(note.getDate()).append("\n\n");
        }

        promptBuilder.append("Please analyze the user's health status based on the above data and provide specific health advice.");

        return promptBuilder.toString();
    }
}
