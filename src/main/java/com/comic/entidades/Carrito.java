package com.comic.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class Carrito {

    private Usuario usuario;  // Relación con el usuario
    private List<Figura> figuras;
    private double total;

    // Constructor
    public Carrito(Usuario usuario) {
        this.usuario = usuario;
        this.figuras = new ArrayList<>();
        this.total = 0.0;
    }

    // Métodos para agregar una figura al carrito
    public void agregarFigura(Figura figura) {
        this.figuras.add(figura);
        this.total += figura.getPrecio();
    }

    // Método para vaciar el carrito
    public void vaciarCarrito() {
        this.figuras.clear();
        this.total = 0.0;
    }

    // Getters y Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Figura> getFiguras() {
        return figuras;
    }

    public double getTotal() {
        return total;
    }

}
