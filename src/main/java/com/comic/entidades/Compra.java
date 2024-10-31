package com.comic.entidades;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(
            name = "compra_figuras",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "figura_id")
    )
    @Fetch(FetchMode.SUBSELECT)  // Añadir FetchMode.SUBSELECT para evitar MultipleBagFetchException
    private List<Figura> figuras = new ArrayList<>();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "precio_total")
    private double precioTotal;

    @PrePersist
    @PreUpdate
    public void preSave() {
        calcularCantidad();
        calcularPrecioTotal();
    }

    public void calcularCantidad() {
        this.cantidad = this.figuras.size();
    }

    public void calcularPrecioTotal() {
        this.precioTotal = this.figuras.stream().mapToDouble(Figura::getPrecio).sum();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Figura> getFiguras() {
        return figuras;
    }

    public void setFiguras(List<Figura> figuras) {
        this.figuras = figuras;
        calcularCantidad();
        calcularPrecioTotal();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }
}
