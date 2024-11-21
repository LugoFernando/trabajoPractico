package com.comic.controladorTest;

import com.comic.controlador.ControladorCarrito;
import com.comic.entidades.*;
import com.comic.entidades.Dto.Compra;
import com.comic.servicios.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CarritoControladorTest {

    private ControladorCarrito controladorCarrito;
    private ServicioLogin servicioLoginMock;
    private FiguraServicio figuraServicioMock;
    private CompraServicio compraServicioMock;
    private CarritoServicio carritoServicioMock;
    private EmailServicio emailServicioMock;
    private HttpSession sessionMock;
    private Usuario usuarioMock;
    private Figura figuraMock;
    private Compra compraMock;

    @BeforeEach
    public void init() {
        servicioLoginMock = mock(ServicioLogin.class);
        figuraServicioMock = mock(FiguraServicio.class);
        compraServicioMock = mock(CompraServicio.class);
        carritoServicioMock = mock(CarritoServicio.class);
        emailServicioMock=mock(EmailServicio.class);
        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
        figuraMock = mock(Figura.class);
        compraMock = mock(Compra.class);

        controladorCarrito = new ControladorCarrito(servicioLoginMock, figuraServicioMock, compraServicioMock , carritoServicioMock,emailServicioMock );
    }


    @Test
    public void queSeRedirijaALoginSiUsuarioNoEstaLogueado() {
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorCarrito.agregarFigurasAlCarrito(1L, sessionMock);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void queSeAgregueFiguraAlCarritoYRedirijaAVistaVer() {
        Long idFigura = 1L;

        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("selgadis25.com");
        when(usuarioMock.getPassword()).thenReturn("123456");
        when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioMock);

        Carrito carritoMock = mock(Carrito.class);
        when(usuarioMock.getCarrito()).thenReturn(carritoMock);
        when(figuraServicioMock.obtenerFiguraPorId(idFigura)).thenReturn(new Figura());

        HttpSession sessionMock = mock(HttpSession.class);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorCarrito.agregarFigurasAlCarrito(idFigura, sessionMock);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/ver"));

        verify(servicioLoginMock).modificarUsuario2(any(Usuario.class));

        verify(carritoMock).agregarFigura(any(Figura.class));
    }

    @Test
    public void queSeRedirijaALoginSiUsuarioNoEstaLogueadoAlVerCarrito() {
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorCarrito.verCarrito(sessionMock);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void queSeMuestreElCarritoSiElUsuarioTieneSesionYCarrito() {

        Carrito carritoMock = mock(Carrito.class);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getEmail()).thenReturn("test@ejemplo.com");
        when(usuarioMock.getPassword()).thenReturn("password123");
        when(servicioLoginMock.consultarUsuario("test@ejemplo.com", "password123")).thenReturn(usuarioMock);
        when(usuarioMock.getCarrito()).thenReturn(carritoMock);
        when(carritoMock.getPedidos()).thenReturn(List.of(mock(PedidoCarrito.class)));
        when(carritoMock.getTotal()).thenReturn(500.0);

        ModelAndView result = controladorCarrito.verCarrito(sessionMock);

        assertNotNull(result);
        assertEquals("carrito", result.getViewName());
        assertNotNull(result.getModel().get("pedidos"));
        assertEquals(500.0, result.getModel().get("total"));
        verify(servicioLoginMock).consultarUsuario("test@ejemplo.com", "password123");
    }

    @Test
    public void queSeCreeUnCarritoNuevoSiElUsuarioNoTieneCarrito() {

        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getEmail()).thenReturn("test@ejemplo.com");
        when(usuarioMock.getPassword()).thenReturn("password123");
        when(servicioLoginMock.consultarUsuario("test@ejemplo.com", "password123")).thenReturn(usuarioMock);
        when(usuarioMock.getCarrito()).thenReturn(null);

        // Actuar
        ModelAndView result = controladorCarrito.verCarrito(sessionMock);

        // Verificar
        assertNotNull(result);
        assertEquals("carrito", result.getViewName());
        assertNotNull(result.getModel().get("pedidos"));
        assertEquals(0.0, result.getModel().get("total"));
        verify(servicioLoginMock).consultarUsuario("test@ejemplo.com", "password123");
    }

    @Test
    public void queSeElimineLaFiguraDelCarritoYRecargeLaPagina() {

        Carrito carritoMock = mock(Carrito.class);
        Figura figuraMock = mock(Figura.class);
        Usuario usuarioMock = mock(Usuario.class);

        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getEmail()).thenReturn("test@ejemplo.com");
        when(usuarioMock.getPassword()).thenReturn("password123");
        when(servicioLoginMock.consultarUsuario("test@ejemplo.com", "password123")).thenReturn(usuarioMock);
        when(figuraServicioMock.obtenerFiguraPorId(1L)).thenReturn(figuraMock);
        when(usuarioMock.getCarrito()).thenReturn(carritoMock);

        ModelAndView result = controladorCarrito.eliminarFiguraDelCarrito(1L, sessionMock);

        verify(carritoMock).eliminarFigura(figuraMock);
        verify(servicioLoginMock).modificarUsuario2(usuarioMock);
        assertNotNull(result);
        assertEquals("redirect:/ver", result.getViewName());
        verify(sessionMock).setAttribute("carrito", carritoMock);
    }

    @Test
    public void queSeVacieElCarritoYRecargeLaPagina() {
        Carrito carritoMock = mock(Carrito.class);
        Usuario usuarioMock = mock(Usuario.class);

        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getEmail()).thenReturn("test@ejemplo.com");
        when(usuarioMock.getPassword()).thenReturn("password123");
        when(servicioLoginMock.consultarUsuario("test@ejemplo.com", "password123")).thenReturn(usuarioMock);
        when(usuarioMock.getCarrito()).thenReturn(carritoMock);

        ModelAndView result = controladorCarrito.vaciarCarrito(sessionMock);

        verify(carritoMock).vaciarCarrito();
        verify(servicioLoginMock).modificarUsuario2(usuarioMock);
        assertNotNull(result);
        assertEquals("redirect:/ver", result.getViewName());
        verify(sessionMock).setAttribute("carrito", carritoMock);
    }

    @Test
    public void queSeProceseLaCompraYSeRedirijaAHome() throws IOException {

        Carrito carritoMock = mock(Carrito.class);
        Usuario usuarioMock = mock(Usuario.class);
        Usuario usuarioBaseDatosMock = mock(Usuario.class);

        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getEmail()).thenReturn("test@example.com");
        when(usuarioMock.getPassword()).thenReturn("password123");
        when(servicioLoginMock.consultarUsuario("test@example.com", "password123")).thenReturn(usuarioBaseDatosMock);
        when(usuarioBaseDatosMock.getCarrito()).thenReturn(carritoMock);

        ModelAndView result = controladorCarrito.terminarCompra(sessionMock);

        verify(compraServicioMock).guardarCompra(usuarioBaseDatosMock);
        verify(carritoMock).vaciarCarrito();
        verify(servicioLoginMock).modificarUsuario2(usuarioBaseDatosMock);
        verify(sessionMock).setAttribute("carrito", carritoMock);
        //verify(emailServicioMock).mandarEmail();
        assertNotNull(result);
        assertEquals("redirect:/home", result.getViewName());
    }


    @Test
    public void queSeMuestreLaListaDeComprasDelUsuarioLogeado() {
        // Preparar
        Usuario usuarioMock = mock(Usuario.class);
        List<Compra> comprasMock = new ArrayList<>();
        Compra compraMock1 = mock(Compra.class);
        Compra compraMock2 = mock(Compra.class);

        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getId()).thenReturn(1L);
        when(compraServicioMock.listarlasCompras()).thenReturn(comprasMock);

        when(compraMock1.getUsuario()).thenReturn(usuarioMock);
        when(compraMock2.getUsuario()).thenReturn(usuarioMock);

        comprasMock.add(compraMock1);
        comprasMock.add(compraMock2);

        ModelAndView result = controladorCarrito.irAlaListaDeCompras(sessionMock);

        assertNotNull(result);
        assertEquals("listaDeCompras", result.getViewName());
        List<Compra> comprasDelUsuario = (List<Compra>) result.getModel().get("listaDeComprasDeUsuario");

        assertThat(comprasDelUsuario, hasSize(2));
        assertThat(comprasDelUsuario, containsInAnyOrder(compraMock1, compraMock2));
        verify(compraServicioMock).listarlasCompras();
    }

    @Test
    public void queNoMuestreComprasDeOtrosUsuarios() {
        // Preparar
        Usuario usuarioMock = mock(Usuario.class);
        Usuario otroUsuarioMock = mock(Usuario.class);
        List<Compra> comprasMock = new ArrayList<>();
        Compra compraMock1 = mock(Compra.class);
        Compra compraMock2 = mock(Compra.class);

        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getId()).thenReturn(1L);

        when(compraMock1.getUsuario()).thenReturn(usuarioMock);
        when(compraMock2.getUsuario()).thenReturn(otroUsuarioMock);

        comprasMock.add(compraMock1);
        comprasMock.add(compraMock2);

        when(compraServicioMock.listarlasCompras()).thenReturn(comprasMock);

        ModelAndView result = controladorCarrito.irAlaListaDeCompras(sessionMock);

        List<Compra> comprasDelUsuario = (List<Compra>) result.getModel().get("listaDeComprasDeUsuario");

        assertThat(comprasDelUsuario, hasSize(1));
        assertThat(comprasDelUsuario, contains(compraMock1));
    }

    @Test
    public void queSeMuestreElDetalleDeCompraCuandoDeUnUsuario() {

        Long compraId = 1L;
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getId()).thenReturn(1L);


        when(compraMock.getId()).thenReturn(compraId);
        when(compraMock.getUsuario()).thenReturn(usuarioMock);
        when(compraServicioMock.buscarCompraPorId(compraId)).thenReturn(compraMock);


        List<PedidoCompra> listaDePedidosAcomprar = new ArrayList<>();
        double precioTotal = 200.0;
        when(compraMock.getListaDePedidosAcomprar()).thenReturn(listaDePedidosAcomprar);
        when(compraMock.getPrecioTotal()).thenReturn(precioTotal);

        ModelAndView result = controladorCarrito.verDetalleCompra(compraId, sessionMock);

        assertNotNull(result);
        assertEquals("detalleCompra", result.getViewName());
        assertEquals(compraMock, result.getModel().get("compra"));
        assertEquals(listaDePedidosAcomprar, result.getModel().get("pedidoCarritos"));
        assertEquals(precioTotal, result.getModel().get("precioTotal"));
    }



}


