package com.octo.bankoperations.amqp;

import com.octo.bankoperations.dto.BankTransferDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AMQPSender {

    private static final Logger logger = LoggerFactory.getLogger(AMQPSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    @Qualifier("virements")
    private Queue queueVirement;

    public void send(BankTransferDTO transfer) {
        this.amqpTemplate.convertAndSend(queueVirement.getName(), transfer);
        logger.info(" [x] Sent '{}'" ,transfer);
    }
}