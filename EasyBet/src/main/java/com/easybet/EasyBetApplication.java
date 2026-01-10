package com.easybet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class EasyBetApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyBetApplication.class, args);
    }

}
