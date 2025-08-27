package com.ipn.mx.springbootwebceroaexperto.common.exceptions;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorMessage {
    private String message;// mensaje de error
    private String exception;// nombre de excepcion ocurrida
    private String path;// url donde se originó la excepcion
    private Map<String, String> errors;// Clave: nombre de campo que ha fallado al hacer la validacion de un DTO. Valor: Mensaje por defecto relativo al error, el cual es modificable desde el mismo dto (como en description de ProductDto, que se menciona que la descripcion debe tener un maximo de 255 caracteres)

    public ErrorMessage(String message, String exception, String path) {// pensado para ser una respuesta para excepciones como ProductNotFoundException ocurridas al hacer peticion GET a /api/v1/products/:id que es atentida en GetProductByIdHandler donde se lanza dicha excepcion, la cual, es manejada por la clase ApiExceptionHandler gracias a que tiene la anotacion @RestControllerAdvice y gracias a que internamente tiene @ExceptionHandler(ProductNotFoundException.class)
        this.message = message;
        this.exception = exception;
        this.path = path;
        this.errors = new HashMap<>();
    }

    public ErrorMessage(String message, String exception, String path, Map<String, String> errors) {// // pensado para ser una respuesta para excepciones como MethodArgumentNotValidException ocurridas al hacer peticion POST a /api/v1/products que es atentida al instante en ProductDto donde los validadores como @NotBlank de spring boot validation, lanzan dicha excepcion, la cual, es manejada por la clase ApiExceptionHandler gracias a que tiene la anotacion @RestControllerAdvice y gracias a que internamente tiene @ExceptionHandler(MethodArgumentNotValidException.class)
        this.message = message;
        this.exception = exception;
        this.path = path;
        this.errors = errors;
    }
}
