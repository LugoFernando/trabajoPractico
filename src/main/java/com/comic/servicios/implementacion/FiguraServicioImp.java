package com.comic.servicios.implementacion;

import com.comic.entidades.Figura;
import com.comic.repositorios.FiguraRepositorio;
import com.comic.servicios.FiguraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("FiguraServicio")
@Transactional
public class FiguraServicioImp implements FiguraServicio {

    private FiguraRepositorio figuraRepositorio;

    @Autowired
    public FiguraServicioImp(FiguraRepositorio figuraRepositorio) {
        this.figuraRepositorio = figuraRepositorio;
    }


    @Override
    public List<Figura> listarFiguras() {
        return figuraRepositorio.buscarTodo();
    }

    @Override
    public void guardarFigura(Figura figura) {
        figuraRepositorio.guardar(figura);
    }

    @Override
    public Figura obtenerFiguraPorId(Long id) {
        return figuraRepositorio.buscarPorId(id);
    }

    @Override
    public void eliminarFigura(Long id) {
        figuraRepositorio.BorrarPorId(id);
    }

}
