package com.ipn.mx.springbootwebceroaexperto.category.infrastructure;

import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<ProductEntity> products;
}
