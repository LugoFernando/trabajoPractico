package com.comic.servicios;

import com.comic.entidades.Figura;

import java.util.List;

public interface FiguraServicio {

    List<Figura> listarFiguras();

    void guardarFigura(Figura figura);

    Figura obtenerFiguraPorId(Long id);

    void eliminarFigura(Long id);
}
