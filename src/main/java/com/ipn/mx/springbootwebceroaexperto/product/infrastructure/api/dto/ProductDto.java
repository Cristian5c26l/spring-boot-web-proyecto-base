package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;// viene de la tabla products (ProductEntity)

    private String provider;// viene de la tabla product_details (ProductDetailEntity)... a traves del mapeo de un Product a este ProductDto (ver ProductMapper y ProductMapperImpl)
    private List<ReviewDto> reviews;// viene de la tabla reviews ... a traves del mapeo de un Product a este ProductDto (ver ProductMapper y ProductMapperImpl)
}
