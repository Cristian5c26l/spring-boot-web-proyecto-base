package com.ipn.mx.springbootwebceroaexperto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;
}
