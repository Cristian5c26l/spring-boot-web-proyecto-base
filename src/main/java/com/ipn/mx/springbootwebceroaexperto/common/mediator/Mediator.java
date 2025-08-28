package com.ipn.mx.springbootwebceroaexperto.common.mediator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Mediator {// Component hace que Mediator sea una clase inyectable a otra clase (@Repository internamente tambien tiene a @Component por lo cual Repository hace que una clase sea inyectable a otra)

    Map<? extends Class<?>, RequestHandler<?, ?>> requestHandlerMap;// ? extends Class<?> hace referencia a clases como CreateProductRequest por ejemplo porque extiende o implementa Request<Void>. RequestHandler<?, ?> hace referencia a clases como CreateProductHandler la cual implementa RequestHandler<CreateProductRequest, Void>

    public Mediator(List<RequestHandler<?, ?>> requestHandlers) {// requestHandlers contiene puras implementaciones o clases que implementan RequestHandler como por ejemplo CreateProductHandler
        this.requestHandlerMap = requestHandlers.stream().collect(Collectors.toMap(reqHand -> reqHand.getRequestType(), Function.identity()));// mapa con clave:valor *clase (como CreateProductRequest) que extiende o implementa de Request (que ocupa 1 generico)*:*clase (como CreateProductHandler) que extiende o implementa de RequestHandler (que ocupa 2 genericos)*.... Function.identity()) hace referencia al elemento reqHand de la lista requestHandlers
    }

    public <R, T extends Request<R>> R dispatch(T request) {// dispatch recibe una clase "T", la cual, en el mismo dispatch en "<R, T extends Request<R>>", se especifica que T es una clase que extiende de Request que recibe un generico "R" (como Void)
        RequestHandler<T, R> handler = (RequestHandler<T, R>) requestHandlerMap.get(request.getClass());

        if (handler == null) {
            log.error("No handler found for request type {}", request.getClass());
            throw new RuntimeException("No handler found for request type: " + request.getClass());
        }

        return handler.handle(request);
    }

    @Async
    public <R, T extends Request<R>> void dispatchAsync(T request) {
        this.dispatch(request);
    }
}
