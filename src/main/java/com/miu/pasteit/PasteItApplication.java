package com.miu.pasteit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class PasteItApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasteItApplication.class, args);
    }

}
