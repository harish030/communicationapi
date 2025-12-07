package com.example.communication.communicationapi.chat;



public class ChatMessage {

    private String message;
    private String receiver;
    private String sender;
    private String communicationType;
    private String communicationRequestType;
    private String token;
    private String senderName;
    private String receiverName;

    public ChatMessage(String message, String receiver, String sender, String communicationType, String communicationRequestType, String token, String senderName, String receiverName) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        this.communicationType = communicationType;
        this.communicationRequestType = communicationRequestType;
        this.token = token;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(String communicationType) {
        this.communicationType = communicationType;
    }

    public String getCommunicationRequestType() {
        return communicationRequestType;
    }

    public void setCommunicationRequestType(String communicationRequestType) {
        this.communicationRequestType = communicationRequestType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}