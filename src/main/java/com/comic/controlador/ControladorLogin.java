package com.comic.controlador;

import com.comic.controlador.dto.DatosLogin;

import com.comic.entidades.Carrito;
import com.comic.entidades.Dto.Compra;
import com.comic.entidades.Figura;
import com.comic.entidades.Preferencias;
import com.comic.servicios.CompraServicio;
import com.comic.servicios.FiguraServicio;
import com.comic.servicios.ServicioLogin;
import com.comic.entidades.Usuario;
import com.comic.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ControladorLogin {

    @Autowired
    private ServicioLogin servicioLogin;
    private FiguraServicio figuraServicio;
    private CompraServicio compraServicio;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin , FiguraServicio figuraServicio , CompraServicio compraServicio) {
        this.servicioLogin = servicioLogin;
        this.figuraServicio=figuraServicio;
        this.compraServicio=compraServicio;
    }




//    @RequestMapping("/login")
//    public ModelAndView irALogin(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//
//        if (session.getAttribute("usuario") != null) {
//            return new ModelAndView("redirect:/home");
//        }
//        else {
//            ModelMap modelo = new ModelMap();
//            Usuario usuarioLogeado = new Usuario();
//            modelo.put("datosLogin", new DatosLogin());
//            modelo.put("usuario", usuarioLogeado);
//            return new ModelAndView("login", modelo);
//        }
//    }

    @RequestMapping("/login")
    public ModelAndView irALogin(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("usuario") != null) {
            return new ModelAndView("redirect:/home");
        } else {
            ModelMap modelo = new ModelMap();
            Usuario usuarioLogeado = new Usuario();
            modelo.put("datosLogin", new DatosLogin());
            modelo.put("usuario", usuarioLogeado);

            // Recuperar el carrito del usuario si existe
            Carrito carrito;
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario != null && usuario.getCarrito() != null) {
                carrito = usuario.getCarrito();
            } else {
                carrito = new Carrito(usuarioLogeado);  // Crear nuevo carrito si no existe
            }

            // Guardar el carrito en la sesión
            session.setAttribute("carrito", carrito);

            return new ModelAndView("login", modelo);
        }
    }

    @RequestMapping( path = "/cuenta", method = RequestMethod.GET)
    public ModelAndView irACuenta(HttpServletRequest request){

        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario datosUsuario=(Usuario)session.getAttribute("usuario");




        modelo.put("datosUsuario",datosUsuario);
        return new ModelAndView("usuario",modelo);

    }
    @RequestMapping(path = "/agregar-preferencias",method =RequestMethod.GET)//TESTEAR
    public ModelAndView irAgregarPreferencias(HttpServletRequest request){

        HttpSession session =request.getSession();
        Usuario datos =(Usuario)session.getAttribute("usuario");
        ModelMap modelo =new ModelMap();
        // Obtiene la lista de preferencias del enum
        List<Preferencias> allPreferencias = Arrays.asList(Preferencias.values());

        // Filtra las preferencias que ya están en preferenciasList del usuario
        List<Preferencias> availablePreferencias = allPreferencias.stream()
                .filter(preferencia -> !datos.getPreferenciasList().contains(preferencia))
                .collect(Collectors.toList());
        modelo.put("datos",datos);
        modelo.put("availablePreferencias", availablePreferencias);
        return  new ModelAndView("agregarPreferencias",modelo);
    }

    @RequestMapping(path = "/guardar-modificaciones",method =RequestMethod.POST)
    public ModelAndView guardarCambiosEnDatosDeCuenta(HttpServletRequest request){
        return new ModelAndView("/cuenta");

    }

    @RequestMapping(path = "quitar_Preferencias", method = RequestMethod.GET)
    public ModelAndView irAQuitarPreferencias(HttpServletRequest request){
            HttpSession session=request.getSession();
            Usuario datos=(Usuario)session.getAttribute("usuario");
            ModelMap modelo =new ModelMap();
            modelo.put("datos",datos);
            modelo.put("preferencias", datos.getPreferenciasList());
        return new ModelAndView("quitarPreferencias",modelo);
    }

    @RequestMapping(path = "/guardar-preferencias", method = RequestMethod.POST)
    public String guardarPreferencias(@RequestParam (value = "preferencias", required = false) List<Preferencias> preferencias, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Agrega las preferencias seleccionadas a la lista del usuario
        if (usuario != null && preferencias != null) {
            for (Preferencias preferencia : preferencias) {
                // Verifica si la preferencia ya está en la lista del usuario
                if (!usuario.getPreferenciasList().contains(preferencia)) {
                    usuario.getPreferenciasList().add(preferencia); // Aquí se agrega la preferencia
                }
            }
        }
       // Usuario usuarioBuscado= servicioLogin.consultarUsuario(usuario.getEmail(),usuario.getPassword());
        servicioLogin.modificarUusuario(usuario);

        // Puedes redirigir a otra página después de guardar
        return "redirect:/cuenta"; // O la página que desees
    }
    @RequestMapping(path = "/guardar-preferencias-eliminadas", method = RequestMethod.POST)
    public String guardarPreferenciasDespuesDeEliminar(@RequestParam(value = "preferencias", required = false) List<Preferencias> preferencias, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Elimina las preferencias seleccionadas de la lista del usuario
        if (usuario != null && preferencias != null) {
            // Iteramos sobre la lista de preferencias a quitar
            for (Preferencias preferencia : preferencias) {
                // Verificamos si la preferencia está en la lista del usuario
                if (usuario.getPreferenciasList().contains(preferencia)) {
                    usuario.getPreferenciasList().remove(preferencia); // Aquí se elimina la preferencia
                }
            }
        }
        servicioLogin.modificarUusuario(usuario);
        return "redirect:/cuenta"; // Redirige a la cuenta después de quitar las preferencias
    }




    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if (usuarioBuscado != null) {
            HttpSession session = request.getSession();
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            session.setAttribute("usuario", usuarioBuscado);
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }


    @RequestMapping(path = "/registrar", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("nuevoUsuario", new Usuario());
        return new ModelAndView("registrar", model);
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("nuevoUsuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        // Recuperar la confirmación de la contraseña del formulario
        String confirmPassword = request.getParameter("confirmPassword");

        // Validar si las contraseñas coinciden
        if (!usuario.getPassword().equals(confirmPassword)) {
            model.put("error", "Las contraseñas no coinciden");
            return new ModelAndView("registrar", model);
        }

        try {
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
            return new ModelAndView("registrar", model);
        } catch (Exception e) {
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("registrar", model);
        }

        return new ModelAndView("redirect:/login");
    }


//    @RequestMapping(path = "/home", method = RequestMethod.GET)
//    public ModelAndView irAHome(HttpServletRequest request) {
//        List<Figura>listaDeFiguras=figuraServicio.listarFiguras();
//        List<Figura>figurasCoincidenConPreferenciasUsuario=new ArrayList<>();
//        ModelMap modelo = new ModelMap();
//        HttpSession session = request.getSession();
//        Usuario datosUsuario=(Usuario)session.getAttribute("usuario");
//        if (datosUsuario != null) {
//            List<Preferencias> preferenciasUsuario = datosUsuario.getPreferenciasList();
//
//            figurasCoincidenConPreferenciasUsuario = listaDeFiguras.stream()
//                    .filter(figura -> figura.getPreferenciasList().stream()
//                            .anyMatch(preferenciasUsuario::contains))
//                    .collect(Collectors.toList()); // busca en la lista completa de figuras coincidencias en base a las preferencias
//        }
//
//
//        modelo.put("figurasFiltradas",figurasCoincidenConPreferenciasUsuario);
//        return new ModelAndView("home2",modelo);
//    }


//    metodo de buscar en base a compra
@RequestMapping(path = "/home", method = RequestMethod.GET)
public ModelAndView irAHome2(HttpServletRequest request) {
    ModelMap modelo = new ModelMap();
    List<Figura>figurasCoincidenConPreferenciasUsuario=new ArrayList<>();//preferencias del metodo q tenia antes
    List<Figura>listaDeFiguras=figuraServicio.listarFiguras();//preferencias del metodo q tenia antes
    HttpSession session = request.getSession();
    Usuario datosUsuario = (Usuario) session.getAttribute("usuario");

    // Obtener todas las figuras disponibles en la base de datos
    List<Figura> listaDeFigurasEnBaseDeDatos = figuraServicio.listarFiguras();
    List<Figura> listaDeFigurasQueCoinciden = new ArrayList<>();

//    if (datosUsuario != null) {
//        // recupero todas las compras desde el servicio
//        List<Compra> todasLasCompras = compraServicio.listarlasCompras();
//
//        // Filtro solo las compras que pertenecen al usuario actual
//        List<Compra> comprasDelUsuario = todasLasCompras.stream()
//                .filter(compra -> compra.getUsuario().getId().equals(datosUsuario.getId()))
//                .collect(Collectors.toList());
//
//        // Filtrar las figuras compradas por el usuario que coinciden con las figuras en la base de datos
//        for (Compra compra : comprasDelUsuario) {
//            for (Figura figuraComprada : compra.getFiguras()) {
//                // Comparar la figura comprada con las figuras de la base de datos usando contains para coincidencias parciales
//                listaDeFigurasEnBaseDeDatos.stream()
//                        .filter(figura -> figura.getNombre().toLowerCase().contains(figuraComprada.getNombre().toLowerCase()) &&
//                                figura.getPreferenciasList().stream().anyMatch(figuraComprada.getPreferenciasList()::contains))
//                        .forEach(listaDeFigurasQueCoinciden::add);
//            }
//        }
//    }
    if (datosUsuario != null) {
        List<Preferencias> preferenciasUsuario = datosUsuario.getPreferenciasList();

        figurasCoincidenConPreferenciasUsuario = listaDeFiguras.stream()
                .filter(figura -> figura.getPreferenciasList().stream()
                        .anyMatch(preferenciasUsuario::contains))
                .collect(Collectors.toList()); // busca en la lista completa de figuras coincidencias en base a las preferencias
    }


    modelo.put("figurasFiltradas",figurasCoincidenConPreferenciasUsuario);
    // Agregar la lista de figuras que coinciden al modelo para usarla en el HTML
//    modelo.addAttribute("figurasCoincidenConCompra", listaDeFigurasQueCoinciden);

    return new ModelAndView("home2", modelo);
}






    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }



    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Obtener la sesión sin crear una nueva
        if (session != null) {
            session.invalidate(); // Invalidar la sesión actual
        }
        return new ModelAndView("redirect:/home"); // Redirigir a la página de inicio
    }

    @RequestMapping(path = "/moificarDatosUsuario", method = RequestMethod.GET)
    public ModelAndView irAAFormularioMoficiarDatosUsuario(HttpServletRequest request){
        ModelMap modelo=new ModelMap();
        HttpSession session=request.getSession();
        Usuario usuario= (Usuario)session.getAttribute("usuario");
        modelo.put("datos",usuario);
        return new ModelAndView("modificarDatosUsuario",modelo);
    }

    @RequestMapping(path = "confirmarModificacionesDeUsuario", method = RequestMethod.POST)
    public String confirmarModificacionesDeUsuario(@ModelAttribute("datos") Usuario usuario,HttpServletRequest request){
        HttpSession sesion =request.getSession();
        Usuario usuarioExistente=(Usuario) sesion.getAttribute("usuario");

        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setPassword(usuario.getPassword());
        servicioLogin.modificarUsuarioPorID(usuarioExistente);
        return "redirect:/cuenta";
    }
}