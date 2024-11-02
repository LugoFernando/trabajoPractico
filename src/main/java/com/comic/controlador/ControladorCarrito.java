package com.comic.controlador;

import com.comic.entidades.Carrito;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.servicios.CarritoServicio;
import com.comic.servicios.CompraServicio;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ControladorCarrito {

    @Autowired
    private ServicioLogin servicioLogin;
    private FiguraServicio figuraServicio;
    private CompraServicio compraServicio;
    private CarritoServicio carritoServicio;

    @Autowired
    public ControladorCarrito(ServicioLogin servicioLogin , FiguraServicio figuraServicio , CompraServicio compraServicio , CarritoServicio carritoServicio) {
        this.servicioLogin =servicioLogin;
        this.figuraServicio=figuraServicio;
        this.compraServicio=compraServicio;
        this.carritoServicio=carritoServicio;
    }



    @PostMapping("/agregar/{id}")
    public String agregarFigurasAlCarrito(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario"); // Verifica si el usuario está logueado
        if (usuario == null) {
            return "redirect:/login"; // Redirige al login si no está logueado
        }

        Figura figura = figuraServicio.obtenerFiguraPorId(id); // Obtiene la figura
        Carrito carrito = carritoServicio.obtenerCarritoPorUsuario(usuario); // Recupera el carrito del usuario

        if (carrito == null) {
            carrito = new Carrito(usuario); // Crea un nuevo carrito si no existe
        }

        carrito.agregarFigura(figura); // Agrega la figura al carrito
        carritoServicio.guardarCarrito(carrito); // Guarda el carrito en la base de datos

        session.setAttribute("carrito", carrito); // Actualiza el carrito en la sesión

        return "redirect:/ver"; // Redirige al carrito
    }



    @GetMapping("/ver")
    public String verCarrito(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario"); // Verifica si el usuario está logueado
        if (usuario == null) {
            return "redirect:/login"; // Redirige al login si no está logueado
        }

        Carrito carrito = carritoServicio.obtenerCarritoPorUsuario(usuario); // Recupera el carrito del usuario
        if (carrito == null) {
            carrito = new Carrito(usuario); // Crea un carrito vacío si no existe
        }

        double total = carrito.getFiguras().stream().mapToDouble(Figura::getPrecio).sum(); // Calcula el total

        model.addAttribute("figuras", carrito.getFiguras()); // Añade las figuras al modelo
        model.addAttribute("total", total); // Añade el total al modelo

        return "carrito"; // Redirige a la vista del carrito
    }

    //        @GetMapping("/ver")
//    public String verCarrito(HttpSession session, Model model) {
//        Usuario usuario = (Usuario) session.getAttribute("usuario");
//        if (usuario == null) {
//            return "redirect:/login";
//        }
//
//        Carrito carrito = (Carrito) session.getAttribute("carrito");
//        model.addAttribute("carrito", carrito);
//        return "verCarrito";
//    }





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
