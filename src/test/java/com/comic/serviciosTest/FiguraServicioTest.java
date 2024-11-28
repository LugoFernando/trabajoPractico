package com.comic.serviciosTest;

import com.comic.entidades.Figura;
import com.comic.repositorios.FiguraRepositorio;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.implementacion.FiguraServicioImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToObject;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class FiguraServicioTest {


    private FiguraServicio figuraServicio;
    private FiguraRepositorio figuraRepositorio;

    @BeforeEach
    public void init() {
        this.figuraRepositorio = mock(FiguraRepositorio.class);
        this.figuraServicio = new FiguraServicioImp(figuraRepositorio);
    }

    @Test
    public void queSeObtengaUnaListaConTodosLasFigurasDeLaBaseDeDatos(){
        List<Figura> figurasMock = new ArrayList<>();

        when(this.figuraRepositorio.buscarTodo()).thenReturn(figurasMock);
        List<Figura> figuras = this.figuraServicio.listarFiguras();

        assertThat(figuras, equalTo(figurasMock));
        verify(figuraRepositorio).buscarTodo();
    }

    @Test
    public void queSeObtengaUnaFiguraDeLaBaseDeDatosPorSuId(){
        Figura figuraMock = new Figura();
        figuraMock.setId(1L);

        when(this.figuraRepositorio.buscarPorId(1L)).thenReturn(figuraMock);
        Figura figura = this.figuraServicio.obtenerFiguraPorId(1L);

        assertThat(figura, equalToObject(figuraMock));
        assertThat(figura.getId(), equalTo(figuraMock.getId()) );
        verify(figuraRepositorio).buscarPorId(1L);
    }


    @Test
    public void queSeguardeUnaNuevaFiguraConUnaImagen() throws IOException {

        Figura figuraMock = new Figura();
        figuraMock.setNombre("Ironman");

        byte[] contenidoImagen = "imagen simulada".getBytes();
        MockMultipartFile mockImagen = new MockMultipartFile("imagen", "imagen.jpg", "image/jpeg", contenidoImagen);

        figuraServicio.guardarFigura(figuraMock, mockImagen);

        // convertilo a byte
        byte[] imagenBase64Esperada = Base64.getEncoder().encode(contenidoImagen);

        assertArrayEquals(imagenBase64Esperada, figuraMock.getImagen());
        verify(figuraRepositorio).guardar(figuraMock);
    }

    @Test
    public void queSeObtengaUnaFiguraDeLaBaseDeDatosAtravezDeSuTituloEnElBuscador() {
        List<Figura> figurasMock = new ArrayList<>();
        Figura figuraMock = new Figura();
        figuraMock.setNombre("Batman");
        figurasMock.add(figuraMock);

        String textoBusqueda = "Batman";
        when(this.figuraRepositorio.darUnaListaBuscandoUnaPalabra(textoBusqueda)).thenReturn(figurasMock);

        List<Figura> figuras = this.figuraServicio.buscarSegunTexto(textoBusqueda);

        assertThat(figuras, equalTo(figurasMock));
        verify(figuraRepositorio).darUnaListaBuscandoUnaPalabra(textoBusqueda);
        verify(figuraRepositorio, never()).buscarTodo(); // no se llamó a buscarTodo
    }

    @Test
    public void queSeObtenganTodaLaListaDeFigurasEnElCasoQueNoSeColoqueNingunTextoEnElBuscador()  {
        List<Figura> figurasMock = new ArrayList<>();

        when(this.figuraRepositorio.buscarTodo()).thenReturn(figurasMock);
        List<Figura> figuras = this.figuraServicio.buscarSegunTexto(null);

        // verificación del repo
        assertThat(figuras, equalTo(figurasMock));
        verify(figuraRepositorio).buscarTodo();
        verify(figuraRepositorio, never()).darUnaListaBuscandoUnaPalabra(anyString());
    }

    @Test
    public void QueSeActulizeLosDatosDeUnaFiguraGuardadaConImagen() throws IOException {
        Figura figuraMock = new Figura();
        figuraMock.setNombre("Superman");

        byte[] contenidoImagen = "imagen simulada".getBytes();
        MockMultipartFile mockImagen = new MockMultipartFile("imagen", "imagen.jpg", "image/jpeg", contenidoImagen);

        figuraServicio.actualizar(figuraMock, mockImagen);

        byte[] imagenBase64Esperada = Base64.getEncoder().encode(contenidoImagen);

        assertArrayEquals(imagenBase64Esperada, figuraMock.getImagen());
        verify(figuraRepositorio).actualizarFigura(figuraMock);
    }

    @Test
    public void QueSeActulizeLosDatosDeUnaFiguraGuardadaSinCambiarLaImagen() {

        Figura figuraMock = new Figura();
        figuraMock.setNombre("Superman");

        figuraServicio.actualizar(figuraMock, null);

        assertNull(figuraMock.getImagen());
        verify(figuraRepositorio).actualizarFigura(figuraMock);
    }

    @Test
    public void queSeElimineFiguraYSeMarqueComoInactiva() {

        Long idFigura = 1L;
        Figura figuraMock = new Figura();
        figuraMock.setId(idFigura);
        figuraMock.setActivo(true);

        when(figuraRepositorio.buscarPorId(idFigura)).thenReturn(figuraMock);

        figuraServicio.eliminarFigura(idFigura);

        assertThat(figuraMock.isActivo(), equalTo(false));
        verify(figuraRepositorio).actualizar(figuraMock);

    }
}