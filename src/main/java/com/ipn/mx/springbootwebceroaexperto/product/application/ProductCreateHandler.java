package com.ipn.mx.springbootwebceroaexperto.product.application;

import com.ipn.mx.springbootwebceroaexperto.common.mediator.RequestHandler;
import com.ipn.mx.springbootwebceroaexperto.product.domain.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCreateHandler implements RequestHandler<ProductCreateRequest, Void> {// ProductCreateRequest es una clase (T) que implementa la interface Request<Void> (Void es R, y es la respuesta)

    private final ProductRepository productRepository;// A ProductCreateHandler se le inyecta la dependencia productRepository a traves del constructor generado por RequiredArgsConstructor

    @Override
    public Void handle(ProductCreateRequest request) {// ProductCreateRequest es la peticion o request "T" y Void es la respuesta o response "R"
        Product product = Product
                .builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .image(request.getImage())
                .build();

        productRepository.upsert(product);

        return null;// null es vacio (Void)
    }

    @Override
    public Class<ProductCreateRequest> getRequestType() {// retornar tipo de peticion (ProductCreateRequest es T)
        return ProductCreateRequest.class;// ProductCreateRequest.class corresponde a la clase ProductCreateRequest que extiende o implementa la clase Request (por ejemplo, tipos como ProductCreateRequest implements Request<Void>, que tiene el formato ? extends Class<?>)
    }
}
