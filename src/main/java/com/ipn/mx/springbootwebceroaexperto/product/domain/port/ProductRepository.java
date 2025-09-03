package com.ipn.mx.springbootwebceroaexperto.product.domain.port;

import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationQuery;
import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationResult;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.ProductFilter;

import java.util.Optional;

public interface ProductRepository {
    Product upsert(Product product);

    Optional<Product> findById(Long id);

    PaginationResult<Product> findAll(PaginationQuery paginationQuery, ProductFilter productFilter);

    void deleteById(Long id);
}
