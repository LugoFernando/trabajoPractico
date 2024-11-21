package com.comic.serviciosTest;

import com.comic.entidades.Carrito;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.repositorios.CarritoRepositorio;
import com.comic.servicios.CarritoServicio;
import com.comic.servicios.implementacion.CarritoServicioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class CarritoServicioTest {

    private CarritoServicio carritoServicio;
    private CarritoRepositorio carritoRepositorio;

    @BeforeEach
    public void init() {
        this.carritoRepositorio = mock(CarritoRepositorio.class);
        this.carritoServicio = new CarritoServicioImpl(carritoRepositorio);
    }

    @Test
    public void queSeGuardeUnCarritoEnElRepositorio() {

        Figura figuraMock = new Figura();
        figuraMock.setId(1L);
        figuraMock.setNombre("SpiderMan");
        figuraMock.setPrecio(150.0);

        Carrito carritoMock = new Carrito();
        carritoMock.agregarFigura(figuraMock);

        carritoServicio.guardarCarrito(carritoMock);

        verify(carritoRepositorio).guardar(carritoMock);
        assertThat(carritoMock.getPedidos().size(), equalTo(1));
        assertThat(carritoMock.getTotal(), equalTo(150.0));
    }

    @Test
    public void queSeObtengaUnCarritoPorUsuario() {

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setEmail("test@email.com");

        Figura figuraMock = new Figura();
        figuraMock.setId(1L);
        figuraMock.setNombre("IronMan");
        figuraMock.setPrecio(200.0);

        Carrito carritoMock = new Carrito();
        carritoMock.agregarFigura(figuraMock);

        when(carritoRepositorio.obtenerCarritoPorUsuario(usuarioMock)).thenReturn(carritoMock);

        Carrito carritoObtenido = carritoServicio.obtenerCarritoPorUsuario(usuarioMock);

        assertThat(carritoObtenido, equalTo(carritoMock));
        assertThat(carritoObtenido.getPedidos().size(), equalTo(1));
        assertThat(carritoObtenido.getTotal(), equalTo(200.0));
        verify(carritoRepositorio).obtenerCarritoPorUsuario(usuarioMock);
    }

    @Test
    public void queSeModifiqueElCarrito() {

        Carrito carritoMock = new Carrito();
        carritoMock.setId(1L);

        Figura figuraMock = new Figura();
        figuraMock.setId(1L);
        figuraMock.setNombre("SpiderMan");
        figuraMock.setPrecio(150.0);

        carritoMock.agregarFigura(figuraMock);

        carritoServicio.modificarCarrito(carritoMock);

        verify(carritoRepositorio).modificarCarrito(carritoMock);
    }
}


