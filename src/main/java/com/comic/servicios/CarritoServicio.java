package com.comic.servicios;

import com.comic.entidades.Carrito;
import com.comic.entidades.Usuario;

public interface CarritoServicio {
    void guardarCarrito(Carrito carrito);

    Carrito obtenerCarritoPorUsuario(Usuario usuario);


    void  modificarCarrito(Carrito carrito);

    void eliminarUnCarrito(Usuario usuario);
}
