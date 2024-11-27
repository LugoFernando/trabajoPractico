package com.comic.punta_a_punta.vistas;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VistaLoginE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaLogin vistaLogin;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(800));
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaLogin = new VistaLogin(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }


    @Test
    void testLoginConCredencialesInvalidas() {

        vistaLogin.escribirEMAIL("usuario@falso.com");
        vistaLogin.escribirClave("claveIncorrecta");
        vistaLogin.darClickEnIniciarSesion();

        String mensajeError = vistaLogin.obtenerMensajeDeError();
        assertNotNull(mensajeError);
        assertEquals("Error: Usuario o clave incorrecta", mensajeError);
    }


    @Test
    void testIrARegistro() {

        vistaLogin.irARegistrarme();


        String urlActual = vistaLogin.obtenerURLActual();
        String urlEsperada = "http://localhost:8080/registrar";

        // eliminar cualquier parámetro de sesión
        if (urlActual.contains(";")) {
            urlActual = urlActual.split(";")[0];
        }

        assertEquals(urlEsperada, urlActual);
    }



    @Test
    void testLoginConCredencialesValidas() {
        // Actuar
        vistaLogin.escribirEMAIL("selgadisselga@gmail.com");
        vistaLogin.escribirClave("selga");
        vistaLogin.darClickEnIniciarSesion();

        // Assert: Verificar que la redirección ocurre y el usuario ve la página esperada
        String urlActual = vistaLogin.obtenerURLActual();
        assertEquals("http://localhost:8080/home", urlActual);
    }

}
