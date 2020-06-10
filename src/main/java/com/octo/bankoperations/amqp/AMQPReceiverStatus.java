package com.octo.bankoperations.amqp;

import com.octo.bankoperations.service.VirementService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Map;

@RabbitListener(queues = "virements_status")
public class AMQPReceiverStatus {

    private static final Logger logger = LoggerFactory.getLogger(AMQPReceiverStatus.class);

    @Autowired
    private VirementService virementService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RabbitHandler
    public void receive(Map<String, String> in, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        logger.info(" [x] Received '{}' with tag {}", in.get("reference"), tag);
        amqpTemplate.convertAndSend("/topic/group", in.get("reference"));
        virementService.saveVirementAddedToBlockchain(in.get("reference"));
    }
}