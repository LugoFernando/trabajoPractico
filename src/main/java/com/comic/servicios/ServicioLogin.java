package com.comic.servicios;

import com.comic.dominio.excepcion.UsuarioExistente;
import com.comic.entidades.Usuario;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;
    void modificarUusuario(Usuario usuario);
    void modificarUsuario2(Usuario usuario);
    void modificarUsuario3(Usuario usuario);
    void modificarCarrito(Usuario usuario);

    void modificarUsuarioPorID(Usuario usuario);
}
