package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api;

import com.ipn.mx.springbootwebceroaexperto.common.mediator.Mediator;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll.GetAllProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll.GetAllProductResponse;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.ProductDto;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private Mediator mediator;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    @Test
    public void shouldReturnAllProducts() {

        GetAllProductResponse getAllProductResponse = new GetAllProductResponse(List.of(
                Product.builder().build(),
                Product.builder().build()
        ));

        when(mediator.dispatch(new GetAllProductRequest())).thenReturn(getAllProductResponse);

        ProductDto productDto = new ProductDto();
        productDto.setName("Product Name");

        when(productMapper.mapToProductDto(any(Product.class))).thenReturn(productDto);

        ResponseEntity<List<ProductDto>> response = productController.getAllProducts("5");

        List<ProductDto> products = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(0, products.size());
        assertEquals(2, products.size());
    }
}