package com.comic.controlador;

import com.comic.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CarritoItemControlador {

    @Autowired
    private ServicioLogin servicioLogin;
    private FiguraServicio figuraServicio;
    private CompraServicio compraServicio;
    private UsuarioServicio usuarioServicio;
    private CarritoItemServicio carritoItemServicio;

    @Autowired
    public CarritoItemControlador(ServicioLogin servicioLogin , FiguraServicio figuraServicio , CompraServicio compraServicio, UsuarioServicio usuarioServicio , CarritoItemServicio carritoItemServicio) {
        this.servicioLogin = servicioLogin;
        this.figuraServicio=figuraServicio;
        this.compraServicio=compraServicio;
        this.usuarioServicio = usuarioServicio;
        this.carritoItemServicio = carritoItemServicio;
    }


}
