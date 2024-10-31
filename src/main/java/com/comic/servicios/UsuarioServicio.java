package com.comic.servicios;

import com.comic.entidades.Figura;


public interface UsuarioServicio {
    
  void agregarALCarrito (Long idFigura, Integer cantidad, Long id);
}
