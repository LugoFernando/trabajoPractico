package com.comic.servicios;

import com.comic.entidades.Dto.Compra;
import com.comic.entidades.Usuario;

import java.util.List;
public interface CompraServicio {

    List<Compra> listarlasCompras();


    Compra guardarCompra(Usuario usuario);

    Compra buscarCompraPorId(Long id);
}

