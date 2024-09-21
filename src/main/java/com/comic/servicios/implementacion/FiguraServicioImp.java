package com.comic.servicios.implementacion;

import com.comic.entidades.Figura;
import com.comic.repositorios.FiguraRepositorio;
import com.comic.servicios.FiguraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
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
    public void guardarFigura(Figura figura , MultipartFile imagen) {
        if(imagen != null) {
            try {
                figura.setImagen(Base64.getEncoder().encode(imagen.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
