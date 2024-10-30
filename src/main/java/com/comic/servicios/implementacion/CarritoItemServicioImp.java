package com.comic.servicios.implementacion;

import com.comic.entidades.CarritoItem;
import com.comic.entidades.Usuario;
import com.comic.repositorios.CarritoItemRepositorio;
import com.comic.repositorios.RepositorioUsuario;
import com.comic.servicios.CarritoItemServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class CarritoItemServicioImp implements CarritoItemServicio {

    private CarritoItemRepositorio carritoItemRepositorio;
    private RepositorioUsuario usuarioRepositorio;
    @Autowired
    public CarritoItemServicioImp(CarritoItemRepositorio carritoItemRepositorio,RepositorioUsuario usuarioRepositorio) {
        this.carritoItemRepositorio = carritoItemRepositorio;
        this.usuarioRepositorio=usuarioRepositorio;
    }

    @Override
    public CarritoItem buscarItemPorId(long id){
        return carritoItemRepositorio.buscarItemCarrito(id);
    }

    @Override
    public List<CarritoItem> traerListaDeCarritoItem(){
        return carritoItemRepositorio.traerListaDeCarritoItem();
    }
    @Override
    public Boolean buscarEnCarritoUsuarioLaFIuraConElId(Long idUsuario, Long idFigura){
        Boolean resultado=false;
        Usuario usuarioEncontrado=usuarioRepositorio.buscarUsuario(idUsuario);
        if(usuarioEncontrado.getCarrito()!=null){
            for(CarritoItem carritoItem :usuarioEncontrado.getCarrito()){
                carritoItem.getFigura().getId().equals(idFigura);
                resultado=true;
                break;
            }
        }
        return resultado;
    }

    @Override
    public void guardarCarrito(CarritoItem carrito) {
        carritoItemRepositorio.guardarITemCarrito(carrito);
    }

    @Override
    public void traerELCarritoItemDelUsuarioYmodificarCantidad(Long idUsuario, Long idFigura, Integer cantidad){
        Integer Cantidad;
        Usuario usuarioEncontrado=usuarioRepositorio.buscarUsuario(idUsuario);
        if(usuarioEncontrado.getCarrito()!=null){
            for(CarritoItem carritoItem :usuarioEncontrado.getCarrito()){
                if(carritoItem.getFigura().getId().equals(idFigura)){
                   cantidad=carritoItem.getCantidad()+cantidad;
                   carritoItem.setCantidad(cantidad);
                   carritoItemRepositorio.modificar(carritoItem);
                }

                break;
            }
        }

    }








}
