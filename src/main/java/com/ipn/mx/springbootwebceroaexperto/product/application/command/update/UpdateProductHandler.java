package com.ipn.mx.springbootwebceroaexperto.product.application.command.update;

import com.ipn.mx.springbootwebceroaexperto.common.mediator.RequestHandler;
import com.ipn.mx.springbootwebceroaexperto.common.util.FileUtils;
import com.ipn.mx.springbootwebceroaexperto.product.domain.entity.Product;
import com.ipn.mx.springbootwebceroaexperto.product.domain.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateProductHandler implements RequestHandler<UpdateProductRequest, Void> {// CreateProductRequest es una clase (T) que implementa la interface Request<Void> (Void es R, y es la respuesta)

    private final ProductRepository productRepository;// A CreateProductHandler se le inyecta la dependencia productRepository a traves del constructor generado por RequiredArgsConstructor
    private final FileUtils fileUtils;

    @Override
    public Void handle(UpdateProductRequest request) {// CreateProductRequest es la peticion o request "T" y Void es la respuesta o response "R"

        log.info("Updating product with id {}", request.getId());

        String uniqueFileName = fileUtils.saveProductImage(request.getFile());

        Product product = Product
                .builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .image(uniqueFileName)
                .build();

        productRepository.upsert(product);

        log.info("Updated product with id {}", request.getId());

        return null;// null es vacio (Void, y esta en la parte de command porque este caso de uso CreateProductHandler no devuelve nada)
    }

    @Override
    public Class<UpdateProductRequest> getRequestType() {// retornar tipo de peticion (CreateProductRequest es T)
        return UpdateProductRequest.class;// CreateProductRequest.class corresponde a la clase CreateProductRequest que extiende o implementa la clase Request (por ejemplo, tipos como CreateProductRequest implements Request<Void>, que tiene el formato ? extends Class<?>)
    }
}
