package com.octo.bankoperations.amqp;

import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.service.VirementService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

@RabbitListener(queues = "virements_received")
public class AMQPReceiverVirements {

    @Autowired
    private VirementService virementService;

    @RabbitHandler
    public void receive(BankTransferDTO dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        System.out.println(" [x] Received '" +  dto.getReference() + "'" + " with tag " + tag);
        virementService.virementReceivedFromBlockchain(dto);
    }
}