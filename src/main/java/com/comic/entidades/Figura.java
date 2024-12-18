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
    @Column(name = "activo")
    private Boolean activo = true;



    @ElementCollection(fetch = FetchType.EAGER,targetClass = Preferencias.class)
    @CollectionTable(name = "figura_preferencias", joinColumns = @JoinColumn(name = "figura_id"))
    @Column(name = "preferencia")
    @Enumerated(EnumType.STRING)  // Almacenar el enum como un String
    private List<Preferencias> preferenciasList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

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

    public Figura(Long id, String nombre, Double precio, String estado, String descripcion , Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = estado;
        this.descripcion = descripcion;
        this.activo=activo;
    }

    public Figura ( String nombre, Double precio){
        this.nombre = nombre;
        this.precio = precio;
    }

    public Figura( String nombre){
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public List<Preferencias> getPreferenciasList() {
        return preferenciasList;
    }

    public void setPreferenciasList(List<Preferencias> preferenciasList) {
        this.preferenciasList = preferenciasList;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Figura figura = (Figura) obj;
        return Objects.equals(id, figura.id); // Usa el ID para comparar la igualdad
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}