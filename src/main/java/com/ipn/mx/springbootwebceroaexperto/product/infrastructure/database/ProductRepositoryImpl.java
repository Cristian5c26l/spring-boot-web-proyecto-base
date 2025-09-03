package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database;

import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationQuery;
import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationResult;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.port.ProductRepository;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.mapper.ProductEntityMapper;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.repository.QueryProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

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
    public PaginationResult<Product> findAll(PaginationQuery paginationQuery) {

        PageRequest pageRequest = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize());

        Page<ProductEntity> page = repository.findAll(pageRequest);// PageRequest es una implementacion de Pageable

        return new PaginationResult<>(
                page.getContent().stream().map(productEntityMapper::mapToProduct).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    @CacheEvict(value = "products", key = "#id")
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
