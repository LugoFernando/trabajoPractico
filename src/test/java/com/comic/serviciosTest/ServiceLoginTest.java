package com.comic.serviciosTest;

import com.comic.controlador.dto.DatosLogin;
import com.comic.dominio.excepcion.UsuarioExistente;
import com.comic.entidades.Usuario;
import com.comic.repositorios.RepositorioUsuario;
import com.comic.servicios.implementacion.ServicioLoginImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServiceLoginTest {

    private RepositorioUsuario repositorioUsuarioMock;
    private DatosLogin datos;
    private ServicioLoginImpl servicioLogin;

    private Usuario usuarioMock;

    @BeforeEach
    public void init() {

        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        servicioLogin = new ServicioLoginImpl(repositorioUsuarioMock);
        datos=new DatosLogin("selgadis25@gmail.com","123456");
        usuarioMock = new Usuario();
        usuarioMock.setEmail("selgadis25@gmail.com");
        usuarioMock.setPassword("123456");
    }


    @Test
    public void verificaQueCuandoSeProporcionaUnEmailyContraseñaválidosElMétodoDevuelveElUsuarioEsperado(){

        when(repositorioUsuarioMock.buscarUsuario("selgadis25@gmail.com", "123456"))
                .thenReturn(usuarioMock);

        Usuario usuarioEncontrado = servicioLogin.consultarUsuario(datos.getEmail() ,datos.getPassword());

        assertThat(usuarioEncontrado, equalTo(usuarioMock));


    }


    @Test
    public void  verificaQueCuandoSeProporcionaUnEmailyContraseñaNoválidosElMétodoDevuelveNull(){
        when(repositorioUsuarioMock.buscarUsuario("selgadis24@gmail.com", "123456"))
                .thenReturn(usuarioMock);

        Usuario usuarioEncontrado = servicioLogin.consultarUsuario("selgadis25@gmail.com" ,"123456");

        assertThat(usuarioEncontrado, equalTo(null));



    }

    @Test void registrar_DebeLanzarUsuarioExistenteSiYaExiste() throws UsuarioExistente {

        when(repositorioUsuarioMock.buscarUsuario(usuarioMock.getEmail(), usuarioMock.getPassword()))
                .thenReturn(usuarioMock);


        assertThrows(UsuarioExistente.class, () -> {
            servicioLogin.registrar(usuarioMock);
        });


        verify(repositorioUsuarioMock, never()).guardar(any(Usuario.class));
    }

    @Test
    public void verificaQueRegistrarGuardaElUsuarioCuandoNoExiste() throws UsuarioExistente {
        when(repositorioUsuarioMock.buscarUsuario(usuarioMock.getEmail(), usuarioMock.getPassword()))
                .thenReturn(null);

        servicioLogin.registrar(usuarioMock);

        verify(repositorioUsuarioMock, times(1)).guardar(usuarioMock);
    }

    @Test
    public void verificarQueSeModifiqueElCarritoCuandoElUsuarioExista() {
        when(repositorioUsuarioMock.buscarUsuario("selgadis25@gmail.com", "123456"))
                .thenReturn(usuarioMock);

        Usuario usuarioModificado = new Usuario();
        usuarioModificado.setEmail("selgadis25@gmail.com");
        usuarioModificado.setPassword("123456");
        servicioLogin.modificarCarrito(usuarioModificado);

        verify(repositorioUsuarioMock, times(1)).modificar(usuarioMock);
    }

}
