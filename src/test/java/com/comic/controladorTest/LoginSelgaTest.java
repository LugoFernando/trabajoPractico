package com.comic.controladorTest;

import com.comic.controlador.ControladorLogin;
import com.comic.controlador.dto.DatosLogin;
import com.comic.dominio.excepcion.UsuarioExistente;
import com.comic.entidades.Preferencias;
import com.comic.entidades.Usuario;
import com.comic.servicios.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
public class LoginSelgaTest {

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
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);
        controladorLogin = new ControladorLogin(servicioLoginMock);
    }


    @Test
    public void queRedirijaAHomeSiElUsuarioYaEstaLoqueado(){
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
    public void devuelveMensajeDeErrorCuandoLasCredencialesNoSonCorrectas(){
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
    }

    @Test
    public void pruebaSiRegistrarmeRegistraCorrectamenteUnUsuarioNuevoYRedirigeAlaPaginaDeLogin() throws UsuarioExistente {
        Usuario usuario=new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("123456"); // Establece otros atributos según sea necesario
        String validarContraseña="123456";
        when(requestMock.getParameter("confirmPassword")).thenReturn(validarContraseña);//recupero la session
       // when(sessionMock.get("confirmPassword")).thenReturn(validarContraseña);

        doNothing().when(servicioLoginMock).registrar(usuario);


        // Llamar al método del controlador
        ModelAndView modelAndView = controladorLogin.registrarme(usuario,requestMock);

        // Verificar que se redirige a la página de login
        assertThat(modelAndView.getViewName(), is("redirect:/login"));

        // Verificar que el método de registro fue llamado con el usuario correcto
        verify(servicioLoginMock).registrar(usuario);
    }

    @Test
    public void pruebaSiElMetodoRegistrarmeMuestraElmensajeDeErrorCuandoSeIntentaRegistrarUnUsuarioConUnEmailExistente() throws UsuarioExistente {
        Usuario usuario=new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("123456");
        String validarContraseña="123456";
        when(requestMock.getParameter("confirmPassword")).thenReturn(validarContraseña);//recupero la session
        doThrow(new UsuarioExistente()).when(servicioLoginMock).registrar(usuario);
        ModelAndView modelAndView=controladorLogin.registrarme(usuario,requestMock);

        assertThat(modelAndView.getModel().get("error"),is("El usuario ya existe"));
    }

    @Test
    public void pruebaSiElMetodoRegistrarmeMuestraElMensajeDeErrorCuandoOcurreUnErrorAlRegistrar() throws UsuarioExistente {
        // Crear un nuevo usuario para registrar  Rehacer
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("123456");
        String validarContraseña="123456";
        when(requestMock.getParameter("confirmPassword")).thenReturn(validarContraseña);//recupero la session
//        when(requestMock.getParameter("confirmPassword")).thenReturn(validarContraseña); fijarse q el usuario  no exista

        doThrow(new RuntimeException("error")).when(servicioLoginMock).registrar(usuario);


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
    public void irAAgregarPreferenciasMostraraTodasLasPreferenciasPorqueNoSeEligioNinguna(){
        //Preferencias marvel=Preferencias.MARVEL;
        List<Preferencias>listaDePreFerenciasVacias=new ArrayList<>();
       // listaDePreFerenciasVacias.add(marvel);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getPreferenciasList()).thenReturn(listaDePreFerenciasVacias);

        ModelAndView modelAndView = controladorLogin.irAgregarPreferencias(requestMock);

        // 3. Verificar el resultado
        assertThat(modelAndView.getViewName(), is("agregarPreferencias")); // Verificar la vista
        assertThat(modelAndView.getModel().get("availablePreferencias"),
                is(Arrays.asList(Preferencias.values()))); // Verificar las preferencias disponibles
        assertThat(modelAndView.getModel().get("datos"), is(usuarioMock)); // Verificar que el modelo contiene el usuario

    }

    @Test
    public void irAAgregarPreferenciasYmostrarDCCOMICMANGAYANIMEporqueSeEligiocomoPreferenciaMARVEL(){
        Preferencias marvel=Preferencias.MARVEL;
        List<Preferencias>listaDePreFerenciasConMarvel=new ArrayList<>();
        listaDePreFerenciasConMarvel.add(marvel);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getPreferenciasList()).thenReturn(listaDePreFerenciasConMarvel);
        List<Preferencias>preferenciasEsperadas=new ArrayList<>();
        preferenciasEsperadas.add(Preferencias.DC);
        preferenciasEsperadas.add(Preferencias.COMIC);
        preferenciasEsperadas.add(Preferencias.MANGA);
        preferenciasEsperadas.add(Preferencias.ANIME);
        ModelAndView modelAndView = controladorLogin.irAgregarPreferencias(requestMock);

        // 3. Verificar el resultado
        assertThat(modelAndView.getViewName(), is("agregarPreferencias")); // Verificar la vista
        assertThat(modelAndView.getModel().get("availablePreferencias"),
                is(preferenciasEsperadas)); // Verificar las preferencias disponibles
        assertThat(modelAndView.getModel().get("datos"), is(usuarioMock)); // Verificar que el modelo contiene el usuario
    }

    @Test
    public void irAQuitarPreferenciasYqueMUestreComoPrefenciaElegidaMarvelParaPoderSacarla(){
        Preferencias marvel=Preferencias.MARVEL;
        List<Preferencias>listaDePreFerenciasConMarvel=new ArrayList<>();
        listaDePreFerenciasConMarvel.add(marvel);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getPreferenciasList()).thenReturn(listaDePreFerenciasConMarvel);
        ModelAndView modelAndView = controladorLogin.irAQuitarPreferencias(requestMock);

        assertThat(modelAndView.getViewName(), is("quitarPreferencias")); // Verificar la vista
        assertThat(modelAndView.getModel().get("preferencias"), is(listaDePreFerenciasConMarvel)); // Asegúrate de que el modelo contenga las preferencias correctas

    }

}
