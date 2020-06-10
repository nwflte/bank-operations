package com.octo.bankoperations.web;

import com.octo.bankoperations.dto.BankTransferDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/sendVirement")
    @SendTo("/topic/group")
    public BankTransferDTO broadcastGroupMessage(@Payload BankTransferDTO message) {
        //Sending this message to all the subscribers
        return message;
    }

    @MessageMapping("/sendReference")
    @SendTo("/topic/group")
    public String broadcastGroupMessage(@Payload String message) {
        //Sending this message to all the subscribers
        return message;
    }
}
