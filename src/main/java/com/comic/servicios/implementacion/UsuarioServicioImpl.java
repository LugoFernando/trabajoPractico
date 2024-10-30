//package com.comic.servicios.implementacion;
//
//import com.comic.entidades.Figura;
//import com.comic.entidades.Usuario;
//import com.comic.repositorios.FiguraRepositorio;
//import com.comic.repositorios.RepositorioUsuario;
//import com.comic.repositorios.implementacion.FiguraRepositorioImpl;
//import com.comic.servicios.UsuarioServicio;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@Service
//@Transactional
//public class UsuarioServicioImpl implements UsuarioServicio {
//
//    private RepositorioUsuario repositorioUsuario;
//    private FiguraRepositorio  figuraRepositorio;
//
//    @Autowired
//    public UsuarioServicioImpl(RepositorioUsuario repositorioUsuario, FiguraRepositorio figuraRepositorio) {
//        this.repositorioUsuario = repositorioUsuario;
//        this.figuraRepositorio = figuraRepositorio;
//    }
//
//
//
//    @Override
//    public void agregarALCarrito(Long idFigura, Integer cantidad,Long idUsuario) {
//        Figura figuraEncontrada= figuraRepositorio.buscarPorId(idFigura);
//        Usuario usuarioEncontrado=repositorioUsuario.buscarUsuario(idUsuario);
//        if(figuraEncontrada.getCantidad()>=cantidad){
//            usuarioEncontrado.getCarrito().add(figuraEncontrada);
//            repositorioUsuario.modificar(usuarioEncontrado);
//        }
//    }
//
////    @Override
////    public void agregarALCarrito(Long idFigura, Integer cantidad, Long idUsuario) {
////        Figura figuraEncontrada = figuraRepositorio.buscarPorId(idFigura);
////        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(idUsuario);
////
////        if (figuraEncontrada.getCantidad() >= cantidad) {
////            // Evitar agregar duplicados al carrito
////            if (!usuarioEncontrado.getCarrito().contains(figuraEncontrada)) {
////                usuarioEncontrado.getCarrito().add(figuraEncontrada);
////            }else{
////                for(Figura figura :usuarioEncontrado.getCarrito()){
////                        if(figura.getId().equals(figuraEncontrada.getId())){
////                            Integer cantidadACtualizada= figura.getCantidadCarrito()+cantidad;
////                            figura.setCantidadCarrito(cantidadACtualizada);
////                        }
////                }
////
////            }
////            repositorioUsuario.modificar(usuarioEncontrado);
////        }
////    }
//
//}
