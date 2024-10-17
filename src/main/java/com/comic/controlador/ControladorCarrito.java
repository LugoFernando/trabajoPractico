package com.comic.controlador;

import com.comic.entidades.Carrito;
import com.comic.entidades.Compra;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.servicios.CompraServicio;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping("/carrito")
public class ControladorCarrito {

    @Autowired
    private ServicioLogin servicioLogin;
    private FiguraServicio figuraServicio;
    private CompraServicio compraServicio;

    @Autowired
    public ControladorCarrito(ServicioLogin servicioLogin , FiguraServicio figuraServicio , CompraServicio compraServicio) {
        this.servicioLogin = servicioLogin;
        this.figuraServicio=figuraServicio;
        this.compraServicio=compraServicio;
    }



    @PostMapping("/agregar/{id}")
    public String agregarAlCarrito(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Figura figura = figuraServicio.obtenerFiguraPorId(id);

        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito(usuario);
        }

        carrito.agregarFigura(figura);
        session.setAttribute("carrito", carrito);

        // Redirigir correctamente a la página del carrito
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
        return "verCarrito";  // Thymeleaf buscará un archivo llamado "verCarrito.html"
    }


    @PostMapping("/confirmar")
    public String confirmarCompra(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito != null && !carrito.getFiguras().isEmpty()) {
            // Crear la compra a partir del carrito
            Compra compra = new Compra();
            compra.setUsuario(usuario);
            compra.setFiguras(carrito.getFiguras());
            compra.setMontoTotal(carrito.getTotal());

            // Lógica para guardar la compra (usando el servicio de compras)
            // compraServicio.guardar(compra);

            // Limpiar el carrito de la sesión
            session.removeAttribute("carrito");
        }

        return "redirect:/carrito/exito";
    }

    @GetMapping("/exito")
    public String exito() {
        return "compraExitosa";
    }

    @GetMapping("/vaciar")
    public String vaciarCarrito(HttpSession session) {
        session.removeAttribute("carrito");
        return "redirect:/carrito/ver";
    }
}
