package com.comic.controlador;

import com.comic.entidades.Figura;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Base64;
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
    public String listarFiguras(Model model) {
        List<Figura> figuras = figuraServicio.listarFiguras();
        model.addAttribute("figuras", figuras);
        return "figuras";
    }

    // pagina para el formulario de nueva figura
    @GetMapping("/nueva")
    public String nuevaFiguraForm(Model model) {
        model.addAttribute("figura", new Figura());
        return "nuevaFigura";
    }

    // guardar una nueva figura
    @PostMapping("/guardar")
    public String guardarFigura(@ModelAttribute ("figura") Figura figura, @RequestParam("imagen") MultipartFile imagen) {
        figuraServicio.guardarFigura(figura , imagen);
        return "redirect:/lista";
    }


    // eliminar una figura por id
    @GetMapping("/eliminar/{id}")
    public String eliminarFigura(@PathVariable Long id) {
        figuraServicio.eliminarFigura(id);
        return "redirect:/lista";
    }

    // mostrar una figura específica por id
    @GetMapping("/detalle/{id}")
    public String detalleFigura(@PathVariable Long id, Model model) {
        Figura figura = figuraServicio.obtenerFiguraPorId(id);
        model.addAttribute("figura", figura);
        return "detalle-figura";
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
    public ModelAndView actualizarFigura(@ModelAttribute("figura") Figura figura) {
        figuraServicio.actualizar(figura);
        return new ModelAndView("redirect:/lista");
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

        // Si el usuario ha ingresado una búsqueda
        if (palabraBuscada != null && !palabraBuscada.isEmpty()) {
            figuras = figuraServicio.buscarSegunTexto(palabraBuscada); // Buscar figuras que coincidan con la palabra
            model.addAttribute("palabraBuscada", palabraBuscada); // Añadir la palabra al modelo
        } else {
            // Si no hay búsqueda, mostrar todas las figuras o un conjunto por defecto
            figuras = figuraServicio.listarFiguras(); // Obtener todas las figuras
        }

        // Añadir la lista de figuras al modelo
        model.addAttribute("figuras", figuras);

        // Retornar la vista "listaDeProducto"
        return new ModelAndView("listaDeProducto");
    }










}
