package com.example.communication.communicationapi.chat;


import io.agora.media.RtcTokenBuilder2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;
import java.util.Objects;

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
        if (Objects.equals(chatMessage.getCommunicationType(), "CHAT")){
            messagingTemplate.convertAndSend("/user/" + chatMessage.getSender() + "_" + chatMessage.getReceiver() +"/queue/messages",
                    new ChatMessage(chatMessage.getMessage(),chatMessage.getReceiver(),chatMessage.getSender(),chatMessage.getCommunicationType(),chatMessage.getCommunicationRequestType(),"","","")
            );
        }
        else if (Objects.equals(chatMessage.getCommunicationType(), "AUDIO")) {
            if (Objects.equals(chatMessage.getCommunicationRequestType(), "REQUEST")){
                String token = requestAudioVideoToken(chatMessage.getSender(),chatMessage.getReceiver());
                messagingTemplate.convertAndSend("/user/" + chatMessage.getSender() + "_" + chatMessage.getReceiver() +"/queue/messages",
                        new ChatMessage(chatMessage.getMessage(),chatMessage.getReceiver(),chatMessage.getSender(),chatMessage.getCommunicationType(),chatMessage.getCommunicationRequestType(),token,chatMessage.getSenderName(),chatMessage.getReceiverName())
                );
            }
            else if (Objects.equals(chatMessage.getCommunicationRequestType(), "JOIN")) {
                String token = joinAudioVideoToken(chatMessage.getSender(),chatMessage.getReceiver());
                messagingTemplate.convertAndSend("/user/" + chatMessage.getReceiver() + "_" + chatMessage.getSender() +"/queue/messages",
                        new ChatMessage(chatMessage.getMessage(),chatMessage.getReceiver(),chatMessage.getSender(),chatMessage.getCommunicationType(),chatMessage.getCommunicationRequestType(),token,chatMessage.getSenderName(),chatMessage.getReceiverName())
                );
            }
            else if (Objects.equals(chatMessage.getCommunicationRequestType(), "LEAVE")) {
                messagingTemplate.convertAndSend("/user/" + chatMessage.getReceiver() + "_" + chatMessage.getSender() +"/queue/messages",
                        new ChatMessage(chatMessage.getMessage(),chatMessage.getReceiver(),chatMessage.getSender(),chatMessage.getCommunicationType(),chatMessage.getCommunicationRequestType(),"",chatMessage.getSenderName(),chatMessage.getReceiverName())
                );
            }

        }

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

    private String requestAudioVideoToken(String sender , String receiver){
        RtcTokenBuilder2 token = new RtcTokenBuilder2();
        String result =
                token.buildTokenWithUserAccount("48b5127bfa5e4dd582f08a1adcfd77f6", "c5e9589a20ca4fdd84579509518ca491", sender + "_" + receiver, receiver, RtcTokenBuilder2.Role.ROLE_PUBLISHER, 3600, 3600);
        System.out.printf("Token with uid: %s\n", result);
        return result;
    }

    private String joinAudioVideoToken(String sender , String receiver){
        RtcTokenBuilder2 token = new RtcTokenBuilder2();
        String result =
                token.buildTokenWithUserAccount("48b5127bfa5e4dd582f08a1adcfd77f6", "c5e9589a20ca4fdd84579509518ca491", sender + "_" + receiver, sender, RtcTokenBuilder2.Role.ROLE_PUBLISHER, 3600, 3600);
        System.out.printf("Token with uid: %s\n", result);
        return result;
    }

}