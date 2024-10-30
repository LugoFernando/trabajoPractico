package com.comic.servicios.implementacion;

import com.comic.entidades.CarritoItem;
import com.comic.repositorios.CarritoItemRepositorio;
import com.comic.servicios.CarritoItemServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarritoItemServicioImp implements CarritoItemServicio {

    private CarritoItemRepositorio carritoItemRepositorio;

    @Autowired
    public CarritoItemServicioImp(CarritoItemRepositorio carritoItemRepositorio) {
        this.carritoItemRepositorio = carritoItemRepositorio;
    }

    @Override
    public CarritoItem buscarItemPorId(long id){
        return carritoItemRepositorio.buscarItemCarrito(id);
    }

    @Override
    public List<CarritoItem> traerListaDeCarritoItem(){
        return carritoItemRepositorio.traerListaDeCarritoItem();
    }






}
