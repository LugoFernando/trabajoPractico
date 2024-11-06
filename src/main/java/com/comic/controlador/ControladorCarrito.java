package com.comic.controlador;

import com.comic.entidades.Carrito;
import com.comic.entidades.Figura;
import com.comic.entidades.Pedido;
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



    @PostMapping("/agregar/{id}")// hacer
    public String agregarFigurasAlCarrito(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario"); // Verifica si el usuario está logueado
        if (usuario == null) {
            return "redirect:/login"; // Redirige al login si no está logueado
        }

        Usuario usuarioBaseDatos = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        Figura figura = figuraServicio.obtenerFiguraPorId(id); // Obtiene la figura
//        Carrito carrito = carritoServicio.obtenerCarritoPorUsuario(usuario); // Recupera el carrito del usuario

        Carrito carrito = usuarioBaseDatos.getCarrito();

        if (carrito == null) {
            carrito = new Carrito(usuario); // Crea un nuevo carrito si no existe
        }
        carrito.agregarFigura(figura);
        usuarioBaseDatos.setCarrito(carrito);

        servicioLogin.modificarUsuario2(usuarioBaseDatos);

        session.setAttribute("carrito", carrito);
        session.setAttribute("usuario", usuarioBaseDatos);
         // Actualiza el carrito en la sesión

        return "redirect:/ver"; // Redirige al carrito
    }

    @GetMapping("/ver") //hacer
    public String verCarrito(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario"); // Verifica si el usuario está logueado
        if (usuario == null) {
            return "redirect:/login";
        }

        Usuario usuarioBaseDatos = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        Carrito carrito = usuarioBaseDatos.getCarrito();

        if (carrito == null) {
            carrito = new Carrito(usuario); // Crea un carrito
        }

        model.addAttribute("pedidos", carrito.getPedidos()); //
        model.addAttribute("total", carrito.getTotal());

        return "carrito";
    }

    @PostMapping("/eliminar/{id}") //hacer
    public String eliminarFiguraDelCarrito(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Usuario usuarioBaseDatos = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        Figura figura = figuraServicio.obtenerFiguraPorId(id);

        Carrito carrito = usuarioBaseDatos.getCarrito();

        if (carrito != null) {
            carrito.eliminarFigura(figura);
            usuarioBaseDatos.setCarrito(carrito);

            servicioLogin.modificarUsuario2(usuarioBaseDatos);
            session.setAttribute("carrito", carrito);
        }

        return "redirect:/ver";
    }

    @PostMapping("/vaciar") //hacer
    public String vaciarCarrito(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Usuario usuarioBaseDatos = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        Carrito carrito = usuarioBaseDatos.getCarrito();

        if (carrito != null) {
            carrito.vaciarCarrito();
            usuarioBaseDatos.setCarrito(carrito);

            servicioLogin.modificarUsuario2(usuarioBaseDatos);
            session.setAttribute("carrito", carrito);
        }

        return "redirect:/ver";
    }



}
