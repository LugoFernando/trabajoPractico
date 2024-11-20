package com.comic.punta_a_punta.vistas;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VistaRegistrarE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaRegistrar vistaRegistrar;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(800));
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaRegistrar = new VistaRegistrar(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void testRegistroConDatosValidos() {

        vistaRegistrar.escribirEmail("fer@gmail.com");
        vistaRegistrar.escribirPassword("123");
        vistaRegistrar.escribirConfirmPassword("123");
        vistaRegistrar.darClickEnRegistrarme();

        String urlActual = vistaRegistrar.obtenerURLActual();
        assertTrue(urlActual.contains("/login"));
    }

    @Test
    void testRegistroConContraseñasNoCoincidentes() {
        // Actuar
        vistaRegistrar.escribirEmail("test@example.com");
        vistaRegistrar.escribirPassword("password123");
        vistaRegistrar.escribirConfirmPassword("password321");
        vistaRegistrar.darClickEnRegistrarme();

        String mensajeError = vistaRegistrar.obtenerMensajeDeError();
        assertEquals("Error Las contraseñas no coinciden", mensajeError);
    }

    @Test
    void testRegistroConUsuarioExistente() {

        vistaRegistrar.escribirEmail("selgadis@gmail.com");  // Usuario ya registrado
        vistaRegistrar.escribirPassword("selga");
        vistaRegistrar.escribirConfirmPassword("selga");
        vistaRegistrar.darClickEnRegistrarme();


        String mensajeError = vistaRegistrar.obtenerMensajeDeError();


        assertEquals("Error El usuario ya existe", mensajeError);
    }


    @Test
    void testIrALoginDesdeRegistrar() {

        vistaRegistrar.irALogin();

        String urlActual = vistaRegistrar.obtenerURLActual();
        assertTrue(urlActual.startsWith("http://localhost:8080/login"),
                "No se redirigió correctamente a la página de login");
    }






}


