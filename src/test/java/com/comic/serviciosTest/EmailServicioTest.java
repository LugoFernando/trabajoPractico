package com.comic.serviciosTest;

import com.comic.servicios.implementacion.EmailServicioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import static org.mockito.Mockito.*;

public class EmailServicioTest {

    private EmailServicioImpl emailService;
    private Transport mockTransport;

    @Value("${mail.username}")
    private String username;

    @BeforeEach
    public void init() throws MessagingException {
        MockitoAnnotations.openMocks(this);

        // Crear mocks de Session y Transport
        Session mockSession = mock(Session.class);
        mockTransport = mock(Transport.class);

        // Simular comportamiento de Transport
        doNothing().when(mockTransport).sendMessage(any(MimeMessage.class), any());

    }

    @Test
    public void queSeEnvieUnCorreoExitosamente() throws MessagingException {

        // Datos del correo
        String mensajeOk = "Email Sent Successfully";
        String emailDestino = "cliente@example.com";
        String subject = "Asunto de prueba";
        String contenido = "Contenido de prueba";

        // Ejecutar el método del servicio
        String mensaje = emailService.enviarEmail(emailDestino, subject, contenido);

        // Verificar configuración del mensaje
        verify(mockTransport).sendMessage(any(MimeMessage.class), any());
        verify(mensaje).equals(mensajeOk);
    }

    @Test
    public void queFalleElEnvioDeCorreoYSeManejeExcepcion() throws MessagingException {
        // Simular una excepción en el transporte
        doThrow(new MessagingException("Error de envío")).when(mockTransport).sendMessage(any(MimeMessage.class), any());

        // Datos del correo
        String emailDestino = "cliente@example.com";
        String subject = "Asunto de prueba";
        String contenido = "Contenido de prueba";

        // Ejecutar y verificar que lanza excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            emailService.enviarEmail(emailDestino, subject, contenido);
        });

        // Validar el mensaje de la excepción
        assert (exception.getMessage().contains("Error de envío"));

        // Verificar que el transporte fue llamado
        verify(mockTransport).sendMessage(any(MimeMessage.class), any());
    }
}
