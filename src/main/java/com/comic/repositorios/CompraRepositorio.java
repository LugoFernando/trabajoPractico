package com.comic.repositorios;

import com.comic.entidades.Compra;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;

import javax.transaction.Transactional;
import java.util.List;

public interface CompraRepositorio {
    @Transactional
    List<Compra> buscarPorUsuario(Usuario usuario);

    @Transactional
    void guardar(Compra compra);

    @Transactional
    Compra buscarPorId(Long id);


    @Transactional
    List<Figura> obtenerFigurasRelacionadas(Usuario usuario);
}
