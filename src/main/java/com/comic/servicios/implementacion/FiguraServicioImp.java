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
        Figura figura = figuraRepositorio.buscarPorId(id);
        if (figura != null) {
            figura.setActivo(false); // Marcar como inactivo
            figuraRepositorio.actualizar(figura); // Guardar los cambios
        }
    }

//    @Override  (ver si es mejor tener en el servicio o en el repo)
//    public void eliminarFigura(Long id) {
//        Figura figura = figuraRepositorio.buscarPorId(id); // Método que busca la figura por ID
//        if (figura == null) {
//            throw new FiguraNotFoundException("No se puede eliminar la figura, ya que no existe con el id: " + id);
//        }
//        figuraRepositorio.BorrarPorId(id); // Eliminar la figura si existe
        //hola
//    }


    @Override
    public List<Figura> buscarSegunTexto(String texto) {
        if (texto != null && !texto.trim().isEmpty()) { //el segundo parametro es para que no cuente los espacios
            return figuraRepositorio.darUnaListaBuscandoUnaPalabra(texto);
        }
        return figuraRepositorio.buscarTodo();
    }

//    @Override
//    public void actualizar(Figura figura , MultipartFile imagen) {
//        if(imagen != null) {
//            try {
//                figura.setImagen(Base64.getEncoder().encode(imagen.getBytes()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        figuraRepositorio.actualizarFigura(figura);
//    }
@Override
public void actualizar(Figura figura, MultipartFile imagen) {
    if (imagen != null && !imagen.isEmpty()) {  // Verificar que la imagen no esté vacía
        try {
            // Solo actualizar la imagen si se ha recibido un archivo de imagen
            figura.setImagen(Base64.getEncoder().encode(imagen.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Actualizar la figura en la base de datos (sin necesidad de modificar la imagen si no se recibió)
    figuraRepositorio.actualizarFigura(figura);
}


    @Override
    public List<Figura> traerListaDeFIgurasPorUsuario(Long id) {
        return figuraRepositorio.buscarFiguraPorIDUsurio(id);
    }

    @Override
    public List<Figura> listarFigurasActivas() {
        return figuraRepositorio.listarFigurasActivas();
    }


}
