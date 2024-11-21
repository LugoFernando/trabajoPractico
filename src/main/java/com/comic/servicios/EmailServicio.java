package com.comic.servicios;

import com.comic.entidades.Dto.Compra;

import java.io.IOException;

public interface EmailServicio {

    public void mandarEmail(Compra compra) throws IOException;
}
