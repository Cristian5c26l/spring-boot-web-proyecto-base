package com.ipn.mx.springbootwebceroaexperto.category.domain;

import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Category {
    private Long id;
    private String name;

    List<Product> products;
}
