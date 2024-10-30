package com.comic.controlador;

import com.comic.entidades.CarritoItem;
import com.comic.entidades.Usuario;
import com.comic.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CarritoItemControlador {

    @Autowired
    private ServicioLogin servicioLogin;
    private FiguraServicio figuraServicio;
    private CompraServicio compraServicio;
    //private UsuarioServicio usuarioServicio;
    private CarritoItemServicio carritoItemServicio;

    @Autowired
    public CarritoItemControlador(ServicioLogin servicioLogin , FiguraServicio figuraServicio , CompraServicio compraServicio , CarritoItemServicio carritoItemServicio) {
        this.servicioLogin = servicioLogin;
        this.figuraServicio=figuraServicio;
        this.compraServicio=compraServicio;
        //this.usuarioServicio = usuarioServicio;
        this.carritoItemServicio = carritoItemServicio;
    }


    @GetMapping("/carrito/{id}")
    public ModelAndView mostrarCarritoYAgregarFiguraElegida(@PathVariable long id, @RequestParam Integer cantidad, HttpServletRequest request){
        CarritoItem carrito;
        HttpSession session =request.getSession();
        Usuario usuarioLogueado=(Usuario)session.getAttribute("usuario");
        Usuario usuarioEncontradoEnBaseDeDatos=servicioLogin.consultarUsuario(usuarioLogueado.getEmail(),usuarioLogueado.getPassword());

        if(carritoItemServicio.buscarEnCarritoUsuarioLaFIuraConElId(usuarioLogueado.getId(),id)){
            //carrito=new CarritoItem(usuarioEncontradoEnBaseDeDatos,figuraServicio.obtenerFiguraPorId(id));
            carritoItemServicio.traerELCarritoItemDelUsuarioYmodificarCantidad(usuarioLogueado.getId(),id,cantidad);
        }else{
            carrito=new CarritoItem(usuarioEncontradoEnBaseDeDatos,figuraServicio.obtenerFiguraPorId(id));
            carrito.setCantidad(cantidad);
            carritoItemServicio.guardarCarrito(carrito);
        }

        ModelMap modelo =new ModelMap();
        modelo.put("usuarioCarrito",usuarioEncontradoEnBaseDeDatos.getCarrito());

        return new ModelAndView("carrito",modelo);
    }


}
