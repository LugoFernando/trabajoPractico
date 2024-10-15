package com.comic.servicios.implementacion;

import com.comic.repositorios.RepositorioUsuario;
import com.comic.dominio.excepcion.UsuarioExistente;
import com.comic.entidades.Usuario;
import com.comic.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        repositorioUsuario.guardar(usuario);
    }

    @Override
    public void modificarUusuario(Usuario usuario) {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        usuarioEncontrado.setPreferenciasList(usuario.getPreferenciasList());
        //usuarioEncontrado.getPreferenciasList().addAll(usuario.getPreferenciasList());
        usuarioEncontrado.setEmail(usuario.getEmail());
        usuarioEncontrado.setPassword(usuario.getPassword());
        repositorioUsuario.modificar(usuarioEncontrado);
    }

    @Override
    public void modificarUsuarioPorID(Usuario usuario) {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getId());
        usuarioEncontrado.setEmail(usuario.getEmail());
        usuarioEncontrado.setPassword(usuario.getPassword());
        repositorioUsuario.modificar(usuarioEncontrado);
    }





}

