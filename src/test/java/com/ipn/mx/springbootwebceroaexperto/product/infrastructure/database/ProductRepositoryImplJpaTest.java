package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database;

import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.repository.QueryProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProductRepositoryImplJpaTest {

    @Autowired
    private QueryProductRepository repository;// AutoWired es inyeccion de dependencias (a esta clase ProductRepositoryImplJpaTest)... Este repositorio es sobre la entidad o tabla ProductEntity, la cual, existe gracias a la existencia de la base de datos en memoria h2 al crearse con @DataJpaTest

    @Test
    void shouldNotReturnProductWhenNotFound() {
        Optional<ProductEntity> optionalProduct = repository.findById(1L);
        assertTrue(optionalProduct.isEmpty());
    }

    @Test
    void shouldReturnProductWhenFound() {
        ProductEntity save = repository.save(new ProductEntity());

        Optional<ProductEntity> optionalProduct = repository.findById(save.getId());// save.getId() = 1L
        assertTrue(optionalProduct.isPresent());
    }
}