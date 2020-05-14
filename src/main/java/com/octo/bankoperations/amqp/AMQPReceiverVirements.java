package com.octo.bankoperations.amqp;

import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.service.VirementService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@RabbitListener(queues = "virements_received")
@Component
public class AMQPReceiverVirements {

    private static final Logger logger = LoggerFactory.getLogger(AMQPReceiverVirements.class);

    @Autowired
    private VirementService virementService;

    @RabbitHandler
    public void receive(@Valid BankTransferDTO dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        logger.info(" [x] Received '{}' with tag {}", dto.getReference(), tag);
        virementService.saveVirementReceivedFromBlockchain(dto);
    }
}