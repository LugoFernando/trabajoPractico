package com.comic.controladorTest;

import com.comic.controlador.FiguraControlador;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

public class controladorFiguraTest {

    private Usuario usuarioMock;
    private FiguraControlador figuraControlador;
    private FiguraServicio servicioFiguraMock;
    private ServicioLogin servicioLoginMock;

    @BeforeEach
    public void init() {
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("selgadis25.com");
        when(usuarioMock.getPassword()).thenReturn("123456");
        servicioLoginMock = mock(ServicioLogin.class);
        servicioFiguraMock = mock(FiguraServicio.class);
        figuraControlador = new FiguraControlador(servicioFiguraMock, servicioLoginMock);
    }


    @Test
    public void queSeMuestreLaVistaFigurasConListaDeFigurasDelUsuario() {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpSession sessionMock = mock(HttpSession.class);
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);

        List<Figura> figurasMock = List.of(new Figura(), new Figura());

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(servicioFiguraMock.traerListaDeFIgurasPorUsuario(usuarioMock.getId())).thenReturn(figurasMock);

        ModelAndView modelAndView = figuraControlador.listarFiguras(requestMock);

        assertThat(modelAndView.getViewName(), equalTo("figuras"));
        assertThat(modelAndView.getModel().get("figuras"), equalTo(figurasMock));
        verify(requestMock).getSession();
        verify(sessionMock).getAttribute("usuario");
        verify(servicioFiguraMock).traerListaDeFIgurasPorUsuario(usuarioMock.getId());
    }


    @Test
    public void queSeMuestreLaVistaNuevaFiguraConFormularioDeFigura() {
        ModelAndView modelAndView = figuraControlador.nuevaFiguraForm();

        assertThat(modelAndView.getViewName(), equalTo("nuevaFigura"));
        assertThat(modelAndView.getModel().get("figura"), equalTo(new Figura()));
    }

    @Test
    public void queSeGuardeLaFiguraYRedirijaALaVistaLista() throws Exception {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpSession sessionMock = mock(HttpSession.class);
        MultipartFile imagenMock = mock(MultipartFile.class);

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);

        Figura figuraMock = new Figura();
        figuraMock.setNombre("Batman");

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = figuraControlador.guardarFigura(figuraMock, imagenMock, requestMock);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/lista"));
        assertThat(figuraMock.getUsuario(), equalTo(usuarioMock));
        verify(servicioFiguraMock).guardarFigura(figuraMock, imagenMock);
        verify(requestMock).getSession();
        verify(sessionMock).getAttribute("usuario");
    }


    @Test
    public void queSeRedirijaALaVistaListaDespuesDeEliminarFigura() {
        Long idFigura = 1L;

        ModelAndView modelAndView = figuraControlador.eliminarFigura(idFigura);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/lista"));
        verify(servicioFiguraMock).eliminarFigura(idFigura);
    }

    @Test
    public void queSeMuestreLaVistaDetalleFiguraConFiguraEspecifica() {
        Long idFigura = 1L;
        Figura figuraMock = new Figura();
        figuraMock.setId(idFigura);
        when(servicioFiguraMock.obtenerFiguraPorId(idFigura)).thenReturn(figuraMock);

        ModelAndView modelAndView = figuraControlador.detalleFigura(idFigura);

        assertThat(modelAndView.getViewName(), equalTo("detalleFigura"));
        assertThat(modelAndView.getModel().get("figura"), equalTo(figuraMock));
        verify(servicioFiguraMock).obtenerFiguraPorId(idFigura);
    }


    @Test
    public void queSeBusqueUnaFiguraPorIDyDevuelvaUnaFigura(){
        Figura figuraMock = mock(Figura.class);

        when(servicioFiguraMock.obtenerFiguraPorId(anyLong())).thenReturn(figuraMock);

        Figura valorObtenido = figuraControlador.buscarFigura(anyLong());

        assertThat(figuraMock, equalTo(valorObtenido));
    }

    @Test
    public void queSeRedirijaALaVistaConTodasLasFiguras(){
            String valorEsperado = "listaDeProducto";
            String valorObtenido = figuraControlador.listarTodasLasFiguras();
            assertThat(valorEsperado, equalTo(valorObtenido));

    }


    @Test
    public void queNoSeRedirijaALaVistaListaDeProductosEnBaseAUnaBusquedaAlNoEncontrarCoincidencia() {
        Model modelMock = mock(Model.class);
        String valorEsperado = "listaDeProducto";
        List<Figura> figurasMock = mock(List.class);

        when(servicioFiguraMock.listarFigurasActivas()).thenReturn(figurasMock);
        ModelAndView valorObtenido = figuraControlador.irAProductos(modelMock, null);

        verify(modelMock, times(0)).addAttribute(eq("palabraBuscada"), anyString());
        verify(modelMock, times(1)).addAttribute(eq("figuras"), anyList());
        verify(servicioFiguraMock, times(1)).listarFigurasActivas();
        verify(servicioFiguraMock, times(0)).buscarSegunTexto(anyString());
        assertThat(valorEsperado, equalTo(valorObtenido.getViewName()));
    }
    @Test
    public void queSeRedirijaALaVistaListaDeProductosEnBaseAUnaBusquedaAlEncontrarCoincidencia(){
        Model modelMock = mock(Model.class);
        String palabraBuscada = "Batman";
        String valorEsperado = "listaDeProducto";
        List<Figura> figurasMock = mock(List.class);

        when(servicioFiguraMock.buscarSegunTexto(palabraBuscada)).thenReturn(figurasMock);
        ModelAndView valorObtenido = figuraControlador.irAProductos(modelMock,palabraBuscada);

        verify(modelMock).addAttribute(eq("palabraBuscada"), anyString());
        verify(modelMock, times(1)).addAttribute(eq("figuras"), anyList());

        assertThat(valorEsperado, equalTo(valorObtenido.getViewName()));

    }


    @Test
    public void queSeMuestreLaVistaFiguraSiencuentraLaFiguraConElID() {
        // mock
        Figura figuraMock = new Figura();
        figuraMock.setId(1L);
        when(servicioFiguraMock.obtenerFiguraPorId(1L)).thenReturn(figuraMock);

        ModelAndView modelAndView = figuraControlador.vistaActualizarFigura(1L);

        assertThat(modelAndView.getViewName(), equalTo("modificarFigura"));
        assertThat(modelAndView.getModel().get("figura"), equalTo(figuraMock));
        verify(servicioFiguraMock).obtenerFiguraPorId(1L);
    }

    @Test
    public void queMuestreLaListaDeFigurasEnCasoQueNoENcuentreCoincidenciaConID() {

        when(servicioFiguraMock.obtenerFiguraPorId(1L)).thenReturn(null);

        ModelAndView modelAndView = figuraControlador.vistaActualizarFigura(1L);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/lista"));
        verify(servicioFiguraMock).obtenerFiguraPorId(1L);
    }

    @Test
    public void queSeRedirigaALaVistaModificarFiguraEnElCasoQueEncuentreLaId() {
        //moc
        Figura figuraMock = new Figura();
        figuraMock.setId(1L);
        when(servicioFiguraMock.obtenerFiguraPorId(1L)).thenReturn(figuraMock);


        ModelAndView modelAndView = figuraControlador.vistaActualizarFigura(1L);

        assertThat(modelAndView.getViewName(), equalTo("modificarFigura"));
        assertThat(modelAndView.getModel().get("figura"), equalTo(figuraMock));
        verify(servicioFiguraMock).obtenerFiguraPorId(1L);
    }

    @Test
    public void queSeRedirigaALaVistaListaEnElCasoQueNoSeEncuentraLaId() {

        when(servicioFiguraMock.obtenerFiguraPorId(1L)).thenReturn(null);


        ModelAndView modelAndView = figuraControlador.vistaActualizarFigura(1L);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/lista"));
        verify(servicioFiguraMock).obtenerFiguraPorId(1L);

    }

    @Test
    public void queSeVeaLaVistaListaDeProductosDespuesDeActualizarLaFiguraConImagen() throws Exception {
        // moc
        Figura figuraMock = new Figura();
        figuraMock.setId(1L);
        figuraMock.setNombre("Batman");

        // simulacion de la img
        MockMultipartFile archivoImagenMock = new MockMultipartFile("archivoImagen", "imagen.jpg", "image/jpeg", "imagen simulada".getBytes());

        // Ejecutar el método del controlador
        ModelAndView modelAndView = figuraControlador.actualizarFigura(figuraMock, archivoImagenMock);

        assertThat(modelAndView.getViewName(), equalTo("redirect:/lista"));
        verify(servicioFiguraMock).actualizar(figuraMock, archivoImagenMock);
    }



}
