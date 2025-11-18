package com.example.communication.communicationapi.chat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;

import static com.example.communication.communicationapi.config.WebSocketEventListener.userSessionMap;

@Controller
public class GreetingController {
    @Autowired
    public SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chatroom-list")
    @SendTo("/chat/chatrooms")
    public Map<String, String> chatRoomEntry() throws Exception {
        Thread.sleep(2000);
        return userSessionMap;
    }


    @MessageMapping("/private-message")
    public void sendPrivate(ChatMessage chatMessage) {
        System.out.println(chatMessage.getReceiver());
        messagingTemplate.convertAndSend("/user/" + chatMessage.getSender() + "_" + chatMessage.getReceiver() +"/queue/messages",
                new ChatMessage(chatMessage.getMessage(),chatMessage.getReceiver(),chatMessage.getSender())
        );
    }


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("check"+message.getName());
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/helloh")
    @SendTo("/topic/greet")
    public Greeting greet(HelloMessage message) throws Exception {
        System.out.println("check"+message.getName());
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


}