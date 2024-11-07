package com.comic.serviciosTest;

import com.comic.entidades.Carrito;
import com.comic.entidades.Dto.Compra;
import com.comic.entidades.Figura;
import com.comic.entidades.Pedido;
import com.comic.entidades.Usuario;
import com.comic.repositorios.CarritoRepositorio;
import com.comic.repositorios.CompraRepositorio;
import com.comic.servicios.CompraServicio;
import com.comic.servicios.implementacion.CompraServicioImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PipedWriter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

public class CompraServicioTEst {


    private CompraRepositorio compraRepositorio;
    private CompraServicioImp compraService;
    private CarritoRepositorio carritoRepositorio;

    @BeforeEach
    public void init() {
        compraRepositorio = mock(CompraRepositorio.class);
        carritoRepositorio = mock(CarritoRepositorio.class);

        this.compraService= new CompraServicioImp(compraRepositorio, carritoRepositorio);

    }

//    @Test
//    public void queSeObtengaUnaListaConTodosLasCompras(){
//        List<Compra> comprasMock = new ArrayList<>();
//        Figura figuraMock = new Figura("superman");
//        figuraMock.setPrecio(100.0);
//        Pedido pedido = new Pedido(1L,figuraMock,1);
//        List<Pedido>listaDePedidos=new ArrayList<>();
//        listaDePedidos.add(pedido);
//        Usuario usuario =new Usuario();
//        usuario.setId(1L);
//        usuario.setEmail("selgadis@gmail.com");
//        usuario.setPassword("selga");
//        Compra compra1=new Compra();
//        compra1.setId(1L);
//        compra1.setCantidad(1);
//        compra1.setPrecioTotal(100.0);
//        compra1.setUsuario(usuario);
//        compra1.setListaDePedidosAcomprar(listaDePedidos);
//
//
//
//        List<Compra> figuras = this.compraServicio.listarlasCompras();
//
//        assertThat(figuras, equalTo(comprasMock));
//        verify(compraRepositorio).buscarTodasLasCompras();
//    }

    @Test
    public void testGuardarCompra() {
        // Crear figura mock y pedido
        Figura figuraMock = new Figura("superman");
        figuraMock.setPrecio(100.0);

        Pedido pedido = new Pedido(1L, figuraMock, 1);
        List<Pedido> listaDePedidos = new ArrayList<>();
        listaDePedidos.add(pedido);

        // Crear usuario con carrito
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("selgadis@gmail.com");
        usuario.setPassword("selga");

        Carrito carrito = new Carrito();
        carrito.setPedidos(listaDePedidos);
        carrito.setTotal(100.0); // Total del carrito
        usuario.setCarrito(carrito);

        // Acción: guardar la compra
        compraService.guardarCompra(usuario);

        // Verificar que compraRepositorio.guardar fue llamado con la compra correcta
        verify(compraRepositorio, times(1)).guardar(argThat(compra ->
                compra.getUsuario().equals(usuario) &&
                        compra.getCantidad() == 1 &&
                        compra.getPrecioTotal() == 100.0 &&
                        compra.getListaDePedidosAcomprar().equals(listaDePedidos)
        ));
    }


    @Test
    public void testListarLasCompras() {
        // Crear figura mock y pedido
        Figura figuraMock = new Figura("superman");
        figuraMock.setPrecio(100.0);

        Pedido pedido = new Pedido(1L, figuraMock, 1);
        List<Pedido> listaDePedidos = new ArrayList<>();
        listaDePedidos.add(pedido);

        // Crear usuario con carrito
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("selgadis@gmail.com");
        usuario.setPassword("selga");

        Carrito carrito = new Carrito();
        carrito.setPedidos(listaDePedidos);
        carrito.setTotal(100.0);
        usuario.setCarrito(carrito);

        // Crear compra
        Compra compraEsperada = new Compra();
        compraEsperada.setId(1L);
        compraEsperada.setCantidad(1);
        compraEsperada.setPrecioTotal(100.0);
        compraEsperada.setUsuario(usuario);
        compraEsperada.setListaDePedidosAcomprar(listaDePedidos);

        // Simular la llamada al repositorio
        when(compraRepositorio.buscarTodasLasCompras()).thenReturn(List.of(compraEsperada));

        // Acción
        List<Compra> resultado = compraService.listarlasCompras();

        // Verificación
        assertThat(resultado, hasSize(1)); // Debe devolver una sola compra
        Compra compraObtenida = resultado.get(0);

        // Validar la compra obtenida
        assertThat(compraObtenida.getUsuario(), equalTo(usuario));
        assertThat(compraObtenida.getListaDePedidosAcomprar(), equalTo(listaDePedidos));
        assertThat(compraObtenida.getPrecioTotal(), equalTo(100.0));
        assertThat(compraObtenida.getCantidad(), equalTo(1.0));

        // Verificar que el repositorio fue llamado
        verify(compraRepositorio, times(1)).buscarTodasLasCompras();
    }



}
