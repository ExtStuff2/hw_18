package com.fun.mailnotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class MailNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailNotificationApplication.class, args);
    }

}
