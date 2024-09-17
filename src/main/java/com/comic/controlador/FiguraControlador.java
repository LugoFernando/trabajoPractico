package com.comic.controlador;

import com.comic.entidades.Figura;
import com.comic.servicios.FiguraServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Controller
public class FiguraControlador {

    @Autowired
    private FiguraServicio figuraServicio;

    @Autowired
    public FiguraControlador(FiguraServicio figuraService) {
        this.figuraServicio = figuraService;
    }

    @GetMapping("/figuras")
    public String listarFiguras(Model model) {
        model.addAttribute("figuras", figuraServicio.listarFiguras());
        return "figuras";
    }

    @GetMapping("/figura/nueva")
    public String nuevaFigura(Model model) {
        model.addAttribute("figura", new Figura());
        return "nuevaFigura";
    }

    @PostMapping("/figura/guardar")
    public String guardarFigura(@ModelAttribute Figura figura, @RequestParam("foto") MultipartFile foto) throws IOException {
        // Guardar la foto en el servidor
        if (!foto.isEmpty()) {
            Path directorioFotos = Paths.get("src/main/resources/static/fotos");
            String fotoNombre = foto.getOriginalFilename();
            Path rutaFoto = directorioFotos.resolve(fotoNombre);
            Files.copy(foto.getInputStream(), rutaFoto);
            figura.setFotoUrl("/fotos/" + fotoNombre);
        }
        figuraServicio.guardarFigura(figura);
        return "redirect:/figuras";
    }

    @GetMapping("/figura/eliminar/{id}")
    public String eliminarFigura(@PathVariable Long id) {
        figuraServicio.eliminarFigura(id);
        return "redirect:/figuras";
    }
}
