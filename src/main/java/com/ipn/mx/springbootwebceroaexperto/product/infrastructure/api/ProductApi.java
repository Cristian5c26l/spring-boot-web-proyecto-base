package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api;

import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationResult;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.CreateProductDto;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.ProductDto;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.UpdateProductDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductApi {

    ResponseEntity<PaginationResult<ProductDto>> getAllProducts(int pageNumber, int pageSize);

    ResponseEntity<ProductDto> getProductById(@PathVariable Long id);

    ResponseEntity<Void> updateProduct(@ModelAttribute @Valid UpdateProductDto updateProductDto);

    ResponseEntity<Void> deleteProduct(@PathVariable Long id);

    ResponseEntity<Void> saveProduct(@ModelAttribute @Valid CreateProductDto createProductDto);
}
