package com.comic.servicios;

import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;

import java.util.List;

public interface CompraServicio {


    List<Figura> obtenerFigurasRelacionadas(Usuario usuario);
}
