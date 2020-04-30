package com.octo.bankoperations.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octo.bankoperations.dto.BankTransferDTO;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AMQPSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    @Qualifier("virements")
    private Queue queueExterneVir;

    //@Scheduled(fixedDelay = 1000, initialDelay = 500)

     /*new MessagePostProcessor() {
        @Override
        public Message postProcessMessage(Message message) throws AmqpException {
            message.getMessageProperties().setContentType("application/json");
            message.getMessageProperties().getHeaders()
                    .put(AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME, "com.octo.bankoperations.DTO.BankTransferDTO");
            return message;
        }
    }*/

    public void send(BankTransferDTO transfer) {
        this.amqpTemplate.convertAndSend(queueExterneVir.getName(), transfer);
        System.out.println(" [x] Sent '" + transfer + "'");
    }
}