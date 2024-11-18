    package com.comic.servicios;

    import org.springframework.stereotype.Service;

    import java.util.List;

    public interface EmailServicio {

        String enviarEmail(String emailDestino, String subject, String contenido);

    }
