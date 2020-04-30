package com.octo.bankoperations.amqp;

import com.octo.bankoperations.service.VirementService;
import com.rabbitmq.client.Channel;
import jdk.nashorn.internal.ir.ObjectNode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;
import java.util.Map;

@RabbitListener(queues = "virements_status")
public class AMQPReceiverStatus {

    @Autowired
    private VirementService virementService;

    @RabbitHandler
    public void receive(Map<String, String> in, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        System.out.println(" [x] Received '" +  in.get("reference") + "'" + " with tag " + tag);
        virementService.virementInterneSavedToBlockchain(in.get("reference"));
    }

    class ReferenceMessage {
        private String reference;

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        @Override
        public String toString() {
            return "ReferenceMessage{" +
                    "reference='" + reference + '\'' +
                    '}';
        }
    }
}