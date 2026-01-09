package com.trading212.cryptocurrencytrading.email.service;

import com.trading212.cryptocurrencytrading.email.model.EmailEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private final KafkaTemplate<String, EmailEntity> kafkaTemplate;
    private final String topicForSellingCrypto;
    private final String topicForBuyingCrypto;

    public EmailSenderService(KafkaTemplate<String, EmailEntity> kafkaTemplate,
                              @Value("${crypto.topics.sell}") String topicForSellingCrypto,
                              @Value("${crypto.topics.buy}") String topicForBuyingCrypto) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicForSellingCrypto = topicForSellingCrypto;
        this.topicForBuyingCrypto = topicForBuyingCrypto;
    }

    public void sendEmailForSellingCrypto(String message, String receiverEmail) {
        kafkaTemplate.send(topicForSellingCrypto, new EmailEntity(message, receiverEmail));
    }

    public void sendEmailForBuyingCrypto(String message, String receiverEmail) {
        kafkaTemplate.send(topicForBuyingCrypto, new EmailEntity(message, receiverEmail));
    }
}