package com.ipn.mx.springbootwebceroaexperto.product.domain.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product with id ".concat(id.toString()).concat(" not found"));
    }
}
