package com.comic.controlador;

import com.comic.servicios.ServicioCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorCarrito {

    @Autowired
    private ServicioCarrito servicioCarrito;

    @Autowired
    public ControladorCarrito(ServicioCarrito servicioCarrito){
        this.servicioCarrito = servicioCarrito;
    }

    @RequestMapping("/carrito")
    public ModelAndView contenidoCorrito(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("usuario") != null) {
            return new ModelAndView("redirect:/login");
        }
        else {
            return null;
        }
    }

}
