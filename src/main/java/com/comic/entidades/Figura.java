package com.comic.entidades;

import javax.persistence.*;
import java.util.Base64;

@Entity
public class Figura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "precio")
    private Double precio;
    @Column(name = "estado")
    private String estado;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fotoUrl")
    private String fotoUrl;

    @Lob
    private byte[] imagen;

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