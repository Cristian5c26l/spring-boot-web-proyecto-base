package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api;

import com.ipn.mx.springbootwebceroaexperto.common.mediator.Mediator;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.create.CreateProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.delete.DeleteProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.update.UpdateProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll.GetAllProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll.GetAllProductResponse;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getById.GetProductByIdRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getById.GetProductByIdResponse;
import com.ipn.mx.springbootwebceroaexperto.product.domain.Product;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.ProductDto;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final Mediator mediator;// dependencia mediator inyectada a ProductController
    private final ProductMapper productMapper;// dependencia mediator inyectada a ProductController

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false) String pageSize) {

        GetAllProductResponse response = mediator.dispatch(new GetAllProductRequest());
        List<ProductDto> dtoProducts = response.getProducts().stream()
                .map(product -> productMapper.mapToProductDto(product))
                .toList();

        return ResponseEntity.ok(dtoProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {

        GetProductByIdResponse getProductByIdResponse = mediator.dispatch(new GetProductByIdRequest(id));// dispatch en este caso ejecuta el caso de uso GetProductByIdHandler mediante su metodo handle, y se obtiene lo que este devuelve, que en este caso es una respuesta de tipo GetByIdResponse
        Product product = getProductByIdResponse.getProduct();
        ProductDto productDto = productMapper.mapToProductDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto) {

        UpdateProductRequest updateProductRequest = productMapper.mapToUpdateProductRequest(productDto);
        mediator.dispatch(updateProductRequest);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        DeleteProductRequest deleteProductRequest = new DeleteProductRequest(id);
        mediator.dispatch(deleteProductRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductDto productDto) {
        //mediator.dispatch(new CreateProductRequest(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImage()));// dispatch recibe una clase "T", la cual, en el mismo dispatch en "<R, T extends Request<R>>", se especifica que T es una clase que extiende de Request que recibe un generico "R" (como Void)

        CreateProductRequest createProductRequest = productMapper.mapToCreateProductRequest(productDto);

        mediator.dispatch(createProductRequest);// ejecucion de caso de uso CreateProductHandler, que es el valor de la llave que en este caso es de tipo CreateProductRequest

        return ResponseEntity.created(URI.create("/api/v1/products/".concat(productDto.getId().toString()))).build();
    }


}
