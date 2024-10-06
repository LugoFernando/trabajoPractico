package com.comic.servicios.implementacion;

import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.repositorios.CompraRepositorio;
import com.comic.servicios.CompraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("CompraServicio")
@Transactional
public class CompraServicioImp implements CompraServicio {

    private CompraRepositorio compraRepositorio;

    @Autowired
    public CompraServicioImp(CompraRepositorio compraRepositorio) {
        this.compraRepositorio = compraRepositorio;
    }


    @Override
    public List<Figura> obtenerFigurasRelacionadas(Usuario usuario) {
        return compraRepositorio.obtenerFigurasRelacionadas(usuario);
    }
}
