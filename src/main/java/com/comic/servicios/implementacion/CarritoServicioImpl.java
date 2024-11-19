package com.comic.servicios.implementacion;

import com.comic.entidades.Carrito;
import com.comic.entidades.Usuario;
import com.comic.repositorios.CarritoRepositorio;
import com.comic.servicios.CarritoServicio;
import com.comic.servicios.EmailServicio;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class CarritoServicioImpl implements CarritoServicio {

    private CarritoRepositorio carritoRepositorio;

    @Autowired
    public CarritoServicioImpl(CarritoRepositorio carritoRepositorio) {
        this.carritoRepositorio = carritoRepositorio;
    }

    @Override//ver
    public void guardarCarrito(Carrito carrito) {
        carritoRepositorio.guardar(carrito);
    }

    @Override//ver
    public Carrito obtenerCarritoPorUsuario(Usuario usuario) {
        return carritoRepositorio.obtenerCarritoPorUsuario(usuario);
    }

    @Override//ver
    public void  modificarCarrito (Carrito carrito){
        carritoRepositorio.modificarCarrito(carrito);
    }


    }

