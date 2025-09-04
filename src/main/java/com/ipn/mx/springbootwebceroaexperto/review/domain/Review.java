package com.ipn.mx.springbootwebceroaexperto.review.domain;

import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {
    private Long id;
    private String comment;
    private Integer score;

    private Product product;
}
