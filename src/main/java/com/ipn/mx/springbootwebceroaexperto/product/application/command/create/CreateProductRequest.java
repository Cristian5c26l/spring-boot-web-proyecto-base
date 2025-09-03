package com.ipn.mx.springbootwebceroaexperto.product.application.command.create;

import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.Request;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProductRequest implements Request<CreateProductResponse> {// Para esta peticion de crear producto, la respuesta es vacia (Void) o no se tiene respuesta
    //Datos que se esperan al crear un producto

    private String name;
    private String description;
    private Double price;
    private MultipartFile file;
}
