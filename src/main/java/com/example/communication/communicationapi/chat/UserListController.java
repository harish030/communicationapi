package com.example.communication.communicationapi.chat;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.agora.media.RtcTokenBuilder2;
import io.agora.media.RtcTokenBuilder2.Role;

import java.util.List;
import java.util.Map;

import static com.example.communication.communicationapi.config.WebSocketEventListener.userSessionMap;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserListController {

    @GetMapping("getuserlist")
    public Map<String, String> userList(){
//        UserListController1();
        return userSessionMap;
    }

    private void UserListController1(){
        RtcTokenBuilder2 token = new RtcTokenBuilder2();
        String result =
                token.buildTokenWithUserAccount("48b5127bfa5e4dd582f08a1adcfd77f6", "c5e9589a20ca4fdd84579509518ca491", "har", "bubu", Role.ROLE_PUBLISHER, 3600, 3600);
        System.out.printf("Token with uid: %s\n", result);
    }
}
