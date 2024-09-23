package com.comic.servicios;

import com.comic.entidades.Figura;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FiguraServicio {

    List<Figura> listarFiguras();

    void guardarFigura(Figura figura ,MultipartFile imagen);


    Figura obtenerFiguraPorId(Long id);

    void eliminarFigura(Long id);
}