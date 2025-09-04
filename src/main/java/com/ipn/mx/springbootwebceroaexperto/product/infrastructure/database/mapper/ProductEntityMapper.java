package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.mapper;

import com.ipn.mx.springbootwebceroaexperto.category.domain.Category;
import com.ipn.mx.springbootwebceroaexperto.category.infrastructure.CategoryEntity;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import com.ipn.mx.springbootwebceroaexperto.review.domain.Review;
import com.ipn.mx.springbootwebceroaexperto.review.infrastructure.ReviewEntity;
import org.mapstruct.*;

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

    @AfterMapping
    default void linkReviews(@MappingTarget ProductEntity productEntity) {// @AfterMapping a función linkReviews significa que, una vez realizados los mapeos, se va a ejecutar la función linkReviews. En esta función, siempre se va a estar grabando el ProductEntity (producto) especificado para cada una de sus reviews que este tenga, es decir, dicha función es para mantener la relación entre las reviews y su propio producto (productEntity). ESTA FUNCION ESTA DESTINADA PARA QUE NO SE PONGA COMO NULA LA ENTIDAD PRODUCT AL NO ESTAR SIENDO MAPEADA (IGNORADA PARA MAPEAR) el atributo en la reviews del productId cargandome asi los datos
        if (productEntity.getReviews() != null) {
            productEntity.getReviews().forEach(r -> r.setProduct(productEntity));
        }
    }
}

// @Mapping(target = "productDetail.product", ignore = true) de ProductEntity mapToProductEntity(Product product), significa que ignore el mapeo de la propiedad product del objeto productDetail que es propiedad de ProductEntity. Con esto se evitan posibles ciclos