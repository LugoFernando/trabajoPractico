package com.comic.controlador;

import com.comic.entidades.Figura;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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



    // Página para listar todas las figuras
    @GetMapping("/lista")
    public String listarFiguras(Model model) {
        List<Figura> figuras = figuraServicio.listarFiguras();
        model.addAttribute("figuras", figuras);
        return "figuras";
    }

    // Página para el formulario de nueva figura
    @GetMapping("/nueva")
    public String nuevaFiguraForm(Model model) {
        model.addAttribute("figura", new Figura());
        return "nuevaFigura";
    }

    // Guardar una nueva figura
    @PostMapping("/guardar")
    public String guardarFigura(@ModelAttribute Figura figura, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // Guardar url
                String fotoUrl = "src/main/resources/webapp/resources/core/images" + file.getOriginalFilename();
                figura.setFotoUrl(fotoUrl);
                // Guardar la foto físicamente
                file.transferTo(new java.io.File(fotoUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        figuraServicio.guardarFigura(figura);
        return "redirect:/lista";
    }


    @GetMapping("/nueva2")
    public String nuevaFiguraForm2(Model model) {
        model.addAttribute("figura", new Figura());
        return "nuevaFigura2";
    }

    @PostMapping("/guardarSinImagen")
    public String guardarFiguraSinImagen(@ModelAttribute Figura figura) {
        // Guardar la figura sin modificar la foto
        figuraServicio.guardarFigura(figura);
        return "redirect:/lista";
    }

    // Eliminar una figura por ID
    @GetMapping("/eliminar/{id}")
    public String eliminarFigura(@PathVariable Long id) {
        figuraServicio.eliminarFigura(id);
        return "redirect:/lista";
    }

    // Mostrar una figura específica por ID
    @GetMapping("/detalle/{id}")
    public String detalleFigura(@PathVariable Long id, Model model) {
        Figura figura = figuraServicio.obtenerFiguraPorId(id);
        model.addAttribute("figura", figura);
        return "detalle-figura";
    }

    @GetMapping("/buscar")
    public Figura buscarFigura(@PathVariable Long id) {
        return figuraServicio.obtenerFiguraPorId(id);
    }
}
