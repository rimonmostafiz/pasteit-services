package com.miu.pasteit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Rimon Mostafiz
 */
@EnableMongoRepositories("com.miu.pasteit.repository")
@EnableJpaRepositories(excludeFilters =
@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.miu.pasteit.repository.*")
)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigurationProperties
public class PasteItApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasteItApplication.class, args);
    }

}
