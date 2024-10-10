package com.comic.repositorios;

import com.comic.entidades.Compra;

import javax.transaction.Transactional;
import java.util.List;

public interface CompraRepositorio {

    @Transactional
    List<Compra> buscarTodasLasCompras();
}
