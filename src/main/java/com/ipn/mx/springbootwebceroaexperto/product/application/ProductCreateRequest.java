package com.ipn.mx.springbootwebceroaexperto.product.application;

import com.ipn.mx.springbootwebceroaexperto.common.mediator.Request;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductCreateRequest implements Request<Void> {// Para esta peticion de crear producto, la respuesta es vacia (Void) o no se tiene respuesta
    //Datos que se esperan al crear un producto
    private Long id;// en un ambiente real, el id es autogenerado por la base de datos
    private String name;
    private String description;
    private Double price;
    private String image;
}
