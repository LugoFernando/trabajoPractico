package com.comic.serviciosTest;

import com.comic.entidades.*;
import com.comic.entidades.Dto.Compra;
import com.comic.repositorios.CarritoRepositorio;
import com.comic.repositorios.CompraRepositorio;
import com.comic.servicios.implementacion.CompraServicioImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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


    @Test
    public void queSeObtengaUnaListaDeTodasLasCompras() {
        Compra compraMock1 = new Compra();
        compraMock1.setId(1L);

        Compra compraMock2 = new Compra();
        compraMock2.setId(2L);

        List<Compra> comprasMock = Arrays.asList(compraMock1, compraMock2);

        when(compraRepositorio.buscarTodasLasCompras()).thenReturn(comprasMock);

        List<Compra> compras = compraService.listarlasCompras();

        assertThat(compras, equalTo(comprasMock));
        assertThat(compras.size(), equalTo(2));
        verify(compraRepositorio).buscarTodasLasCompras();
    }

    @Test
    public void queGuardeUnaCompraBasadaEnElCarritoDeUsuario() {

        Usuario usuarioMock = new Usuario();
        Carrito carritoMock = new Carrito();


        Figura figuraMock = new Figura();
        figuraMock.setId(1L);
        figuraMock.setPrecio(100.0);

        PedidoCarrito pedidoMock = new PedidoCarrito(figuraMock, 2);
        carritoMock.agregarFigura(figuraMock);

        usuarioMock.setCarrito(carritoMock);

        compraService.guardarCompra(usuarioMock);


        ArgumentCaptor<Compra> compraCaptor = ArgumentCaptor.forClass(Compra.class);
        verify(compraRepositorio).guardar(compraCaptor.capture());

        Compra compraGuardada = compraCaptor.getValue();

        assertNotNull(compraGuardada);
        assertThat(compraGuardada.getUsuario(), equalTo(usuarioMock));
        assertThat(compraGuardada.getCantidad(), equalTo((double) usuarioMock.getCarrito().getPedidos().size()));
        assertThat(compraGuardada.getPrecioTotal(), equalTo(usuarioMock.getCarrito().getTotal()));
        assertThat(compraGuardada.getListaDePedidosAcomprar().size(), equalTo(usuarioMock.getCarrito().getPedidos().size()));

        PedidoCompra pedidoCompra = compraGuardada.getListaDePedidosAcomprar().get(0);
        PedidoCarrito pedidoCarrito = usuarioMock.getCarrito().getPedidos().get(0);

        assertThat(pedidoCompra.getFigura(), equalTo(pedidoCarrito.getFigura()));
        assertThat(pedidoCompra.getCantidad(), equalTo(pedidoCarrito.getCantidad()));
    }

    @Test
    public void queSeObtengaUnaCompraPorId() {

        Long idCompra = 1L;
        Figura figuraMock = new Figura();
        figuraMock.setId(1L);
        figuraMock.setPrecio(200.0);
        figuraMock.setNombre("Figura Mock");

        PedidoCompra pedidoMock = new PedidoCompra(null, figuraMock, 3);

        Compra compraMock = new Compra();
        compraMock.setId(idCompra);
        compraMock.setCantidad(1);
        compraMock.setPrecioTotal(600.0);
        compraMock.setListaDePedidosAcomprar(List.of(pedidoMock));

        when(compraRepositorio.buscarCompraPorId(idCompra)).thenReturn(compraMock);

        Compra compraObtenida = compraService.buscarCompraPorId(idCompra);

        assertNotNull(compraObtenida);
        assertThat(compraObtenida.getId(), equalTo(idCompra));
        assertThat(compraObtenida.getCantidad(), equalTo(1.0));
        assertThat(compraObtenida.getPrecioTotal(), equalTo(600.0));
        assertThat(compraObtenida.getListaDePedidosAcomprar().size(), equalTo(1));

        PedidoCompra pedidoObtenido = compraObtenida.getListaDePedidosAcomprar().get(0);
        assertThat(pedidoObtenido.getFigura(), equalTo(figuraMock));
        assertThat(pedidoObtenido.getCantidad(), equalTo(3));
        verify(compraRepositorio).buscarCompraPorId(idCompra);
    }

}
