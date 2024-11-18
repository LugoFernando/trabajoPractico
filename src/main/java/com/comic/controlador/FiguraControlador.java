package com.comic.controlador;

import com.comic.entidades.Figura;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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



    // pagina para listar todas las figuras
    @GetMapping("/lista")
    public ModelAndView listarFiguras() {
        List<Figura> figuras = figuraServicio.listarFiguras();
        ModelAndView modelAndView = new ModelAndView("figuras"); // Asigna la vista "figuras"
        modelAndView.addObject("figuras", figuras); // Añade la lista de figuras al modelo
        return modelAndView;
    }

    // pagina para el formulario de nueva figura
    @GetMapping("/nueva")
    public ModelAndView nuevaFiguraForm() {
        ModelAndView modelAndView = new ModelAndView("nuevaFigura"); // Asigna la vista "nuevaFigura"
        modelAndView.addObject("figura", new Figura()); // Añade un nuevo objeto Figura al modelo
        return modelAndView;
    }

    // guardar una nueva figura
    @PostMapping("/guardar")
    public ModelAndView guardarFigura(@ModelAttribute("figura") Figura figura, @RequestParam("imagen") MultipartFile imagen) {
        figuraServicio.guardarFigura(figura, imagen);
        return new ModelAndView("redirect:/lista"); // Redirige a la lista de figuras
    }


    // eliminar una figura por id
    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminarFigura(@PathVariable Long id) {
        figuraServicio.eliminarFigura(id);
        return new ModelAndView("redirect:/lista");
    }

    // mostrar una figura específica por id
    @GetMapping("/detalle/{id}")
<<<<<<< HEAD
    public ModelAndView detalleFigura(@PathVariable Long id) {
=======
    public String detalleFigura(@PathVariable Long id, Model model) {
>>>>>>> 7095581c6f8fceadea0e2ff0feeaae72c25b07b3
        Figura figura = figuraServicio.obtenerFiguraPorId(id);
        ModelAndView modelAndView = new ModelAndView("detalleFigura"); // Asigna la vista "detalleFigura"
        modelAndView.addObject("figura", figura); // Añade el objeto figura al modelo
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
            // Cambiar la imagen solo si se proporciona un nuevo archivo
            if (!archivoImagen.isEmpty()) {
                figura.setImagen(archivoImagen.getBytes());
            }

            figuraServicio.actualizar(figura , archivoImagen);
            return new ModelAndView("redirect:/lista"); // Redirigir a la lista de figuras
        } catch (Exception e) {
            // Manejo de errores (opcional)
            return new ModelAndView("error"); // Redirigir a una vista de error
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



    @RequestMapping(path = "/listaDeProducto", method = RequestMethod.GET)
    public ModelAndView irAProductos(Model model, @RequestParam(value = "palabraBuscada", required = false) String palabraBuscada) {

        List<Figura> figuras;
        if (palabraBuscada != null && !palabraBuscada.isEmpty()) {
            figuras = figuraServicio.buscarSegunTexto(palabraBuscada); // busca figuras que coincidan
            model.addAttribute("palabraBuscada", palabraBuscada); //  añade el resultado al model
        } else {
            figuras = figuraServicio.listarFiguras();
        }
        // añade la lista
        model.addAttribute("figuras", figuras);

        return new ModelAndView("listaDeProducto");
    }


}