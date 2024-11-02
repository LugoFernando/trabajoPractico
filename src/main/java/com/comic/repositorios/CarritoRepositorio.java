package com.comic.repositorios;

import com.comic.entidades.Carrito;
import com.comic.entidades.Usuario;

import javax.transaction.Transactional;

public interface CarritoRepositorio {
    @Transactional
    void guardar(Carrito carrito);

    @Transactional
    Carrito obtenerCarritoPorUsuario(Usuario usuario);

    @Transactional
    void modificarCarrito(Carrito carrito);
}
