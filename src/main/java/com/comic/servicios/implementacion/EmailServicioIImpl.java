package com.comic.servicios.implementacion;

import com.comic.entidades.Dto.Compra;
import com.comic.servicios.EmailServicio;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class EmailServicioIImpl implements EmailServicio{


    public void mandarEmail(Compra compra) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        int cantidad = (int) compra.getCantidad();

        String htmlContent = "<!DOCTYPE html>" +
                "<html lang=\"es\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Gracias por tu compra</title>" +
                "    <style>" +
                "        body { " +
                "            font-family: Arial, sans-serif; " +
                "            background-color: #f0f0f0; /* Fondo gris claro */ " +
                "            margin: 0; padding: 0; " +
                "        }" +
                "        .email-container { " +
                "            max-width: 600px; " +
                "            margin: 50px auto; " +
                "            padding: 20px; " +
                "            background: #ffffff; /* Fondo blanco del rectángulo */ " +
                "            border: 2px solid blue; " + // Borde azul
                "            border-radius: 8px; " +
                "            box-shadow: 0 4px 8px rgba(0,0,0,0.1); " +
                "            text-align: center; " +
                "        }" +
                "        .email-header { " +
                "            color: blue; " +
                "            font-size: 24px; " +
                "            font-weight: bold; " +
                "            margin-bottom: 20px; " +
                "        }" +
                "        .email-details { " +
                "            text-align: left; " +
                "            font-size: 16px; " +
                "            margin-top: 20px; " +
                "        }" +
                "        .email-details p { " +
                "            margin: 5px 0; " +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"email-container\">" +
                "        <h1 class=\"email-header\">Gracias por elegir Mis Figuras</h1>" +
                "        <p>Estos son los detalles de tu compra:</p>" +
                "        <div class=\"email-details\">" +
                "            <p><strong>Número de compra:</strong> " + compra.getId() + "</p>" +
                "            <p><strong>Cantidad de productos:</strong> " + cantidad + "</p>" +
                "            <p><strong>Monto total:</strong> $" + compra.getPrecioTotal() + "</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";

        
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{" +
                "\"from\":{\"email\":\"hello@demomailtrap.com\",\"name\":\"Tus Figuras\"}," +
                "\"to\":[{\"email\":\"selgadisselga@gmail.com\"}]," +
                "\"subject\":\"Gracias por elegirnos!\"," +
                "\"html\":\"" + htmlContent.replace("\"", "\\\"") + "\"," + //
                "\"category\":\"Compra Detalles\"" +
                "}");


        Request request = new Request.Builder()
                .url("https://send.api.mailtrap.io/api/send")
                .method("POST", body)
                .addHeader("Authorization", "Bearer b0d01e76f29a0860962fdef36555544a")
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
    }

}
