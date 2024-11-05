package com.comic.controlador;

import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import lombok.Value;
import org.springframework.core.env.Environment;
import com.comic.entidades.UserBuyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorPago {

    private final String mercadoLibreToken;

    @Autowired
    public ControladorPago(Environment env) {
        this.mercadoLibreToken = env.getProperty("codigo.mercadoLibre");
    }

    @RequestMapping(value = "api/mp", method = RequestMethod.POST)
    public String getList (@RequestBody UserBuyer userBuyer)    {
        if(userBuyer==null){return "error json :/";}
        String title = userBuyer.getTitle();
        int quantity = userBuyer.getQuantity();
        int price = userBuyer.getUnit_price();

        try {
            MercadoPagoConfig.setAccessToken(mercadoLibreToken);

            //-- Creacion de preferencias --//
            //  1 - Preferencia de venta
            PreferenceItemRequest itemRequest = PreferenceItemRequest
                    .builder()
                    .title(title)
                    .quantity(quantity)
                    .unitPrice(new BigDecimal(price))
                    .currencyId("ARS") //se pueden poner imagenes tambien
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            //  1 - Preferencia de control de sucesos
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest
                    .builder()
                    .success("https://youtube.com") //donde mandarlo si sale ok
                    .pending("https://youtube.com") //donde mandarlo si queda pendiente
                    .failure("https://youtube.com") //donde mandarlo si falla
                    .build();

            //-- Ensamble de preferencias --//

            // Creo una preferencia que guarda todas las otra preferencias
            PreferenceRequest preferenceRequest = PreferenceRequest
                    .builder()
                    .items(items)
                    .backUrls(backUrls)
                    .build();

            //Objeto tipo cliente para comunicarme con mp
            PreferenceClient client = new PreferenceClient();
            //respuesta que nuestro cliente a partir de la info enviada
            Preference preference = client.create(preferenceRequest);

            //-- Retorno de preferencia --//
            return preference.getId();

        } catch (MPException | MPApiException e) {
            return e.getMessage();
        }

    }

    @RequestMapping(path = "/checkOut")
    public ModelAndView checkOut(HttpServletRequest request) {

        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario datosUsuario=(Usuario)session.getAttribute("usuario");

        modelo.put("datosUsuario",datosUsuario);
        return new ModelAndView("checkOut",modelo);
    }

}
