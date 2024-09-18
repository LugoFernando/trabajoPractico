package com.comic.controlador;

import com.comic.servicios.ServicioCarrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorCarrito {

    private ServicioCarrito servicioCarrito;

    @Autowired
    public ControladorCarrito(ServicioCarrito servicioCarrito){
        this.servicioCarrito = servicioCarrito;
    }

    @RequestMapping("/agregarCarrito")
    public ModelAndView agregarAlCarrito(HttpServletRequest request, @RequestParam("productoId") Integer productoId) {
        ModelMap modelo = new ModelMap();
        servicioCarrito.agregarAlCarrito(productoId);

        return new ModelAndView("carrito", modelo);

    }

    @RequestMapping("/carrito")
    public ModelAndView contenidoCarrito(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();



        return new ModelAndView("carrito", modelo);

    }

}
