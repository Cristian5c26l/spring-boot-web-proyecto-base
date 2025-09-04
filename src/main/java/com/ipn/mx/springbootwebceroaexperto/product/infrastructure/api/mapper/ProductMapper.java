package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.mapper;

import com.ipn.mx.springbootwebceroaexperto.category.domain.Category;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.create.CreateProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.update.UpdateProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.CreateProductDto;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.ProductDto;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.ReviewDto;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.UpdateProductDto;
import com.ipn.mx.springbootwebceroaexperto.review.domain.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {
    CreateProductRequest mapToCreateProductRequest(CreateProductDto createProductDto);

    @Mapping(target = "provider", source = "productDetail.provider")
    ProductDto mapToProductDto(Product product);

    UpdateProductRequest mapToUpdateProductRequest(UpdateProductDto updateProductDto);

    @Mapping(target = "product", ignore = true)
    Review mapToReview(ReviewDto reviewDto);

    default List<String> mapToCategoryNames(List<Category> categories) {// mapToCategoryNames se usa internamente (dentro de mapToProductDto) para mapear categories (que es una lista de Category) de Product, a categories pero de tipo Lista de String
        return categories.stream().map(Category::getName).toList();
    }
}

// @Mapping(target = "provider", source = "productDetail.provider") significa hacer que el productDetail.provider de Product, se mapee al string “provider” de ProductDto
