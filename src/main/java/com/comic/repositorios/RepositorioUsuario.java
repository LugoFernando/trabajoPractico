package com.comic.repositorios;

import com.comic.entidades.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
    Usuario buscarUsuario(Long id);
}

