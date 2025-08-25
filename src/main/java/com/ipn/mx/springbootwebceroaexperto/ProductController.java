package com.ipn.mx.springbootwebceroaexperto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    public List<Product> products;

    public ProductController() {
        this.products = new ArrayList<>();
        products.add(Product.builder().id(1L).name("Product 1").description("Description 1").price(100.0).image("image").build());
        products.add(Product.builder().id(2L).name("Product 2").description("Description 2").price(200.0).image("image2").build());
    }

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
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        products.removeIf(p -> p.getId().equals(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        products.add(product);
        return ResponseEntity
                .created(URI.create("/api/v1/products/".concat(product.getId().toString())))
                .build();
    }


}
