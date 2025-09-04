package com.ipn.mx.springbootwebceroaexperto.product.application.command.update;

import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.Request;
import com.ipn.mx.springbootwebceroaexperto.review.domain.Review;
import lombok.Data;

@Data
public class UpdateProductRequest implements Request<Void> {// Para esta peticion de crear producto, la respuesta es vacia (Void) o no se tiene respuesta
    //Datos que se esperan al crear un producto
    private Long id;// en un ambiente real, el id es autogenerado por la base de datos
    private String name;
    private String description;
    private Double price;
    private String provider;
    private Review review;
    private Long categoryId;
}
