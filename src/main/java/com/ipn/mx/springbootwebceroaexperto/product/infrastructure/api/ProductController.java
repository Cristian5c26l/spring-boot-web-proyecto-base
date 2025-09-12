package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api;

import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.Mediator;
import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationQuery;
import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationResult;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.create.CreateProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.create.CreateProductResponse;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.delete.DeleteProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.command.update.UpdateProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll.GetAllProductRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getAll.GetAllProductResponse;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getById.GetProductByIdRequest;
import com.ipn.mx.springbootwebceroaexperto.product.application.query.getById.GetProductByIdResponse;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.ProductFilter;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<PaginationResult<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax
    ) {

        log.info("Get all products");

        PaginationQuery paginationQuery = new PaginationQuery(pageNumber, pageSize, sortBy, direction);
        ProductFilter productFilter = new ProductFilter(name, description, priceMin, priceMax);

        GetAllProductRequest getAllProductRequest = new GetAllProductRequest(paginationQuery, productFilter);

        GetAllProductResponse response = mediator.dispatch(getAllProductRequest);
        PaginationResult<Product> productsPage = response.getProductsPage();// productsPage, que viene desde el caso de uso GetAllProductRequest, tiene la propiedad "content" que en este caso, es una Lista de "Product". A parte de esa propiedad "content" cuenta con otras como "page", "size", entre otras.

        PaginationResult<ProductDto> productDtoPaginationResult = new PaginationResult<>(
                productsPage.getContent().stream().map(productMapper::mapToProductDto).toList(),
                productsPage.getPage(),
                productsPage.getSize(),
                productsPage.getTotalPages(),
                productsPage.getTotalElements()
        );

        return ResponseEntity.ok(productDtoPaginationResult);
    }

    @Operation(summary = "Get product", description = "Get product")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid UpdateProductDto updateProductDto) {

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

        log.info("Saving product");

        CreateProductRequest createProductRequest = productMapper.mapToCreateProductRequest(createProductDto);

        CreateProductResponse response = mediator.dispatch(createProductRequest);// ejecucion de caso de uso CreateProductHandler, que es el valor de la llave que en este caso es de tipo CreateProductRequest

        Product product = response.getProduct();

        log.info("Saved product with id {}", product.getId());

        return ResponseEntity.created(URI.create("/api/v1/products/".concat(product.getId().toString()))).build();
    }


}
