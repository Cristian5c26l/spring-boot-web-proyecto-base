package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.api;

import com.ipn.mx.springbootwebceroaexperto.common.mediator.Mediator;
import com.ipn.mx.springbootwebceroaexperto.product.application.ProductCreateRequest;
import com.ipn.mx.springbootwebceroaexperto.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final Mediator mediator;// dependencia mediator inyectada a ProductController

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String pageSize) {
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product.get());
    }

    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {

        Optional<Product> productSelected = products.stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst();

        if (productSelected.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product productUpdated = productSelected.get();

        productUpdated.setName(product.getName());
        productUpdated.setDescription(product.getDescription());
        productUpdated.setPrice(product.getPrice());
        productUpdated.setImage(product.getImage());

        return ResponseEntity.ok(productUpdated);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        products.removeIf(p -> p.getId().equals(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<Void> saveProduct(@RequestBody Product product) {
        mediator
                .dispatch(new ProductCreateRequest(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImage()));// dispatch recibe una clase "T", la cual, en el mismo dispatch en "<R, T extends Request<R>>", se especifica que T es una clase que extiende de Request que recibe un generico "R" (como Void)

        return ResponseEntity.created(URI.create("/api/v1/products/".concat(product.getId().toString()))).build();
    }


}
