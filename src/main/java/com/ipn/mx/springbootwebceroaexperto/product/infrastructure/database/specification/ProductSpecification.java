package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.specification;

import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<ProductEntity> byName(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(root.get("name"), "%" + name + "%");// cb.like rootget("name") es como "where name like...". cb.equal es como "where": where name (get("name")) = name (variable string)
    }

    public static Specification<ProductEntity> byDescription(String description) {
        return (root, query, cb) -> description == null ? null : cb.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<ProductEntity> byPrice(Double priceMin, Double priceMax) {
        return (root, query, cb) -> priceMin == null || priceMax == null ? null : cb.between(root.get("price"), priceMin, priceMax);
    }
}
