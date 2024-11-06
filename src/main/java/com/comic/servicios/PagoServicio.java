package com.comic.servicios;

import com.comic.entidades.Carrito;
import com.comic.entidades.Usuario;

import java.io.IOException;

public interface PagoServicio {

    String pagarOrdenMp(Double precioTotal) throws IOException;
}
