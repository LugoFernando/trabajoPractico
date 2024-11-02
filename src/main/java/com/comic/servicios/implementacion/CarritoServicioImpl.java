package com.comic.servicios.implementacion;

import com.comic.entidades.Carrito;
import com.comic.entidades.Usuario;
import com.comic.repositorios.CarritoRepositorio;
import com.comic.servicios.CarritoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoServicioImpl implements CarritoServicio {

    private CarritoRepositorio carritoRepositorio;

    @Autowired
    public CarritoServicioImpl(CarritoRepositorio carritoRepositorio) {
        this.carritoRepositorio = carritoRepositorio;
    }

    @Override
    public void guardarCarrito(Carrito carrito) {
        carritoRepositorio.guardar(carrito);
    }

    @Override
    public Carrito obtenerCarritoPorUsuario(Usuario usuario) {
        return carritoRepositorio.obtenerCarritoPorUsuario(usuario);
    }

    @Override
    public void  modificarCarrito (Carrito carrito){
        carritoRepositorio.modificarCarrito(carrito);
    }
}
