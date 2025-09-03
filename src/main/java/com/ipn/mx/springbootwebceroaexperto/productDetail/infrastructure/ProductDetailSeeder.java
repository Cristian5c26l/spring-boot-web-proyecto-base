package com.ipn.mx.springbootwebceroaexperto.productDetail.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDetailSeeder implements CommandLineRunner { // CommandLineRunner permite que, al iniciarse la aplicacion de spring boot, se ejecute de inmediato el metodo run

    private final QueryProductDetailRepository queryProductDetailRepository;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        long count = queryProductDetailRepository.count();

        if (count > 0) {
            return;
        }

        Resource resource = resourceLoader.getResource("classpath:products_details.json");

        List<ProductDetailEntity> productDetailEntities = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });

        queryProductDetailRepository.saveAll(productDetailEntities);
    }
}
