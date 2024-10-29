package com.comic.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "carrito")
    private Usuario usuario;  // Relación con el usuario

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "carrito_figura",  // Nombre de la tabla de unión
            joinColumns = @JoinColumn(name = "carrito_id"),  // Columna para la FK de Carrito
            inverseJoinColumns = @JoinColumn(name = "figura_id")  // Columna para la FK de Figura
    )
    private List<Figura> figuras = new ArrayList<>();  // Permite duplicados al eliminar restricciones


    private double total;

    //contructor

    public Carrito() {
        this.figuras = new ArrayList<>();  // Asegúrate de inicializar la lista
        this.total = 0.0;
    }

    // Constructor con parámetros
    public Carrito(Usuario usuario) {
        this.usuario = usuario;
        this.figuras = new ArrayList<>();
        this.total = 0.0;
    }


    // Getters y Setters
    public List<Figura> getFiguras() {
        return figuras;  // Asegúrate de que este getter sea público
    }

    public void setFiguras(List<Figura> figuras) {
        this.figuras = figuras;
    }

    public double getTotal() {
        return total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Métodos para agregar una figura al carrito
    public void agregarFigura(Figura figura) {
        this.figuras.add(figura);  // Agregar figura directamente sin verificación
        this.total += figura.getPrecio();  // Actualizar el total cada vez que se agrega
    }

    // Método para vaciar el carrito
    public void vaciarCarrito() {
        this.figuras.clear();
        this.total = 0.0;
    }


}
