package com.comic.servicios.implementacion;

import com.comic.entidades.Compra;
import com.comic.repositorios.CompraRepositorio;
import com.comic.servicios.CompraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CompraServicio")
public class CompraServicioImp implements CompraServicio {

    private CompraRepositorio compraRepositorio;

    @Autowired
    public CompraServicioImp(CompraRepositorio compraRepositorio) {
        this.compraRepositorio = compraRepositorio;
    }


    @Override
    public List<Compra> listarlasCompras() {

        return compraRepositorio.buscarTodasLasCompras();
    }


}
