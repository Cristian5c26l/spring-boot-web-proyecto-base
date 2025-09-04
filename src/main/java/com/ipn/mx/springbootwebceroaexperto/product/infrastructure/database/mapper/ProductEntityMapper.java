package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.mapper;

import com.ipn.mx.springbootwebceroaexperto.category.domain.Category;
import com.ipn.mx.springbootwebceroaexperto.category.infrastructure.CategoryEntity;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import com.ipn.mx.springbootwebceroaexperto.review.domain.Review;
import com.ipn.mx.springbootwebceroaexperto.review.infrastructure.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductEntityMapper {

    @Mapping(target = "productDetail.product", ignore = true)
    ProductEntity mapToProductEntity(Product product);

    @Mapping(target = "productDetail.product", ignore = true)
    Product mapToProduct(ProductEntity productEntity);

    @Mapping(target = "product", ignore = true)
    Review mapToReview(ReviewEntity reviewEntity);

    @Mapping(target = "product", ignore = true)
    ReviewEntity mapToReviewEntity(Review review);

    @Mapping(target = "products", ignore = true)
    Category mapToCategory(CategoryEntity categoryEntity);

    @Mapping(target = "products", ignore = true)
    CategoryEntity mapToCategoryEntity(Category category);
}

// @Mapping(target = "productDetail.product", ignore = true) de ProductEntity mapToProductEntity(Product product), significa que ignore el mapeo de la propiedad product del objeto productDetail que es propiedad de ProductEntity. Con esto se evitan posibles ciclos