package com.comic.repositorios;

import com.comic.entidades.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCarrito {

    Producto buscarProductoPorId(Integer productoId);
}
