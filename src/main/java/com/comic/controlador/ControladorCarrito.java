package com.comic.controlador;

import com.comic.entidades.Carrito;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.servicios.CompraServicio;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorCarrito {

    @Autowired
    private ServicioLogin servicioLogin;
    private FiguraServicio figuraServicio;
    private CompraServicio compraServicio;

    @Autowired
    public ControladorCarrito(ServicioLogin servicioLogin , FiguraServicio figuraServicio , CompraServicio compraServicio) {
        this.servicioLogin =servicioLogin;
        this.figuraServicio=figuraServicio;
        this.compraServicio=compraServicio;
    }

    @PostMapping("/agregar/{id}")
    public String agregarFigurasAlCarrito(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario"); //verifica que este logeado en la seesion
        if (usuario == null) {
            return "redirect:/login";
        }

        Figura figura = figuraServicio.obtenerFiguraPorId(id); //agrega la figura por el id con el boton

        Carrito carrito = (Carrito) session.getAttribute("carrito"); //agrega a la sesion un carrito
        if (carrito == null) {
            carrito = new Carrito(usuario);
            session.setAttribute("carrito", carrito);  // solo setea cuando el carrito es nuevo
        }
        carrito.agregarFigura(figura);

        return "redirect:/ver";
    }


        @GetMapping("/ver")
    public String verCarrito(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Carrito carrito = (Carrito) session.getAttribute("carrito");
        model.addAttribute("carrito", carrito);
        return "verCarrito";
    }


//    @PostMapping("/confirmar")
//    public String confirmarCompra(HttpSession session) {
//        Usuario usuario = (Usuario) session.getAttribute("usuario");
//        if (usuario == null) {
//            return "redirect:/login";
//        }
//
//        Carrito carrito = (Carrito) session.getAttribute("carrito");
//
//        if (carrito != null && !carrito.getFiguras().isEmpty()) {
//            // Crear la compra a partir del carrito
//            Compra compra = new Compra();
//            compra.setUsuario(usuario);
//            compra.setFiguras(carrito.getFiguras());
//            compra.setMontoTotal(carrito.getTotal());
//
//            // Lógica para guardar la compra (usando el servicio de compras)
//            // compraServicio.guardar(compra);
//
//            // Limpiar el carrito de la sesión
//            session.removeAttribute("carrito");
//        }
//
//        return "redirect:/carrito/exito";
//    }

}
