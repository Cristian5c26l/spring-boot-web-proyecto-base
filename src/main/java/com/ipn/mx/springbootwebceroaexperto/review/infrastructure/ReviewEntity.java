package com.ipn.mx.springbootwebceroaexperto.review.infrastructure;

import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
}

// Con @ManyToOne se indica que varias Reviews estaran asociadas a 1 producto
// Con @JoinColumn(name = "product_id") se indica que reviews tendra un campo llamado product_id, que tendra valores de la llave primaria de la tabla products (productEntity)