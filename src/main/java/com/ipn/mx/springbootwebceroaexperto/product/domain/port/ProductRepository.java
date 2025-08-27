package com.ipn.mx.springbootwebceroaexperto.product.domain.port;

import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void upsert(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);
}
