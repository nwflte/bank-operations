package com.octo.bankoperations.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {

    @Bean(name = "virements")
    public Queue virementsExterne() {
        return new Queue("virements");
    }

    @Bean(name = "virementsStatus")
    public Queue virementStatus() {
        return new Queue("virements_status");
    }

    @Bean
    public AMQPReceiverStatus receiver() {
        return new AMQPReceiverStatus();
    }

    @Bean
    public AMQPSender sender() {
        return new AMQPSender();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}