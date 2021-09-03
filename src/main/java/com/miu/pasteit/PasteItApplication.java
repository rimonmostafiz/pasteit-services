package com.miu.pasteit;

import com.miu.pasteit.repository.PasteRepository;
import com.miu.pasteit.repository.UserRepository;
import com.miu.pasteit.repository.UserRolesRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories("com.miu.pasteit.repository")
@EnableJpaRepositories(excludeFilters =
@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.miu.pasteit.repository.*" ))
@SpringBootApplication
@EnableConfigurationProperties
public class PasteItApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasteItApplication.class, args);
    }

}
