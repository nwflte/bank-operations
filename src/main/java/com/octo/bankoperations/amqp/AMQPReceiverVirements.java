package com.octo.bankoperations.amqp;

import com.octo.bankoperations.dto.BankTransferDTO;
import com.octo.bankoperations.enums.VirementStatus;
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
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RabbitListener(queues = "virements_received")
@Component
public class AMQPReceiverVirements {

    private static final Logger logger = LoggerFactory.getLogger(AMQPReceiverVirements.class);

    @Autowired
    private VirementService virementService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /*@RabbitHandler
    public void receive(BankTransferDTO dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        logger.info(" [x] Received '{}' with tag {}", dto.getReference(), tag);
        amqpTemplate.convertAndSend("/topic/group", dto);
        virementService.saveVirementReceivedFromBlockchain(dto);
    }*/

    @RabbitHandler
    public void receive(Map<String, Object> in, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        logger.info(" [x] Received '{}' with tag {}", in.get("reference"), tag);
        //amqpTemplate.convertAndSend("/topic/group", dto);
        BankTransferDTO dto = new BankTransferDTO();
        dto.setReference((String) in.get("reference"));
        dto.setStatus(VirementStatus.valueOf((String)in.get("status")));
        //dto.setStatusUpdate(Date .parse(in.get("statusUpdate")));
        dto.setSenderRIB((String)in.get("senderRIB"));
        dto.setReceiverRIB((String)in.get("receiverRIB"));
        dto.setExecutionDate(new Date());
        dto.setAmount(BigDecimal.valueOf((int)in.get("amount")));
        virementService.saveVirementReceivedFromBlockchain(dto);
    }
}