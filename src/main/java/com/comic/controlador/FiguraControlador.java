package com.comic.controlador;

import com.comic.entidades.Figura;
import com.comic.servicios.FiguraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@Controller
public class FiguraControlador {

    @Autowired
    private FiguraServicio figuraServicio;

    @Autowired
    public FiguraControlador(FiguraServicio figuraServicio) {
        this.figuraServicio = figuraServicio;
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
                // Asumiendo que quieres guardar la URL de la foto en el objeto
                String fotoUrl = "resources/core/images" + file.getOriginalFilename();  // Modificar con tu ruta de almacenamiento
                figura.setFotoUrl(fotoUrl);
                // Guardar la foto físicamente
                file.transferTo(new java.io.File(fotoUrl));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        figuraServicio.guardarFigura(figura);
        return "redirect:/spring/lista";
    }

    // Eliminar una figura por ID
    @GetMapping("/eliminar/{id}")
    public String eliminarFigura(@PathVariable Long id) {
        figuraServicio.eliminarFigura(id);
        return "redirect:/spring/lista";
    }

    // Mostrar una figura específica por ID
    @GetMapping("/detalle/{id}")
    public String detalleFigura(@PathVariable Long id, Model model) {
        Figura figura = figuraServicio.obtenerFiguraPorId(id);
        model.addAttribute("figura", figura);
        return "detalle-figura";
    }
}
