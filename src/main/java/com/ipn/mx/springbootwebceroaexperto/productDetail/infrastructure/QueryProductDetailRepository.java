package com.ipn.mx.springbootwebceroaexperto.productDetail.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryProductDetailRepository extends JpaRepository<ProductDetailEntity, Long> {
}
