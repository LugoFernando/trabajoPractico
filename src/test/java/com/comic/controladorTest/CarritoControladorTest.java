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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

//public class CarritoControladorTest {

//    private ControladorCarrito controladorCarrito;
//    private ServicioLogin servicioLoginMock;
//    private FiguraServicio figuraServicioMock;
//    private CompraServicio compraServicioMock;
//    private CarritoServicio carritoServicioMock;
//    private HttpSession sessionMock;
//    private Usuario usuarioMock;
//    private Figura figuraMock;
//
//    @BeforeEach
//    public void init() {
//        servicioLoginMock = mock(ServicioLogin.class);
//        figuraServicioMock = mock(FiguraServicio.class);
//        compraServicioMock = mock(CompraServicio.class);
//        carritoServicioMock = mock(CarritoServicio.class);
//        sessionMock = mock(HttpSession.class);
//        usuarioMock = mock(Usuario.class);
//        figuraMock = mock(Figura.class);
//
//        controladorCarrito = new ControladorCarrito(servicioLoginMock, figuraServicioMock, compraServicioMock , carritoServicioMock );
//    }
//
//
//    @Test
//    public void queSeRedirijaALoginSiUsuarioNoEstaLogueado() {
//        when(sessionMock.getAttribute("usuario")).thenReturn(null);
//
//        ModelAndView modelAndView = controladorCarrito.agregarFigurasAlCarrito(1L, sessionMock);
//
//        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
//    }
//
//    @Test
//    public void queSeAgregueFiguraAlCarritoYRedirijaAVistaVer() {
//        Long idFigura = 1L;
//
//        Usuario usuarioMock = mock(Usuario.class);
//        when(usuarioMock.getEmail()).thenReturn("selgadis25.com");
//        when(usuarioMock.getPassword()).thenReturn("123456");
//        when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioMock);
//
//        Carrito carritoMock = mock(Carrito.class);
//        when(usuarioMock.getCarrito()).thenReturn(carritoMock);
//        when(figuraServicioMock.obtenerFiguraPorId(idFigura)).thenReturn(new Figura());
//
//        HttpSession sessionMock = mock(HttpSession.class);
//        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
//
//        ModelAndView modelAndView = controladorCarrito.agregarFigurasAlCarrito(idFigura, sessionMock);
//
//        assertThat(modelAndView.getViewName(), equalTo("redirect:/ver"));
//
//        verify(servicioLoginMock).modificarUsuario2(any(Usuario.class));
//
//        verify(carritoMock).agregarFigura(any(Figura.class));
//    }
//
//    @Test
//    public void queSeRedirijaALoginSiUsuarioNoEstaLogueadoAlVerCarrito() {
//        when(sessionMock.getAttribute("usuario")).thenReturn(null);
//
//        ModelAndView modelAndView = controladorCarrito.verCarrito(sessionMock);
//
//        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
//    }
//
//
//}


