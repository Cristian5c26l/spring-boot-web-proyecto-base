package com.ipn.mx.springbootwebceroaexperto.review.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ReviewSeeder implements CommandLineRunner {
    private final QueryReviewRepository queryReviewRepository;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        long count = queryReviewRepository.count();

        if (count > 0) {
            return;
        }

        Resource resource = resourceLoader.getResource("classpath:reviews.json");

        List<ReviewEntity> reviewEntities = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });

        queryReviewRepository.saveAll(reviewEntities);
    }
}
