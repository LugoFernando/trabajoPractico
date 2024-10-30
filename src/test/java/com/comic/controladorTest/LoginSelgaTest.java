package com.comic.controladorTest;

import com.comic.controlador.ControladorLogin;
import com.comic.controlador.dto.DatosLogin;
import com.comic.dominio.excepcion.UsuarioExistente;
import com.comic.entidades.Compra;
import com.comic.entidades.Figura;
import com.comic.entidades.Preferencias;
import com.comic.entidades.Usuario;
import com.comic.servicios.CompraServicio;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import com.comic.servicios.UsuarioServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
public class LoginSelgaTest {

    private ControladorLogin controladorLogin;
    private Usuario usuarioMock;
    private DatosLogin datosLoginMock;
    private HttpSession sessionMock;
    private HttpServletRequest requestMock;
    private ServicioLogin servicioLoginMock;
    private FiguraServicio figuraServicio;
    private CompraServicio compraServicio;
    private UsuarioServicio usuarioServicio;
    @BeforeEach
    public void init(){
        datosLoginMock = new DatosLogin("selgadis25.com", "123456");
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("selgadis25.com");
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        servicioLoginMock = mock(ServicioLogin.class);
        controladorLogin = new ControladorLogin(servicioLoginMock,figuraServicio,compraServicio,usuarioServicio);
    }


    @Test
    public void queRedirijaAHomeSiElUsuarioYaEstaLoqueado(){
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        ModelAndView modelAndView = controladorLogin.irALogin(requestMock);
        assertThat(modelAndView.getViewName(), is("redirect:/home"));
    }

    @Test
    public void queCreeUnUsuarioCuandoNoHaySesionActiva() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorLogin.irALogin(requestMock);

        assertThat(modelAndView.getViewName(), is("login"));

        assertThat(modelAndView.getModel().get("datosLogin"), is(instanceOf(DatosLogin.class)));

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

        when(requestMock.getSession()).thenReturn(sessionMock);

        Usuario usuarioMock = new Usuario();


        when(servicioLoginMock.consultarUsuario(datosLoginMock.getEmail(), datosLoginMock.getPassword()))
                .thenReturn(usuarioMock);


        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

        assertThat(modelAndView.getViewName(), is("redirect:/home"));


