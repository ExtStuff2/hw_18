package com.fun.mailnotification.service;

import com.fun.mailnotification.domain.ReceiptDetailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmailToEmployee(String to, ReceiptDetailed receiptDetailed, double sum) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Subject: Receipt payment number ->> " + receiptDetailed.getId());
        message.setText("Client " + receiptDetailed.getClientName() + "successfully paid receipt ->> " +
                receiptDetailed.getId() + " Receipt sum ->> " + sum
        );
        emailSender.send(message);
    }

    public void sendEmailToClient(String to, ReceiptDetailed receiptDetailed, String servicesPrices) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Subject: Receipt payment number ->> " + receiptDetailed.getId());
        message.setText("Thank you  " + receiptDetailed.getClientName() + "for using our payment service " +
                "you paid for ->> " + servicesPrices
        );
        emailSender.send(message);
    }
}
