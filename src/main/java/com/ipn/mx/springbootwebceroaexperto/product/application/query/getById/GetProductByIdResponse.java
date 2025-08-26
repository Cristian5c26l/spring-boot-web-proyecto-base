package com.ipn.mx.springbootwebceroaexperto.product.application.query.getById;

import com.ipn.mx.springbootwebceroaexperto.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetProductByIdResponse {
    private Product product;
}
