package com.miu.pasteit;

import com.miu.pasteit.repository.mongo.FeedbackRepository;
import com.miu.pasteit.repository.mongo.PasteRepository;
import com.miu.pasteit.repository.mongo.activity.ActivityPasteRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Rimon Mostafiz
 */
@EnableMongoRepositories("com.miu.pasteit.repository.*")
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {PasteRepository.class,
                ActivityPasteRepository.class,
                FeedbackRepository.class})
)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigurationProperties
public class PasteItApplication {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/pasteit-services").allowedOrigins("http://localhost:3000");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(PasteItApplication.class, args);
    }

}