        verify(sessionMock).setAttribute("usuario", usuarioMock);
        verify(requestMock.getSession()).setAttribute("ROL", usuarioMock.getRol());
    }

    @Test
    public void devuelveMensajeDeErrorCuandoLasCredencialesNoSonCorrectas(){

        when(requestMock.getSession()).thenReturn(sessionMock);


        when(servicioLoginMock.consultarUsuario(datosLoginMock.getEmail(), datosLoginMock.getPassword()))
                .thenReturn(null); // Simula credenciales incorrectas


        ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);


        assertThat(modelAndView.getViewName(), is("login"));


        assertThat(modelAndView.getModel().get("error"), is("Usuario o clave incorrecta"));
    }

    @Test
    public void pruebaSiRegistrarmeRegistraCorrectamenteUnUsuarioNuevoYRedirigeAlaPaginaDeLogin() throws UsuarioExistente {
        Usuario usuario=new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("123456"); //
        String validarContraseña="123456";
        when(requestMock.getParameter("confirmPassword")).thenReturn(validarContraseña);

        doNothing().when(servicioLoginMock).registrar(usuario);

        ModelAndView modelAndView = controladorLogin.registrarme(usuario,requestMock);


        assertThat(modelAndView.getViewName(), is("redirect:/login"));


        verify(servicioLoginMock).registrar(usuario);
    }

    @Test
    public void pruebaSiElMetodoRegistrarmeMuestraElmensajeDeErrorCuandoSeIntentaRegistrarUnUsuarioConUnEmailExistente() throws UsuarioExistente {
        Usuario usuario=new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("123456");
        String validarContraseña="123456";
        when(requestMock.getParameter("confirmPassword")).thenReturn(validarContraseña);
        doThrow(new UsuarioExistente()).when(servicioLoginMock).registrar(usuario);
        ModelAndView modelAndView=controladorLogin.registrarme(usuario,requestMock);

        assertThat(modelAndView.getModel().get("error"),is("El usuario ya existe"));
    }

    @Test
    public void pruebaSiElMetodoRegistrarmeMuestraElMensajeDeErrorCuandoOcurreUnErrorAlRegistrar() throws UsuarioExistente {

        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("123456");
        String validarContraseña="123456";
        when(requestMock.getParameter("confirmPassword")).thenReturn(validarContraseña);//recupero la session

        doThrow(new RuntimeException("error")).when(servicioLoginMock).registrar(usuario);


        ModelAndView modelAndView = controladorLogin.registrarme(usuario,requestMock);


        assertThat(modelAndView.getViewName(), is("registrar"));


        assertThat(modelAndView.getModel().get("error"), is("Error al registrar el nuevo usuario"));
    }

    @Test
    public void pruebaSiLogoutInvalidaLaSesionCorrectamente(){
        when(requestMock.getSession(false)).thenReturn(sessionMock);

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

        List<Preferencias>listaDePreFerenciasVacias=new ArrayList<>();

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);
        when(usuarioMock.getPreferenciasList()).thenReturn(listaDePreFerenciasVacias);

        ModelAndView modelAndView = controladorLogin.irAgregarPreferencias(requestMock);


        assertThat(modelAndView.getViewName(), is("agregarPreferencias"));
        assertThat(modelAndView.getModel().get("availablePreferencias"),
                is(Arrays.asList(Preferencias.values())));
        assertThat(modelAndView.getModel().get("datos"), is(usuarioMock));

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


        assertThat(modelAndView.getViewName(), is("agregarPreferencias"));
        assertThat(modelAndView.getModel().get("availablePreferencias"),
                is(preferenciasEsperadas));
        assertThat(modelAndView.getModel().get("datos"), is(usuarioMock));
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

        assertThat(modelAndView.getViewName(), is("quitarPreferencias"));
        assertThat(modelAndView.getModel().get("preferencias"), is(listaDePreFerenciasConMarvel));

    }
    @Test
    public void testGuardarPreferenciasDespuesDeEliminar() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        List<Preferencias> preferenciasAEliminar = new ArrayList<>();
        Preferencias preferenciaARemover = Preferencias.MARVEL;
        preferenciasAEliminar.add(preferenciaARemover);

        List<Preferencias> preferenciasActuales = new ArrayList<>(Arrays.asList(preferenciaARemover, Preferencias.DC));
        when(usuarioMock.getPreferenciasList()).thenReturn(preferenciasActuales);

        String result = controladorLogin.guardarPreferenciasDespuesDeEliminar(preferenciasAEliminar, requestMock);

        assertThat(preferenciasActuales, is(Arrays.asList(Preferencias.DC)));
        verify(servicioLoginMock).modificarUusuario(usuarioMock);
        assertThat(result, is("redirect:/cuenta"));
    }

    @Test
    public void testGuardarPreferencias() {
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuarioMock);

        List<Preferencias> nuevasPreferencias = Arrays.asList(Preferencias.MARVEL, Preferencias.ANIME);

        List<Preferencias> preferenciasExistentes = new ArrayList<>();
        when(usuarioMock.getPreferenciasList()).thenReturn(preferenciasExistentes);

        String resultado = controladorLogin.guardarPreferencias(nuevasPreferencias, requestMock);


        assertThat(preferenciasExistentes.size(), is(2));
        assertThat(preferenciasExistentes, is(nuevasPreferencias));


        verify(servicioLoginMock).modificarUusuario(usuarioMock);


        assertThat(resultado, is("redirect:/cuenta"));
    }





}
