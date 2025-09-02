package com.ipn.mx.springbootwebceroaexperto.product.application.command.create;

import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateProductResponse {
    private Product product;
}
