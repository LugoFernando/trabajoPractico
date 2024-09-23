package com.comic.serviciosTest;

import com.comic.controlador.dto.DatosLogin;
import com.comic.dominio.excepcion.UsuarioExistente;
import com.comic.entidades.Usuario;
import com.comic.repositorios.RepositorioUsuario;
import com.comic.servicios.implementacion.ServicioLoginImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServiceLoginTest {

    private RepositorioUsuario repositorioUsuarioMock;
    private DatosLogin datos;
    private ServicioLoginImpl servicioLogin;

    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        // Inicializa manualmente el servicio con el repositorio mockeado
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        servicioLogin = new ServicioLoginImpl(repositorioUsuarioMock);
        datos=new DatosLogin("selgadis25@gmail.com","123456");
        // Mock de Usuario
        usuarioMock = new Usuario();
        usuarioMock.setEmail("selgadis25@gmail.com");
        usuarioMock.setPassword("123456");
    }

   /* @Override
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }*/

    @Test
    public void verificaQueCuandoSeProporcionaUnEmailyContraseñaválidosElMétodoDevuelveElUsuarioEsperado(){

        when(repositorioUsuarioMock.buscarUsuario("selgadis25@gmail.com", "123456"))
                .thenReturn(usuarioMock);

        // Llama al método que deseas probar
        Usuario usuarioEncontrado = servicioLogin.consultarUsuario(datos.getEmail() ,datos.getPassword());

        // Verifica que el usuario encontrado sea igual al usuario mockeado
        assertThat(usuarioEncontrado, equalTo(usuarioMock));


    }

//    Verifica que cuando se proporciona un email o contraseña incorrecta,
//    se maneja correctamente un valor nulo o vacío (dependiendo de la implementación de buscarUsuario).
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

        // Verifica que se lance la excepción
        assertThrows(UsuarioExistente.class, () -> {
            servicioLogin.registrar(usuarioMock); // Esta línea debe lanzar la excepción
        });

        // Verifica que el método guardar no fue llamado
        verify(repositorioUsuarioMock, never()).guardar(any(Usuario.class));
    }
    @Test
    public void verificaQueRegistrarGuardaElUsuarioCuandoNoExiste() throws UsuarioExistente {
        when(repositorioUsuarioMock.buscarUsuario(usuarioMock.getEmail(), usuarioMock.getPassword()))
                .thenReturn(null);

        servicioLogin.registrar(usuarioMock);

        verify(repositorioUsuarioMock, times(1)).guardar(usuarioMock);


    }



}
