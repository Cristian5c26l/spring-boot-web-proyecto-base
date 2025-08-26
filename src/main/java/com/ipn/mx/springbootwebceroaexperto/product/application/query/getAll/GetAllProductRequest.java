package com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll;

import com.ipn.mx.springbootwebceroaexperto.common.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

//public class GetProductByIdRequest implements Request<Product> {// Para esta peticion de obtener un producto por id, la respuesta es un Product (Caso de uso GetProductById, que esta en la capa de Aplicacion, conoce a Product que esta en la capa de Domain). Capa de Aplicacion solo conoce a Capa de Dominio, no de Infraestructura (que contiene a ProductEntity)
@Data
@AllArgsConstructor
public class GetAllProductRequest implements Request<GetAllProductResponse> {
    
}
