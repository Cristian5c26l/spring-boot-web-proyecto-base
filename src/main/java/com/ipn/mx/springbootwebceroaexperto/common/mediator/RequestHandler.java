package com.ipn.mx.springbootwebceroaexperto.common.mediator;

public interface RequestHandler<T extends Request<R>, R> {// En T extends Request<R>, T es una clase especifica (Como CreateProductRequest) que extiende o implementa de la clase Request. R hace referencia al tipo de respuesta (como Void) a la peticion (Request) especifica

    R handle(T request);

    Class<T> getRequestType();
}