package com.ipn.mx.springbootwebceroaexperto.productDetail.infrastructure;

import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_details")
public class ProductDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String specifications;
    private String warranty;
    private String provider;

    @OneToOne(mappedBy = "productDetail")
    private ProductEntity product;
}

// @OneToOne(mappedBy = "productDetailEntity") permite que la relacion establecida en ProductEntity, que es de 1 a 1 entre ProductEntity y ProductDetailEntity, funcione
