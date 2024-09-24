package com.comic.entidades;

import javax.persistence.*;
import java.util.Base64;

@Entity
public class Figura {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    private String estado;
    private String descripcion;

    @Lob
    private byte[] imagen;

    public Figura(){
    }

    public Figura(Long id, String nombre, Double precio, String estado, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = estado;
        this.descripcion = descripcion;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {return precio;}

    public void setPrecio(Double precio) {this.precio = precio;}


    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }


    public String cadena(){
       return new String(this.imagen);
    }


}