package com.comic.repositorios.implementacion;

import com.comic.servicios.EmailServicio;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class EmailImpl implements EmailServicio {

    public void mandarEmail() throws IOException {
        // Looking to send emails in production? Check out our Email API/SMTP product!
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"from\":{\"email\":\"hello@example.com\",\"name\":\"Mailtrap Test\"},\"to\":[{\"email\":\"selgadis24@gmail.com\"}],\"subject\":\"You are awesome!\",\"text\":\"Congrats for sending test email with Mailtrap!\",\"category\":\"Integration Test\"}");
        Request request = new Request.Builder()
                .url("https://sandbox.api.mailtrap.io/api/send/3285755")
                .method("POST", body)
                .addHeader("Authorization", "Bearer f03cbb31f8a1cc1e0583bbbb464d82f4")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }
    
}
