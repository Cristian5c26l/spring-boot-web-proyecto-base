package com.ipn.mx.springbootwebceroaexperto.productDetail.domain;

import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetail {
    private long id;
    private String specifications;
    private String warranty;
    private String provider;

    private Product product;
}
