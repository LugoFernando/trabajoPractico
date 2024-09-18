package com.comic.controlador;

import com.comic.entidades.Figura;
import com.comic.servicios.ServicioFigura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorFigura {

@Autowired
 private ServicioFigura servicioFigura;

@Autowired
 public ControladorFigura (ServicioFigura servicioFigura)  {

    this.servicioFigura=servicioFigura;
}

    @RequestMapping(path = "/fotos", method = RequestMethod.GET)
    public ModelAndView irAFiguras() {
        ModelMap modelo = new ModelMap();

        // Supongamos que buscas la figura con id = 1
        Long idFigura = 1L; // Cambia esto según el id que necesites
        Figura figura = servicioFigura.buscarFiguraPorId(idFigura);

        // Añadir la figura al modelo para que esté disponible en la vista
        modelo.put("figura", figura);

        return new ModelAndView("fotos", modelo);
    }


}
