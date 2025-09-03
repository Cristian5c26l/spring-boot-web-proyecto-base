package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.mapper;

import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductEntityMapper {

    @Mapping(target = "productDetailEntity", source = "productDetail")
    @Mapping(target = "productDetailEntity.productEntity", ignore = true)
    ProductEntity mapToProductEntity(Product product);

    @Mapping(target = "productDetail", source = "productDetailEntity")
    @Mapping(target = "productDetail.product", ignore = true)
    Product mapToProduct(ProductEntity productEntity);
}

// @Mapping(target = "productDetailEntity", source = "productDetail") de ProductEntity mapToProductEntity(Product product), significa que la propiedad productDetail de Product, se va a mapear a la propiedad productDetailEntity de ProductEntity
// @Mapping(target = "productDetailEntity.productEntity", ignore = true) de ProductEntity mapToProductEntity(Product product), significa que ignore el mapeo de la propiedad productEntity del objeto productDetailEntity que es propiedad de ProductEntity. Con esto se evitan posibles ciclos