package com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll;

import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.RequestHandler;
import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationResult;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
//public class GetProductByIdHandler implements RequestHandler<GetProductByIdRequest, Product> {// CreateProductRequest es una clase (T) que implementa la interface Request<Void> (Void es R, y es la respuesta)
public class GetAllProductHandler implements RequestHandler<GetAllProductRequest, GetAllProductResponse> {
    private final ProductRepository productRepository;// A CreateProductHandler se le inyecta la dependencia productRepository a traves del constructor generado por RequiredArgsConstructor

    @Override
    public GetAllProductResponse handle(GetAllProductRequest request) {// CreateProductRequest es la peticion o request "T" y Void es la respuesta o response "R"
        //return productRepository.findById(request.getId()).get();// productRepository.findById(request.getId()) retorna un Optional<Product>, con el metodo get() se obtiene el producto

        log.info("Getting all products");

        PaginationResult<Product> products = productRepository.findAll(request.getPaginationQuery(), request.getProductFilter());

        return new GetAllProductResponse(products);
    }

    @Override
    public Class<GetAllProductRequest> getRequestType() {// retornar tipo de peticion (CreateProductRequest es T)
        return GetAllProductRequest.class;// CreateProductRequest.class corresponde a la clase CreateProductRequest que extiende o implementa la clase Request (por ejemplo, tipos como CreateProductRequest implements Request<Void>, que tiene el formato ? extends Class<?>)
    }
}
