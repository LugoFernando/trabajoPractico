package com.comic.servicios;

import com.comic.entidades.CarritoItem;

import java.util.List;

public interface CarritoItemServicio {






    CarritoItem buscarItemPorId(long id);

    List<CarritoItem> traerListaDeCarritoItem();



    Boolean buscarEnCarritoUsuarioLaFIuraConElId(Long idUsuario, Long idFigura);

    void guardarCarrito(CarritoItem carrito);
    void traerELCarritoItemDelUsuarioYmodificarCantidad(Long idUsuario, Long idFigura, Integer cantidad);
}
