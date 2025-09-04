package com.ipn.mx.springbootwebceroaexperto.product.domain.entity;

import com.ipn.mx.springbootwebceroaexperto.category.domain.Category;
import com.ipn.mx.springbootwebceroaexperto.productDetail.domain.ProductDetail;
import com.ipn.mx.springbootwebceroaexperto.review.domain.Review;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;

    private ProductDetail productDetail;
    private List<Review> reviews;
    private List<Category> categories;
}
