package com.comic.controladorTest;

import com.comic.controlador.FiguraControlador;
import com.comic.controlador.dto.DatosLogin;
import com.comic.entidades.Usuario;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class controladorFiguraTest {

    private Usuario usuarioMock;
    private DatosLogin datosLoginMock;
    private HttpSession sessionMock;//hago referencia a la sesion de un usuario donde tengo iformacion
    private HttpServletRequest requestMock;//hace referencia a las solicitudes que voy a manejar
    private FiguraControlador figuraControlador;
    private FiguraServicio servicioFiguraMock;
    private ServicioLogin servicioLoginMock;

    @BeforeEach
    public void init() {
        datosLoginMock = new DatosLogin("selgadis25.com", "123456");
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("selgadis25.com");
        when(usuarioMock.getPassword()).thenReturn("123456");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);
        servicioFiguraMock = mock(FiguraServicio.class);
        figuraControlador = new FiguraControlador(servicioFiguraMock, servicioLoginMock);
    }

    //Crear, Guardar y Mostrar
    @Test
    public void queSeCreeUnaFiguraNuevaYSeAgregueALaLista(){

    }

    //Buscar y Eliminar
    @Test
    public void buscarUnaFiguraPorIdParaLuegoEliminarla(){

    }

    //Buscar por texto
    @Test
    public void buscarUnaFiguraPorSueDescripcionYNombre(){

    }


}
