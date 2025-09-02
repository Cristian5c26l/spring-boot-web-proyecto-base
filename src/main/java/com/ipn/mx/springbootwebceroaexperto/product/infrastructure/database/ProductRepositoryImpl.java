package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database;

import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.port.ProductRepository;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.mapper.ProductEntityMapper;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.repository.QueryProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    private final QueryProductRepository repository;// manipulacion de la tabla "products" de la base de datos spring de postgres

    private final ProductEntityMapper productEntityMapper;// inyectar esta dependencia a ProductRepositoryImpl

    @Override
    public Product upsert(Product product) {// product viene de la capa de dominio (logica de negocio con la que se da vida a la aplicacion). ProductEntity hace referencia a la tabla de una  base de datos
        ProductEntity productEntity = productEntityMapper.mapToProductEntity(product);
        ProductEntity save = repository.save(productEntity);// sirve para agregar un nuevo producto o para actualizar uno existente
        return productEntityMapper.mapToProduct(save);
    }

    @Cacheable(value = "products", key = "#id")
    @Override
    public Optional<Product> findById(Long id) {

        log.info("Finding product with id {}", id);

        return repository.findById(id).map(productEntity -> productEntityMapper.mapToProduct(productEntity));
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll().stream()
                .map(productEntity -> productEntityMapper.mapToProduct(productEntity))
                .toList();
    }

    @CacheEvict(value = "products", key = "#id")
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
