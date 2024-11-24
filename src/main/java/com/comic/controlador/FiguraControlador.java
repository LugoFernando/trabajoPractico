package com.comic.controlador;

import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;



@Controller
public class FiguraControlador {


    private FiguraServicio figuraServicio;


    private ServicioLogin servicioLogin;

    @Autowired
    public FiguraControlador(FiguraServicio figuraServicio , ServicioLogin servicioLogin) {
        this.figuraServicio = figuraServicio;
        this.servicioLogin = servicioLogin;
    }



    // pagina para listar todas las figuras de usuario
//    @GetMapping("/lista")
//    public ModelAndView listarFiguras(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Usuario datosUsuario = (Usuario) session.getAttribute("usuario");
//        List<Figura> figuras = figuraServicio.traerListaDeFIgurasPorUsuario(datosUsuario.getId());
//        ModelAndView modelAndView = new ModelAndView("figuras"); // Asigna la vista "figuras"
//        modelAndView.addObject("figuras", figuras); // Añade la lista de figuras al modelo
//        return modelAndView;
//    }

    @GetMapping("/lista")
    public ModelAndView listarFiguras(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario datosUsuario = (Usuario) session.getAttribute("usuario");
        List<Figura> figuras = figuraServicio.traerListaDeFIgurasPorUsuario(datosUsuario.getId());
        ModelAndView modelAndView = new ModelAndView("figuras");
        modelAndView.addObject("figuras", figuras);
        return modelAndView;
    }

    // pagina para el formulario de nueva figura
    @GetMapping("/nueva")
    public ModelAndView nuevaFiguraForm() {
        ModelAndView modelAndView = new ModelAndView("nuevaFigura");
        modelAndView.addObject("figura", new Figura());
        return modelAndView;
    }

    // guardar una nueva figura
    @PostMapping("/guardar")
    public ModelAndView guardarFigura(@ModelAttribute("figura") Figura figura, @RequestParam("imagen") MultipartFile imagen,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario datosUsuario = (Usuario) session.getAttribute("usuario");
        figura.setUsuario(datosUsuario);
        figuraServicio.guardarFigura(figura, imagen);
        return new ModelAndView("redirect:/lista");
    }


    // eliminar una figura por id
    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminarFigura(@PathVariable Long id) {
        figuraServicio.eliminarFigura(id);
        return new ModelAndView("redirect:/lista");
    }

    // mostrar una figura específica por id
    @GetMapping("/detalle/{id}")
    public ModelAndView detalleFigura(@PathVariable Long id) {
        Figura figura = figuraServicio.obtenerFiguraPorId(id);
        ModelAndView modelAndView = new ModelAndView("detalleFigura");
        modelAndView.addObject("figura", figura);
        return modelAndView;
    }


    // mostrar la vista de modificacion
    @GetMapping("/actualizar/{id}")
    public ModelAndView vistaActualizarFigura(@PathVariable Long id) {
        Figura figura = figuraServicio.obtenerFiguraPorId(id);
        if (figura != null) {
            ModelMap model = new ModelMap();
            model.put("figura", figura);
            return new ModelAndView("modificarFigura", model);
        }
        return new ModelAndView("redirect:/lista");
    }



    // actualizar la figura
    @PostMapping("/actualizar")
    public ModelAndView actualizarFigura(@ModelAttribute Figura figura, @RequestParam("archivoImagen") MultipartFile archivoImagen) {
        try {
            // cambia img
            if (!archivoImagen.isEmpty()) {
                figura.setImagen(archivoImagen.getBytes());
            }

            figuraServicio.actualizar(figura , archivoImagen);
            return new ModelAndView("redirect:/lista");
        } catch (Exception e) {
            // Manejo de errores (opcional)
            return new ModelAndView("error");
        }
    }


    @GetMapping("/productos")
    public Figura buscarFigura(@PathVariable Long id) {
        return figuraServicio.obtenerFiguraPorId(id);
    }


    @GetMapping("/limpiar")
    public String listarTodasLasFiguras() {
        return "listaDeProducto";
    }



//    @RequestMapping(path = "/listaDeProducto", method = RequestMethod.GET)
//    public ModelAndView irAProductos(Model model, @RequestParam(value = "palabraBuscada", required = false) String palabraBuscada) {
//
//        List<Figura> figuras;
//        if (palabraBuscada != null && !palabraBuscada.isEmpty()) {
//            figuras = figuraServicio.buscarSegunTexto(palabraBuscada); // busca figuras que coincidan
//            model.addAttribute("palabraBuscada", palabraBuscada); //  añade el resultado al model
//        } else {
//            figuras = figuraServicio.listarFiguras();
//        }
//        // añade la lista
//        model.addAttribute("figuras", figuras);
//
//        return new ModelAndView("listaDeProducto");
//    }

    @RequestMapping(path = "/listaDeProducto", method = RequestMethod.GET)
    public ModelAndView irAProductos(Model model, @RequestParam(value = "palabraBuscada", required = false) String palabraBuscada) {

        List<Figura> figuras;
        if (palabraBuscada != null && !palabraBuscada.isEmpty()) {
            figuras = figuraServicio.buscarSegunTexto(palabraBuscada);
            model.addAttribute("palabraBuscada", palabraBuscada);
        } else {
            figuras = figuraServicio.listarFigurasActivas();
        }

        model.addAttribute("figuras", figuras);
        return new ModelAndView("listaDeProducto");
    }











}
