package com.ipn.mx.springbootwebceroaexperto.product.application.command.update;

import com.ipn.mx.springbootwebceroaexperto.category.domain.Category;
import com.ipn.mx.springbootwebceroaexperto.category.infrastructure.CategoryEntityMapper;
import com.ipn.mx.springbootwebceroaexperto.category.infrastructure.QueryCategoryRepository;
import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.RequestHandler;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.exception.ProductNotFoundException;
import com.ipn.mx.springbootwebceroaexperto.product.domain.port.ProductRepository;
import com.ipn.mx.springbootwebceroaexperto.productDetail.domain.ProductDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class UpdateProductHandler implements RequestHandler<UpdateProductRequest, Void> {// CreateProductRequest es una clase (T) que implementa la interface Request<Void> (Void es R, y es la respuesta)

    private final ProductRepository productRepository;// A CreateProductHandler se le inyecta la dependencia productRepository a traves del constructor generado por RequiredArgsConstructor
    private final QueryCategoryRepository queryCategoryRepository;// esto no deberia de ser... pero para fines didacticos se puso aqui
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public Void handle(UpdateProductRequest request) {// CreateProductRequest es la peticion o request "T" y Void es la respuesta o response "R"

        log.info("Updating product with id {}", request.getId());

        Product product = productRepository.findById(request.getId()).orElseThrow(() -> new ProductNotFoundException(request.getId()));

        ProductDetail productDetail = product.getProductDetail();

        productDetail.setProvider(request.getProvider());
        product.getReviews().add(request.getReview());
        Category category = queryCategoryRepository.findById(request.getCategoryId())
                .map(categoryEntityMapper::mapToCategory)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.getCategories().add(category);// agregado una categoria, ya existente en tabla de categorias, al producto "products"
        productRepository.upsert(product);

        log.info("Updated product with id {}", request.getId());

        return null;// null es vacio (Void, y esta en la parte de command porque este caso de uso CreateProductHandler no devuelve nada)
    }

    @Override
    public Class<UpdateProductRequest> getRequestType() {// retornar tipo de peticion (CreateProductRequest es T)
        return UpdateProductRequest.class;// CreateProductRequest.class corresponde a la clase CreateProductRequest que extiende o implementa la clase Request (por ejemplo, tipos como CreateProductRequest implements Request<Void>, que tiene el formato ? extends Class<?>)
    }
}
