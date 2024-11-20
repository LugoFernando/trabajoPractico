package com.comic.punta_a_punta.vistas;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class VistaRegistrar extends VistaWeb {

    public VistaRegistrar(Page page) {
        super(page);
        page.navigate("http://localhost:8080/registrar");
    }

    public void escribirEmail(String email) {
        this.escribirEnElElemento("#email", email);
    }

    public void escribirPassword(String password) {
        this.escribirEnElElemento("#password", password);
    }

    public void escribirConfirmPassword(String confirmPassword) {
        this.escribirEnElElemento("#confirmPassword", confirmPassword);
    }

    public void seleccionarPreferencia(String preferencia) {
        Locator checkbox = this.page.locator("#" + preferencia);
        if (!checkbox.isChecked()) {
            checkbox.click();  // Solo hace clic si no está seleccionado
        }
    }

    public void darClickEnRegistrarme() {
        this.darClickEnElElemento("button[type='submit']");
    }

    public String obtenerMensajeDeError() {
        return this.obtenerTextoDelElemento("p.alert.alert-danger");
    }

    public void irALogin() {
        this.darClickEnElElemento("#ir-a-login");
    }
}
