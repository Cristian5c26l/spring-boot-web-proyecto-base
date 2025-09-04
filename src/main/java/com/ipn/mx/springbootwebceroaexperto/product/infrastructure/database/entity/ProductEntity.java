package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity;

import com.ipn.mx.springbootwebceroaexperto.productDetail.infrastructure.ProductDetailEntity;
import com.ipn.mx.springbootwebceroaexperto.review.infrastructure.ReviewEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 500)
    private String description;
    private Double price;
    private String image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_detail_id")
    private ProductDetailEntity productDetail;

    @OneToMany(mappedBy = "product")
    private List<ReviewEntity> reviews = new ArrayList<>();
}

// @JoinColumn(name = "product_detail_id") permite insertar un nuevo campo añadido a tabla "products" llamado "product_detail_id". Este campo hace referencia al campo identificador @id de la tabla "product_details" (ProductDetailEntity). Asi se ha creado esta integridad referencial. Este campo product_detail_id de esta tabla "products" tendra valores del campo "id" de la tabla "product_details" (ProductDetailEntity)
