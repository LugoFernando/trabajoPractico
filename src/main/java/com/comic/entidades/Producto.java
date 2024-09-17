package com.comic.entidades;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Base64;
/*
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario")
*/

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Lob
    private byte[] img;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "precio")
    private Long precio;
    @Column(name = "ID_USUARIO") //Futura relacion 1 - n
    private Integer ID_USUARIO;

    public Producto(Long id, String nombre, String descripcion, byte[] img, Integer stock, Long precio, Integer ID_USUARIO) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.img = img;
        this.stock = stock;
        this.precio = precio;
        this.ID_USUARIO = ID_USUARIO;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImageDataBase64(){
        return Base64.getEncoder().encodeToString(Base64.getDecoder().decode(this.img));
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public Integer getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(Integer ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }
}
