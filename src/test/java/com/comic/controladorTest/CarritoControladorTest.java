package com.comic.controladorTest;

import com.comic.controlador.ControladorCarrito;
import com.comic.entidades.Carrito;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.servicios.CarritoServicio;
import com.comic.servicios.CompraServicio;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CarritoControladorTest {

    private ControladorCarrito controladorCarrito;
    private ServicioLogin servicioLoginMock;
    private FiguraServicio figuraServicioMock;
    private CompraServicio compraServicioMock;
    private CarritoServicio carritoServicioMock;
    private HttpSession sessionMock;
    private Usuario usuarioMock;
    private Figura figuraMock;

    @BeforeEach
    public void init() {
        servicioLoginMock = mock(ServicioLogin.class);
        figuraServicioMock = mock(FiguraServicio.class);
        compraServicioMock = mock(CompraServicio.class);
        carritoServicioMock = mock(CarritoServicio.class);
        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
        figuraMock = mock(Figura.class);

        controladorCarrito = new ControladorCarrito(servicioLoginMock, figuraServicioMock, compraServicioMock , carritoServicioMock );
    }


//    @Test
//    public void queMantengaElCarritoExistenteYAgregueFigura() {
//        Carrito carritoMock = mock(Carrito.class);
//        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
//        when(sessionMock.getAttribute("carrito")).thenReturn(carritoMock);
//        when(figuraServicioMock.obtenerFiguraPorId(1L)).thenReturn(figuraMock);
//
//        String resultado = controladorCarrito.agregarFigurasAlCarrito(1L, sessionMock);
//
//        verify(carritoMock).agregarFigura(figuraMock);
//        verify(sessionMock, never()).setAttribute(eq("carrito"), any(Carrito.class)); // no deberia crear un nuevo carro
//        assertThat(resultado, is("redirect:/ver"));
//    }
//
//    @Test
//    public void queRedirijaALoginSiElUsuarioNoEstaLogeadoEnVerCarrito() {
//        when(sessionMock.getAttribute("usuario")).thenReturn(null);
//
//        String resultado = controladorCarrito.verCarrito(sessionMock, null);
//
//        assertThat(resultado, is("redirect:/login"));
//    }

}
