package com.example.communication.communicationapi.chat;

public class UserDetails {
    private  String username;
    private String socketId;
    private MessageType userStatus;
    public UserDetails(String username,String socketId,MessageType userStatus){
        this.username = username;
        this.socketId = socketId;
        this.userStatus = userStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSocketId() {
        return socketId;
    }

    public MessageType getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(MessageType userStatus) {
        this.userStatus = userStatus;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }
}
