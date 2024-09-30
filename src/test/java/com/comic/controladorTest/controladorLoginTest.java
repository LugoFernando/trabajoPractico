package com.comic.controladorTest;

import com.comic.controlador.ControladorLogin;
import com.comic.controlador.dto.DatosLogin;
import com.comic.dominio.excepcion.UsuarioExistente;
import com.comic.entidades.Usuario;
import com.comic.servicios.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class controladorLoginTest {

    private ControladorLogin controladorLogin;
    private Usuario usuarioMock;
    private DatosLogin datosLoginMock;
    private HttpSession sessionMock;//hago referencia a la sesion de un usuario donde tengo iformacion
    private HttpServletRequest requestMock;//hace referencia a las solicitudes que voy a manejar
    private ServicioLogin servicioLoginMock;

    @BeforeEach
    public void init(){
        datosLoginMock = new DatosLogin("selgadis25.com", "123456");
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("selgadis25.com");
        when(usuarioMock.getPassword()).thenReturn("123456");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);
        controladorLogin = new ControladorLogin(servicioLoginMock);
    }

    @Test
    public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente {

        // ejecucion
        Usuario usuario = new Usuario();
        usuario.setEmail("melina@unlam.com");
        usuario.setPassword("4321");
        when(requestMock.getParameter("confirmPassword")).thenReturn("4321");
        ModelAndView modelAndView = controladorLogin.registrarme(usuario,requestMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
        verify(servicioLoginMock, times(1)).registrar(usuario);
    }

    @Test
    public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente {
        // preparacion
        //requestMock.setAttribute("confirmPassword",usuarioMock.getPassword());
        Usuario usuario = new Usuario();
        usuario.setEmail("melina@unlam.com");
        usuario.setPassword("4321");
        when(requestMock.getParameter("confirmPassword")).thenReturn("4321");
        doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(usuario);

        // ejecucion
        ModelAndView modelAndView = controladorLogin.registrarme(usuario,requestMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("registrar"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
    }

    @Test
    public void debeRegistrarUnUsuarioNuevoRedirigiendomeAlLogin() throws UsuarioExistente {

        //DADO -> BeforeEach +
        Usuario usuario = new Usuario();
        usuario.setEmail("melina@unlam.com");
        usuario.setPassword("4321");
        when(requestMock.getParameter("confirmPassword")).thenReturn("4321");

        //CUANDO
        ModelAndView modelAndView = controladorLogin.registrarme(usuario,requestMock);

        //ENTONCES
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
        assertThat(modelAndView.getViewName(), is("redirect:/login"));
        verify(servicioLoginMock).registrar(usuario);
    }

    @Test
    public void queRedirijaAHomeSiElUsuarioYaEstaLogueado(){
        when(requestMock.getSession()).thenReturn(sessionMock);//recupero la session
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorLogin.irALogin(requestMock);
        assertThat(modelAndView.getViewName(), is("redirect:/home"));
    }

    @Test
    public void queCreeUnUsuarioCuandoNoHaySesionActiva() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        // Ejecutar el método del controlador
        ModelAndView modelAndView = controladorLogin.irALogin(requestMock);

        // Verificar que la vista devuelta es "login"
        assertThat(modelAndView.getViewName(), is("login"));

        // Verificar que el modelo contiene un objeto de tipo DatosLogin
        assertThat(modelAndView.getModel().get("datosLogin"), is(instanceOf(DatosLogin.class)));

        // Verificar que el modelo contiene un objeto de tipo Usuario
        assertThat(modelAndView.getModel().get("usuario"), is(instanceOf(Usuario.class)));
    }

    @Test
    public void queRedirijaALoginSiNoHayUsuarioRegistrado(){
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView=controladorLogin.irALogin(requestMock);

        assertThat(modelAndView.getViewName(),is("login"));

    }

    @Test
    public void pruebaElMetodoValidarLoginRedirigeAHomeCuandoLasCredencialesSonCorrectas() {
        // Configurar los mocks
        when(requestMock.getSession()).thenReturn(sessionMock);

        // Simular un usuario válido que se devuelve al consultar el servicio de login
        Usuario usuarioMock = new Usuario();
        //usuarioMock.setRol("ROLE_USER"); // O el rol que necesites

        // Simular el comportamiento del servicio de login
        when(servicioLoginMock.consultarUsuario(datosLoginMock.getEmail(), datosLoginMock.getPassword()))
                .thenReturn(usuarioMock);

        // Llamar al método del controlador
        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

        // Verificar que se redirige a "home"
        assertThat(modelAndView.getViewName(), is("redirect:/home"));

        // Verificar que el usuario se guardó en la sesión
        verify(sessionMock).setAttribute("usuario", usuarioMock);
        verify(requestMock.getSession()).setAttribute("ROL", usuarioMock.getRol());
    }

    @Test
    public void loginConUsuarioYPasswordInorrectosDevuelveMensajeDeErrorRedirigiendoAlLogin(){
        // Configurar los mocks
        when(requestMock.getSession()).thenReturn(sessionMock);

        // Simular que el servicio de login no encuentra al usuario
        when(servicioLoginMock.consultarUsuario(datosLoginMock.getEmail(), datosLoginMock.getPassword()))
                .thenReturn(null); // Simula credenciales incorrectas

        // Llamar al método del controlador
        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

        // Verificar que la vista devuelta sea "login"
        assertThat(modelAndView.getViewName(), is("login"));

        // Verificar que el modelo contenga el mensaje de error esperado
        assertThat(modelAndView.getModel().get("error"), is("Usuario o clave incorrecta"));
        verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
    }

    @Test
    public void pruebaSiElMetodoRegistrarmeMuestraElMensajeDeErrorCuandoOcurreUnErrorAlRegistrar() throws UsuarioExistente {
        // Crear un nuevo usuario para registrar
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("123456");
        when(requestMock.getParameter("confirmPassword")).thenReturn("123456");


        doThrow(new RuntimeException("Error inesperado")).when(servicioLoginMock).registrar(usuario);
        ModelAndView modelAndView = controladorLogin.registrarme(usuario,requestMock);

        assertThat(modelAndView.getViewName(), is("registrar"));
        assertThat(modelAndView.getModel().get("error"), is("Error al registrar el nuevo usuario"));
    }

    @Test
    public void pruebaSiLogoutInvalidaLaSesionCorrectamente(){
        when(requestMock.getSession(false)).thenReturn(sessionMock);//chequear porque tengo que pooner false

        ModelAndView modelAndView =controladorLogin.logout(requestMock);

        verify(sessionMock).invalidate();
        assertThat(modelAndView.getViewName(), is("redirect:/home"));

    }
    @Test
    public void muestraLaInformacionDelUsuarioAutentificadoyRedirigeACuenta(){

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView=controladorLogin.irACuenta(requestMock);


        assertThat(modelAndView.getViewName(), is("usuario"));


        assertThat(modelAndView.getModel().get("datosUsuario"), is(usuarioMock));


    }

    @Test
    public void debeRegistrarUnUsuarioNuevoSinObtenerNingunError() {

        //DADO -> BeforeEach +
        Usuario usuario = new Usuario();
        usuario.setEmail("melina@unlam.com");
        usuario.setPassword("4321");
        when(requestMock.getParameter("confirmPassword")).thenReturn("4321");

        //CUANDO
        ModelAndView modelAndView = controladorLogin.registrarme(usuario,requestMock);

        //ENTONCES
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));

    }


}
