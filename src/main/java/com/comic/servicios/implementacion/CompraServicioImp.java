package com.comic.servicios.implementacion;

import com.comic.entidades.Carrito;
import com.comic.entidades.Dto.Compra;
import com.comic.entidades.PedidoCarrito;
import com.comic.entidades.PedidoCompra;
import com.comic.entidades.Usuario;
import com.comic.repositorios.CarritoRepositorio;
import com.comic.repositorios.CompraRepositorio;
import com.comic.servicios.CompraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("CompraServicio")
public class CompraServicioImp implements CompraServicio {

    private CompraRepositorio compraRepositorio;
    private CarritoRepositorio carritoRepositorio;

    @Autowired
    public CompraServicioImp(CompraRepositorio compraRepositorio , CarritoRepositorio carritoRepositorio) {
        this.compraRepositorio = compraRepositorio;
        this.carritoRepositorio= carritoRepositorio;
    }


    @Override
    public List<Compra> listarlasCompras() {

        return compraRepositorio.buscarTodasLasCompras();
    }

    @Override
    public void guardarCompra(Usuario usuario){
        Compra compraNueva = new Compra();
        Carrito nuevoCarrito = usuario.getCarrito();

        //
        List<PedidoCompra> pedidoCompras = new ArrayList<>();

        for (PedidoCarrito pedidoA : nuevoCarrito.getPedidos()){
            pedidoCompras.add(new PedidoCompra(null, pedidoA.getFigura(), pedidoA.getCantidad()));
        }

        compraNueva.setListaDePedidosAcomprar(pedidoCompras);
        compraNueva.setCantidad(nuevoCarrito.getPedidos().size());
        compraNueva.setPrecioTotal(nuevoCarrito.getTotal());
        compraNueva.setUsuario(usuario);

        compraRepositorio.guardar(compraNueva);
    }



}
