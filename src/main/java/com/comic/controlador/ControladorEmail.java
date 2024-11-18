package com.comic.controlador;

import com.comic.entidades.Carrito;
import com.comic.entidades.Pedido;
import com.comic.servicios.EmailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ControladorEmail {

    @Autowired
    private EmailServicio emailServicio;

    @PostMapping("/purchase")
    public String completarComprar(String emailCliente, Carrito carrito) { //List<Pedido> pedidos (figura(nombre,precio),cantidad) double total
        // Simular detalle de compra y link de pago
        String purchaseDetails = "Detalle de tu compra:\n- Producto: Comic X\n- Total: $20\n";
        String paymentLink = "https://www.mercadopago.com/link-de-ejemplo";

        String emailContent = purchaseDetails + "\nHaz clic en el siguiente enlace para pagar:\n" + paymentLink;

        // Enviar el correo
        emailServicio.enviarEmail(emailCliente, "Detalle de tu compra", emailContent);

        return "purchase-confirmation"; // Vista de confirmación
    }
}
