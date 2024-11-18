package com.comic.controlador;

import com.comic.dominio.excepcion.PrecioInvalidoException;
import com.comic.entidades.Usuario;
import com.comic.entidades.Carrito;
import com.comic.servicios.CarritoServicio;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class ControladorPago {

    private final PagoServicio pagoServicio;
    private final CarritoServicio carritoServicio;

    @Autowired
    public ControladorPago(PagoServicio pagoServicio, CarritoServicio carritoServicio) {
        this.pagoServicio = pagoServicio;
        this.carritoServicio = carritoServicio;
    }

    @RequestMapping(path = "/checkOut")
    public ModelAndView checkOut(HttpServletRequest request) {

        System.out.println("ACA ROMPE TODO");
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario datosUsuario=(Usuario)session.getAttribute("usuario");
        Carrito carrito = this.carritoServicio.obtenerCarritoPorUsuario(datosUsuario);

        modelo.put("carrito",carrito);
        modelo.put("datosUsuario",datosUsuario);
        return new ModelAndView("checkOut",modelo);
    }

    @RequestMapping(value = "/pagar")
    public ModelAndView pagar(HttpSession session) {
        ModelMap model = new ModelMap();

        Usuario datosUsuario=(Usuario)session.getAttribute("usuario");
        Carrito carrito = this.carritoServicio.obtenerCarritoPorUsuario(datosUsuario);

        try {
            if (carrito.getTotal() <= 0) {
                throw new PrecioInvalidoException("Valor Invalido");
            }
            String redirectUrl = pagoServicio.pagarOrdenMp(carrito.getTotal());
            return new ModelAndView("redirect:" + redirectUrl);
        } catch (PrecioInvalidoException | IOException e) {
            model.put("mensajeError", e.getMessage());
            return new ModelAndView("checkOut",model);
        }
    }

//
//    private final String mercadoLibreToken;
//
//    @Autowired
//    public ControladorPago(Environment env) {
//        this.mercadoLibreToken = env.getProperty("codigo.mercadoLibre");
//    }
//
//    @RequestMapping(value = "api/mp", method = RequestMethod.POST)
//    public String getList (@RequestBody UserBuyer userBuyer)    {
//        if(userBuyer==null){return "error json :/";}
//        String title = userBuyer.getTitle();
//        int quantity = userBuyer.getQuantity();
//        int price = userBuyer.getUnit_price();
//
//        try {
//            MercadoPagoConfig.setAccessToken(mercadoLibreToken);
//
//            //-- Creacion de preferencias --//
//            //  1 - Preferencia de venta
//            PreferenceItemRequest itemRequest = PreferenceItemRequest
//                    .builder()
//                    .title(title)
//                    .quantity(quantity)
//                    .unitPrice(new BigDecimal(price))
//                    .currencyId("ARS") //se pueden poner imagenes tambien
//                    .build();
//            List<PreferenceItemRequest> items = new ArrayList<>();
//            items.add(itemRequest);
//
//            //  1 - Preferencia de control de sucesos
//            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest
//                    .builder()
//                    .success("https://youtube.com") //donde mandarlo si sale ok
//                    .pending("https://youtube.com") //donde mandarlo si queda pendiente
//                    .failure("https://youtube.com") //donde mandarlo si falla
//                    .build();
//
//            //-- Ensamble de preferencias --//
//
//            // Creo una preferencia que guarda todas las otra preferencias
//            PreferenceRequest preferenceRequest = PreferenceRequest
//                    .builder()
//                    .items(items)
//                    .backUrls(backUrls)
//                    .build();
//
//            //Objeto tipo cliente para comunicarme con mp
//            PreferenceClient client = new PreferenceClient();
//            //respuesta que nuestro cliente a partir de la info enviada
//            Preference preference = client.create(preferenceRequest);
//
//            //-- Retorno de preferencia --//
//            return preference.getId();
//
//        } catch (MPException | MPApiException e) {
//            return e.getMessage();
//        }
//
//    }
//
//    @RequestMapping(path = "/checkOut")
//    public ModelAndView checkOut(HttpServletRequest request) {
//
//        ModelMap modelo = new ModelMap();
//        HttpSession session = request.getSession();
//        Usuario datosUsuario=(Usuario)session.getAttribute("usuario");
//
//        modelo.put("datosUsuario",datosUsuario);
//        return new ModelAndView("checkOut",modelo);
//    }



    //    @RequestMapping(value = "/crear-envio")
//    public ModelAndView crearViajeConPaqueteYCliente(HttpSession session) {
//        ModelMap model = new ModelMap();
//        Integer idUsuario = (Integer) session.getAttribute("IDUSUARIO");
//        Paquete paqueteActual = (Paquete) session.getAttribute("paqueteActual");
//        Viaje viajeActual = (Viaje) session.getAttribute("viajeActual");
//
//        try {
//            Cliente cliente = this.clienteServicio.obtenerClientePorId(idUsuario);
//            this.paqueteServicio.guardarPaquete(paqueteActual);
//            this.viajeServicio.crearViaje(cliente, viajeActual, paqueteActual);
//            return new ModelAndView("redirect:/pagar?precio=" + viajeActual.getPrecio());
//        } catch (PaqueteNoEncontradoException | ViajeNoEncontradoException | ClienteNoEncontradoException | PrecioInvalidoException | UsuarioNoEncontradoException e) {
//            model.put("mensajeError", e.getMessage());
//            return new ModelAndView("redirect:/*", model);
//        }
//    }
}
