package com.comic.controladorTest;

import com.comic.controlador.ControladorCarrito;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.servicios.CarritoServicio;
import com.comic.servicios.CompraServicio;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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


}
