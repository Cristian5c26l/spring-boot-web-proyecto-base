package com.ipn.mx.springbootwebceroaexperto.product.application.query.getById;

import com.ipn.mx.springbootwebceroaexperto.common.mediator.RequestHandler;
import com.ipn.mx.springbootwebceroaexperto.product.domain.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//public class GetProductByIdHandler implements RequestHandler<GetProductByIdRequest, Product> {// CreateProductRequest es una clase (T) que implementa la interface Request<Void> (Void es R, y es la respuesta)
public class GetProductByIdHandler implements RequestHandler<GetProductByIdRequest, GetProductByIdResponse> {
    private final ProductRepository productRepository;// A CreateProductHandler se le inyecta la dependencia productRepository a traves del constructor generado por RequiredArgsConstructor

    @Override
    public GetProductByIdResponse handle(GetProductByIdRequest request) {// CreateProductRequest es la peticion o request "T" y Void es la respuesta o response "R"
        //return productRepository.findById(request.getId()).get();// productRepository.findById(request.getId()) retorna un Optional<Product>, con el metodo get() se obtiene el producto

        Product product = productRepository.findById(request.getId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return new GetProductByIdResponse(product);
    }

    @Override
    public Class<GetProductByIdRequest> getRequestType() {// retornar tipo de peticion (CreateProductRequest es T)
        return GetProductByIdRequest.class;// CreateProductRequest.class corresponde a la clase CreateProductRequest que extiende o implementa la clase Request (por ejemplo, tipos como CreateProductRequest implements Request<Void>, que tiene el formato ? extends Class<?>)
    }
}
