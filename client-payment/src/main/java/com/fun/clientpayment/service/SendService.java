package com.fun.clientpayment.service;

import com.fun.clientpayment.model.ReceiptDetailed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendService {

    private final KafkaTemplate<String, ReceiptDetailed> kafkaTemplate;

    @Value("${spring.kafka.task.topic}")
    private String topicName;

    public void sendMessage(ReceiptDetailed msg) {
        kafkaTemplate.send(topicName, msg);
    }

}
