package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database;

import com.ipn.mx.springbootwebceroaexperto.product.domain.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.ProductRepository;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.mapper.ProductEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final List<ProductEntity> products = new ArrayList<>();

    private final ProductEntityMapper productEntityMapper;// inyectar esta dependencia a ProductRepositoryImpl

    @Override
    public void upsert(Product product) {// product viene de la capa de dominio (logica de negocio con la que se da vida a la aplicacion). ProductEntity hace referencia a la tabla de una  base de datos
        ProductEntity productEntity = productEntityMapper.mapToProductEntity(product);
        products.add(productEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(productEntity -> productEntityMapper.mapToProduct(productEntity));
    }

    @Override
    public List<Product> findAll() {
        return products.stream()
                .map(productEntity -> productEntityMapper.mapToProduct(productEntity))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }
}
