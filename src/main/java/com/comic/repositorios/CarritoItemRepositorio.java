package com.comic.repositorios;

import com.comic.entidades.CarritoItem;

import java.util.List;

public interface CarritoItemRepositorio {

    CarritoItem buscarItemCarrito(Long id);
    List<CarritoItem>traerListaDeCarritoItem();

    void guardarITemCarrito(CarritoItem carrito);

    void modificar(CarritoItem carrito);
}
