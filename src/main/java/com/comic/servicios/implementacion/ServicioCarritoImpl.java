package com.comic.servicios.implementacion;

import com.comic.entidades.Producto;
import com.comic.repositorios.RepositorioCarrito;
import com.comic.servicios.ServicioCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("servicioCarrito")
public class ServicioCarritoImpl implements ServicioCarrito {

    private final RepositorioCarrito repositorioCarrito;

    @Autowired
    public ServicioCarritoImpl(RepositorioCarrito repositorioCarrito){
        this.repositorioCarrito = repositorioCarrito;
    }


    @Override
    public void agregarAlCarrito(Integer productoId) {

        Producto productoBuscado = repositorioCarrito.buscarProductoPorId(productoId);
    }
}
