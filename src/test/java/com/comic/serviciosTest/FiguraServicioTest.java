package com.comic.serviciosTest;

import com.comic.entidades.Figura;
import com.comic.repositorios.FiguraRepositorio;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.implementacion.FiguraServicioImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToObject;
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
    public void queSeObtengaUnaListaConTodosLasFiguras(){
        List<Figura> figurasMock = new ArrayList<>();

        when(this.figuraRepositorio.buscarTodo()).thenReturn(figurasMock);
        List<Figura> figuras = this.figuraServicio.listarFiguras();

        assertThat(figuras, equalTo(figurasMock));
        verify(figuraRepositorio).buscarTodo();
    }

    @Test
    public void queSeObtengaUnaFiguraPorSuId(){
        Figura figuraMock = new Figura();
        figuraMock.setId(1L);

        when(this.figuraRepositorio.buscarPorId(1L)).thenReturn(figuraMock);
        Figura figura = this.figuraServicio.obtenerFiguraPorId(1L);

        assertThat(figura, equalToObject(figuraMock));
        assertThat(figura.getId(), equalTo(figuraMock.getId()) );
        verify(figuraRepositorio).buscarPorId(1L);
    }

    @Test
    public void queSeElimineUnaFigura() {
        Figura figuraMock = new Figura();
        figuraMock.setId(1L);

        figuraServicio.eliminarFigura(figuraMock.getId());

        verify(figuraRepositorio).BorrarPorId(figuraMock.getId());
    }

    @Test
    public void queSeObtengaFigurasBuscandoPorTexto() {
        List<Figura> figurasMock = new ArrayList<>();
        Figura figuraMock = new Figura();
        figuraMock.setNombre("Batman");
        figurasMock.add(figuraMock);

        // Simulación
        String textoBusqueda = "Batman";
        when(this.figuraRepositorio.darUnaListaBuscandoUnaPalabra(textoBusqueda)).thenReturn(figurasMock);

        // Ejecución
        List<Figura> figuras = this.figuraServicio.buscarSegunTexto(textoBusqueda);

        // Verificacion
        assertThat(figuras, equalTo(figurasMock));
        verify(figuraRepositorio).darUnaListaBuscandoUnaPalabra(textoBusqueda);
        verify(figuraRepositorio, never()).buscarTodo(); // Asegurarse de que no se llamó a buscarTodo
    }

    @Test
    public void queSeObtenganTodasLasFigurasCuandoTextoEsNulo() {
        // Mock
        List<Figura> figurasMock = new ArrayList<>();
        // Simulación
        when(this.figuraRepositorio.buscarTodo()).thenReturn(figurasMock);
        // Ejecución
        List<Figura> figuras = this.figuraServicio.buscarSegunTexto(null);

        // Verificación
        assertThat(figuras, equalTo(figurasMock));
        verify(figuraRepositorio).buscarTodo();
        verify(figuraRepositorio, never()).darUnaListaBuscandoUnaPalabra(anyString());
    }




}
