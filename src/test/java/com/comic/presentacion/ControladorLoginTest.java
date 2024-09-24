package com.comic.presentacion;

import com.comic.controlador.ControladorLogin;
import com.comic.controlador.dto.DatosLogin;
import com.comic.servicios.ServicioLogin;
import com.comic.entidades.Usuario;
import com.comic.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {

	private ControladorLogin controladorLogin;
	private Usuario usuarioMock;
	private DatosLogin datosLoginMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ServicioLogin servicioLoginMock;


	@BeforeEach
	public void init(){
		datosLoginMock = new DatosLogin("dami@unlam.com", "123");
		usuarioMock = mock(Usuario.class);
		when(usuarioMock.getEmail()).thenReturn("melina@unlam.com");
		when(usuarioMock.getPassword()).thenReturn("4321");
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(ServicioLogin.class);
		controladorLogin = new ControladorLogin(servicioLoginMock);
	}

	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente(){
		// preparacion
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
		verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
	}
	
	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHome(){
		// preparacion
		Usuario usuarioEncontradoMock = mock(Usuario.class);


		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);
		
		// ejecucion
		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);
		
		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
	}

	@Test
	public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente {

		// ejecucion
		Usuario usuario = new Usuario();
		usuario.setEmail("melina@unlam.com");
		usuario.setPassword("4321");
		when(requestMock.getParameter("confirmPassword")).thenReturn("4321");
		ModelAndView modelAndView = controladorLogin.registrarme(usuarioMock,requestMock);

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
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
	}

	@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente {
		// preparacion
		Usuario usuario = new Usuario();
		usuario.setEmail("melina@unlam.com");
		usuario.setPassword("4321");
		when(requestMock.getParameter("confirmPassword")).thenReturn("4321");
		doThrow(RuntimeException.class).when(servicioLoginMock).registrar(usuario);

		// ejecucion
		ModelAndView modelAndView = controladorLogin.registrarme(usuario,requestMock);

		// validacion
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
	}

	@Test
	public void debeRegistrarUnUsuarioNuevoMostrandoMensajeDeRegistroCorrecto() {

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
