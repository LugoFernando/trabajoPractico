package com.comic.repositorios;

import com.comic.entidades.Producto;

public interface RepositorioCarrito {

    Producto buscarProductoPorId(Integer productoId);
}
