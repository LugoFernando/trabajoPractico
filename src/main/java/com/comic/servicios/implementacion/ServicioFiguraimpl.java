package com.comic.servicios.implementacion;

import com.comic.entidades.Figura;
import com.comic.repositorios.RepositorioFigura;
import com.comic.repositorios.implementacion.RepositorioFiguraImpl;
import com.comic.servicios.ServicioFigura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioFigura")
@Transactional
public class ServicioFiguraimpl implements ServicioFigura {

    private  RepositorioFigura repositorioFigura;

    @Autowired
    public ServicioFiguraimpl(RepositorioFigura repositorioFigura) {
        this.repositorioFigura = repositorioFigura;
    }

    @Override
    public Figura buscarFiguraPorId(Long id) {
        return repositorioFigura.buscarFigura(id);
    }
}
