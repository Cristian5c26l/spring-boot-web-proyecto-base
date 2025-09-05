package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.repository.QueryProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("!test")
// Para que este ProductSeeder no se ejecute al levantar la aplicacion en modo test (como correr el archivo ProductIT)
public class ProductSeeder implements CommandLineRunner {

    private final QueryProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    @Override
    public void run(String... args) throws Exception {
        long count = productRepository.count();

        if (count > 0) {
            return;
        }

        Resource resource = resourceLoader.getResource("classpath:products.json");// classpath = src/main/java/resources

        List<ProductEntity> products = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });

        productRepository.saveAll(products);
    }
}
