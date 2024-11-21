package com.comic.controlador;

import com.comic.entidades.Carrito;
import com.comic.entidades.Dto.Compra;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorCarrito {

    @Autowired
    private ServicioLogin servicioLogin;
    private FiguraServicio figuraServicio;
    private CompraServicio compraServicio;
    private CarritoServicio carritoServicio;
    private EmailServicio emailServicio;



    @Autowired
    public ControladorCarrito(ServicioLogin servicioLogin , FiguraServicio figuraServicio , CompraServicio compraServicio , CarritoServicio carritoServicio, EmailServicio emailServicio) {
        this.servicioLogin =servicioLogin;
        this.figuraServicio=figuraServicio;
        this.compraServicio=compraServicio;
        this.carritoServicio=carritoServicio;
        this.emailServicio=emailServicio;

    }



    @PostMapping("/agregar/{id}")
    public ModelAndView agregarFigurasAlCarrito(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuarioBaseDatos = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        Figura figura = figuraServicio.obtenerFiguraPorId(id);
        Carrito carrito = usuarioBaseDatos.getCarrito();
        if (carrito == null) {
            carrito = new Carrito(usuario);
        }

        carrito.agregarFigura(figura);
        usuarioBaseDatos.setCarrito(carrito);

        servicioLogin.modificarUsuario2(usuarioBaseDatos);
        session.setAttribute("carrito", carrito);
        session.setAttribute("usuario", usuarioBaseDatos);

        return new ModelAndView("redirect:/ver");
    }


    @GetMapping("/ver")
    public ModelAndView verCarrito(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuarioBaseDatos = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        Carrito carrito = usuarioBaseDatos.getCarrito();

        if (carrito == null) {
            carrito = new Carrito(usuario);
        }

        ModelAndView modelAndView = new ModelAndView("carrito");
        modelAndView.addObject("pedidos", carrito.getPedidos());
        modelAndView.addObject("total", carrito.getTotal());

        return modelAndView;
    }


    @PostMapping("/eliminar/{id}")
    public ModelAndView eliminarFiguraDelCarrito(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
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

        return new ModelAndView("redirect:/ver");
    }


    @PostMapping("/vaciar")
    public ModelAndView vaciarCarrito(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuarioBaseDatos = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        Carrito carrito = usuarioBaseDatos.getCarrito();

        if (carrito != null) {
            carrito.vaciarCarrito();
            usuarioBaseDatos.setCarrito(carrito);

            servicioLogin.modificarUsuario2(usuarioBaseDatos);
            session.setAttribute("carrito", carrito);
        }

        return new ModelAndView("redirect:/ver");
    }



    @PostMapping("/pagar")
    public ModelAndView terminarCompra(HttpSession session) throws IOException {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuarioBaseDatos = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        Carrito carrito = usuarioBaseDatos.getCarrito();

        if (carrito != null) {
            compraServicio.guardarCompra(usuarioBaseDatos);
            carrito.vaciarCarrito();
            usuarioBaseDatos.setCarrito(carrito);

            servicioLogin.modificarUsuario2(usuarioBaseDatos);
            session.setAttribute("carrito", carrito);
            emailServicio.mandarEmail();
        }

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/compras")
    public ModelAndView irAlaListaDeCompras(HttpSession session){

        Usuario datos =(Usuario)session.getAttribute("usuario");
        List<Compra>compras=compraServicio.listarlasCompras();
        List<Compra>comprasDelUsuarioIndicado=new ArrayList<>();
        for (Compra compraItem :compras){
           if(compraItem.getUsuario().getId().equals(datos.getId())) {
               comprasDelUsuarioIndicado.add(compraItem);
           }
        }
        ModelMap map=new ModelMap();
        map.put("listaDeComprasDeUsuario",comprasDelUsuarioIndicado);
        return new ModelAndView("listaDeCompras",map);
    }

    @GetMapping("/compras/detalle/{id}")
    public ModelAndView verDetalleCompra(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Compra compra = compraServicio.buscarCompraPorId(id);

        if (compra == null || !compra.getUsuario().getId().equals(usuario.getId())) {
            return new ModelAndView("error", new ModelMap("mensaje", "No tienes acceso a esta compra."));
        }

        ModelMap map = new ModelMap();
        map.put("compra", compra);
        map.put("pedidoCarritos", compra.getListaDePedidosAcomprar());
        map.put("precioTotal", compra.getPrecioTotal());

        return new ModelAndView("detalleCompra", map);
    }






}
