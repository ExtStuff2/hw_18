package com.fun.mailnotification.service;

import com.fun.mailnotification.domain.ReceiptDetailed;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiverService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(
            topics = "${spring.kafka.task.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMessages(ReceiptDetailed receipt) throws MessagingException {
        log.info("Received Message: " + receipt);
        double sum = 0;
        for (Object value : receipt.getAdditionalServices().values()) {
            sum += Double.valueOf(value.toString());
        }
        sum = sum + receipt.getServicePrice();
        String servicePayments = receipt.getServiceName() + " = " + receipt.getServicePrice() +
                "Additional services ->> " + receipt.getAdditionalServices().toString()
                + "Final price ->> " + String.valueOf(sum);
        log.info(servicePayments);
        emailService.sendEmailToEmployee("setup_your_mail_destination", receipt, sum);
        emailService.sendEmailToClient("setup_your_mail_destination", receipt, servicePayments);
    }

}
