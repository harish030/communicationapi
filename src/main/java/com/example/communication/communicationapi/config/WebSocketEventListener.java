package com.example.communication.communicationapi.config;

import com.example.communication.communicationapi.chat.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    @Autowired
    public SimpMessagingTemplate messagingTemplate;

    public static final Map<String, String> userSessionMap = new ConcurrentHashMap<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) throws InterruptedException {
        System.out.println(event.getMessage());
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println(sha.getFirstNativeHeader("username"));
        System.out.println("🔗 Connected session ID: " + sha.getSessionId());
        userSessionMap.put(sha.getSessionId(),sha.getFirstNativeHeader("username"));
//        UserDetails userDetail = new UserDetails(sha.getFirstNativeHeader("username"),sha.getSessionId());

        messagingTemplate.convertAndSend("/chat/chatroom",new UserDetails(sha.getFirstNativeHeader("username"),sha.getSessionId(),MessageType.JOIN));


//        messagingTemplate.convertAndSendToUser(
//                Objects.requireNonNull(sha.getSessionId()), // user session ID as unique destination
//                "/queue/connected-users", // private destination
//                userSessionMap.keySet()   // send list of usernames
//        );
        System.out.println("success");
    }
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        messagingTemplate.convertAndSend("/chat/chatroom",new UserDetails(userSessionMap.get(sessionId),sessionId,MessageType.LEAVE));
        userSessionMap.remove(sessionId);
        // Perform actions on disconnect, e.g., log, notify other clients, clean up resources
        System.out.println("Client disconnected: " + sessionId);
    }

    public static String getUsernameBySessionId(String sessionId) {
        return userSessionMap.get(sessionId);
    }

    public static String getSessionIdByUsername(String username) {
        return userSessionMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(username))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

}