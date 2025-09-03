package com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll;

import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationResult;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetAllProductResponse {
    private PaginationResult<Product> productsPage;// en este caso, productsPage es un objeto que internamente tendrá una List de objetos tipo "Product"
}
