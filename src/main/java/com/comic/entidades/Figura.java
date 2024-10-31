package com.comic.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column
    private Boolean disponible;



    @ElementCollection(fetch = FetchType.EAGER,targetClass = Preferencias.class)
    @CollectionTable(name = "figura_preferencias", joinColumns = @JoinColumn(name = "figura_id"))
    @Column(name = "preferencia")
    @Enumerated(EnumType.STRING)  // Almacenar el enum como un String
    private List<Preferencias> preferenciasList =new ArrayList<>();



    @Lob
    private byte[] imagen;

    public Figura(){
    }

    public Figura(Long id, String nombre, Double precio, String estado, String descripcion, Integer cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = estado;
        this.descripcion = descripcion;
        this.cantidad=cantidad;
        disponible=true;
    }

    public Figura( String nombre){
        this.nombre = nombre;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String cadena(){
       return new String(this.imagen);
    }

    public List<Preferencias> getPreferenciasList() {
        return preferenciasList;
    }

    public void setPreferenciasList(List<Preferencias> preferenciasList) {
        this.preferenciasList = preferenciasList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figura figura = (Figura) o;
        return Objects.equals(id, figura.id) && Objects.equals(nombre, figura.nombre) && Objects.equals(precio, figura.precio) && Objects.equals(estado, figura.estado) && Objects.equals(descripcion, figura.descripcion) && Objects.equals(preferenciasList, figura.preferenciasList) && Objects.deepEquals(imagen, figura.imagen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, precio, estado, descripcion, preferenciasList, Arrays.hashCode(imagen));
    }


}