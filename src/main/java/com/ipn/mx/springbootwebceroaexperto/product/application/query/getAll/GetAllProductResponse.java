package com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll;

import com.ipn.mx.springbootwebceroaexperto.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GetAllProductResponse {
    private List<Product> products;
}
