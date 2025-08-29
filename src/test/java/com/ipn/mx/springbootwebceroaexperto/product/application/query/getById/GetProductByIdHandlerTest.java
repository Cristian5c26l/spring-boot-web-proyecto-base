package com.ipn.mx.springbootwebceroaexperto.product.application.query.getById;

import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.exception.ProductNotFoundException;
import com.ipn.mx.springbootwebceroaexperto.product.domain.port.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductByIdHandlerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductByIdHandler getProductByIdHandler;

    @Test
    public void shouldReturnProductWhenFound() {

        // Arrange
        long productId = 1L;
        Product product = Product.builder().id(productId).build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        GetProductByIdRequest request = new GetProductByIdRequest(productId);

        // Act
        GetProductByIdResponse response = getProductByIdHandler.handle(request);

        // Assert
        assertNotNull(response);
        assertEquals(productId, response.getProduct().getId());
    }

    @Test
    public void shouldThrowExceptionWhenProductNotFound() {

        // Arrange
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        GetProductByIdRequest request = new GetProductByIdRequest(productId);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> getProductByIdHandler.handle(request));// () -> getProductByIdHandler.handle(request) es una funcion lambda que llama al metodo handle y como esta envuelta en assertThrows, comprobamos que se lanza la excepcion


    }


}