package com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database;

import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationQuery;
import com.ipn.mx.springbootwebceroaexperto.common.domain.PaginationResult;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.ProductFilter;
import com.ipn.mx.springbootwebceroaexperto.product.domain.port.ProductRepository;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.entity.ProductEntity;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.mapper.ProductEntityMapper;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.repository.QueryProductRepository;
import com.ipn.mx.springbootwebceroaexperto.product.infrastructure.database.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public PaginationResult<Product> findAll(PaginationQuery paginationQuery, ProductFilter productFilter) {

        PageRequest pageRequest = PageRequest.of(
                paginationQuery.getPage(),// inicio paginacion
                paginationQuery.getSize(),// fin paginacion
                Sort.by(Sort.Direction.fromString(paginationQuery.getDirection()), paginationQuery.getSortBy())// ordenar los resultados de la paginacion
        );// es posible que los resultados paginados se ordenen por varios campos

        Specification<ProductEntity> specification = Specification.allOf(
                ProductSpecification.byName(productFilter.getName())
                        .and(ProductSpecification.byDescription(productFilter.getDescription()))
                        .and(ProductSpecification.byPrice(productFilter.getPriceMin(), productFilter.getPriceMax()))
        );

        Page<ProductEntity> page = repository.findAll(specification, pageRequest);// PageRequest es una implementacion de Pageable

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
