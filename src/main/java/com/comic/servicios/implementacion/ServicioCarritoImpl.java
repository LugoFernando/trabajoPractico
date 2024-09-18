package com.comic.servicios.implementacion;

import com.comic.entidades.Producto;
import com.comic.repositorios.RepositorioCarrito;
import com.comic.servicios.ServicioCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service("servicioCarrito")
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito {

    private RepositorioCarrito repositorioCarrito;

    @Autowired
    public ServicioCarritoImpl(RepositorioCarrito repositorioCarrito){
        this.repositorioCarrito = repositorioCarrito;
    }

    @Override
    public void agregarAlCarrito(Integer productoId) {

        Producto productoBuscado = repositorioCarrito.buscarProductoPorId(productoId);
    }
}
