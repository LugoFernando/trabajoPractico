package com.comic.serviciosTest;

import com.comic.entidades.Compra;
import com.comic.entidades.Figura;
import com.comic.repositorios.CompraRepositorio;
import com.comic.servicios.CompraServicio;
import com.comic.servicios.implementacion.CompraServicioImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class CompraServicioTEst {

    private CompraServicio compraServicio;
    private CompraRepositorio compraRepositorio;

    @BeforeEach
    public void init() {
        this.compraRepositorio = mock(CompraRepositorio.class);
        this.compraServicio = new CompraServicioImp(compraRepositorio);
    }

    @Test
    public void queSeObtengaUnaListaConTodosLasCompras(){
        List<Compra> comprasMock = new ArrayList<>();

        when(this.compraRepositorio.buscarTodasLasCompras()).thenReturn(comprasMock);
        List<Compra> figuras = this.compraServicio.listarlasCompras();

        assertThat(figuras, equalTo(comprasMock));
        verify(compraRepositorio).buscarTodasLasCompras();
    }

}
