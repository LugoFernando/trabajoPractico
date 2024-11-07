package com.comic.servicios;

import com.comic.entidades.Dto.Compra;
import com.comic.entidades.Usuario;

import java.util.List;

public interface CompraServicio {

    List<Compra> listarlasCompras();


    void guardarCompra(Usuario usuario);
}
