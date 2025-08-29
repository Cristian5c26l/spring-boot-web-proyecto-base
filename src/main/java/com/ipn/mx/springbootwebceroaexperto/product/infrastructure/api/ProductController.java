package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api;

import com.ipn.mx.springbootwebceroaexperto.common.mediator.Mediator;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.create.CreateProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.delete.DeleteProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.update.UpdateProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll.GetAllProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll.GetAllProductResponse;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getById.GetProductByIdRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getById.GetProductByIdResponse;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.CreateProductDto;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.ProductDto;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.dto.UpdateProductDto;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "Product API operations")
@RequiredArgsConstructor
@Slf4j
public class ProductController implements ProductApi {

    private final Mediator mediator;// dependencia mediator inyectada a ProductController
    private final ProductMapper productMapper;// dependencia mediator inyectada a ProductController

    @Operation(summary = "Get all products", description = "Get all products")
    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false) String pageSize) {

        log.info("Get all products");

        GetAllProductResponse response = mediator.dispatch(new GetAllProductRequest());
        List<ProductDto> dtoProducts = response.getProducts().stream()
                .map(product -> productMapper.mapToProductDto(product))
                .toList();

        log.info("Found {} products", dtoProducts.size());

        return ResponseEntity.ok(dtoProducts);
    }

    @Operation(summary = "Get product", description = "Get product")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {

        log.info("Getting product with id {}", id);

        GetProductByIdResponse getProductByIdResponse = mediator.dispatch(new GetProductByIdRequest(id));// dispatch en este caso ejecuta el caso de uso GetProductByIdHandler mediante su metodo handle, y se obtiene lo que este devuelve, que en este caso es una respuesta de tipo GetByIdResponse
        Product product = getProductByIdResponse.getProduct();
        ProductDto productDto = productMapper.mapToProductDto(product);

        log.info("Found product with id {}", productDto.getId());

        return ResponseEntity.ok(productDto);
    }

    @Operation(summary = "Update product", description = "Update product")
    @PutMapping("")
    public ResponseEntity<Void> updateProduct(@ModelAttribute @Valid UpdateProductDto updateProductDto) {

        log.info("Updating product with id {}", updateProductDto.getId());

        UpdateProductRequest updateProductRequest = productMapper.mapToUpdateProductRequest(updateProductDto);
        mediator.dispatch(updateProductRequest);

        log.info("Updated product with id {}", updateProductDto.getId());

        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Delete product", description = "Delete product")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        log.info("Deleting product with id {}", id);

        DeleteProductRequest deleteProductRequest = new DeleteProductRequest(id);
        mediator.dispatchAsync(deleteProductRequest);

        log.info("Deleted product with id {}", id);

        return ResponseEntity.accepted().build();// codigo de estado 202 (accepted) indica que la peticion se acepto correctamente, y que esta se procesara asincronamente o en segundo plano
    }

    @Operation(summary = "Create product", description = "Create product")
    @PostMapping("")
    public ResponseEntity<Void> saveProduct(@ModelAttribute @Valid CreateProductDto createProductDto) {
        //mediator.dispatch(new CreateProductRequest(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImage()));// dispatch recibe una clase "T", la cual, en el mismo dispatch en "<R, T extends Request<R>>", se especifica que T es una clase que extiende de Request que recibe un generico "R" (como Void)

        log.info("Saving product with id {}", createProductDto.getId());

        CreateProductRequest createProductRequest = productMapper.mapToCreateProductRequest(createProductDto);

        mediator.dispatch(createProductRequest);// ejecucion de caso de uso CreateProductHandler, que es el valor de la llave que en este caso es de tipo CreateProductRequest

        log.info("Saved product with id {}", createProductRequest.getId());

        return ResponseEntity.created(URI.create("/api/v1/products/".concat(createProductDto.getId().toString()))).build();
    }


}
