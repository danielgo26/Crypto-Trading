package com.trading212.emailsender.email.service;

import com.trading212.emailsender.email.model.EmailEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailEventListenerService {

    private static final String SOLD_SHARES_SUBJECT = "Sold shares";
    private static final String BOUGHT_SHARES_SUBJECT = "Bought shares";

    private final EmailSenderService emailSenderService;

    public EmailEventListenerService(final EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topics.sell}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeSellEvent(EmailEntity emailData) {
        System.out.println(emailData.content() + " Email: " + emailData.receiverEmail());

        emailSenderService.sendEmail(emailData.receiverEmail(), SOLD_SHARES_SUBJECT, emailData.content());
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topics.buy}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeBuyEvent(EmailEntity emailData) {
        System.out.println(emailData.content() + " Email: " + emailData.receiverEmail());

        emailSenderService.sendEmail(emailData.receiverEmail(), BOUGHT_SHARES_SUBJECT, emailData.content());
    }
}