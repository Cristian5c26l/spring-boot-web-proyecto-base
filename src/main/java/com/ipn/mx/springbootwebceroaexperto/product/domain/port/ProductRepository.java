package com.ipn.mx.springbootwebceroaexperto.product.domain.port;

import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationQuery;
import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationResult;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;

import java.util.Optional;

public interface ProductRepository {
    Product upsert(Product product);

    Optional<Product> findById(Long id);

    PaginationResult<Product> findAll(PaginationQuery paginationQuery);

    void deleteById(Long id);
}
