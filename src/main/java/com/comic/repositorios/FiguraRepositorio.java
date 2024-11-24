package com.comic.repositorios;

import com.comic.entidades.Figura;

import javax.transaction.Transactional;
import java.util.List;

public interface FiguraRepositorio {


    List<Figura> buscarTodo();

    List<Figura> buscarFiguraPorIDUsurio(Long id);

    void guardar(Figura figura);

    Figura buscarPorId(Long id);

    void BorrarPorId(Long id);

    void actualizarFigura(Figura figura);

    List<Figura> darUnaListaBuscandoUnaPalabra(String texto);

    @Transactional
    void actualizar(Figura figura);
}
