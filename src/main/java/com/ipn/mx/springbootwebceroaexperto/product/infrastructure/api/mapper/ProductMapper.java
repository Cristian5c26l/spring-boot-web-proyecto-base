package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.mapper;

import com.ipn.mx.springbootwebceroaexperto.product.application.command.create.CreateProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.domain.Product;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {
    CreateProductRequest mapToCreateProductRequest(ProductDto productDto);

    ProductDto mapToCreateProductDto(Product product);
}
