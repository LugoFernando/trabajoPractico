package com.comic.servicios.implementacion;

import com.comic.servicios.EmailServicio;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class EmailServicioIImpl implements EmailServicio{




        public void mandarEmail() throws IOException {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"from\":{\"email\":\"hello@demomailtrap.com\",\"name\":\"Tus Figuras\"},\"to\":[{\"email\":\"selgadisselga@gmail.com\"}],\"subject\":\"Gracias por elegirnos!\",\"text\":\"dentro de poco podras te va a llegar un email con los detalles de tu compra,Recorda que podes ver los detalles en la web en la seccion mis compras!\",\"category\":\"Integration Test\"}");
            Request request = new Request.Builder()
                    .url("https://send.api.mailtrap.io/api/send")
                    .method("POST", body)
                    .addHeader("Authorization", "Bearer b0d01e76f29a0860962fdef36555544a")
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
        }


    }
