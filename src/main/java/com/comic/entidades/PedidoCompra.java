package com.comic.entidades;

import javax.persistence.*;

@Entity
public class PedidoCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "figura_id")
    private Figura figura;

    private int cantidad;

    public PedidoCompra() {
    }

    public PedidoCompra(Long id, Figura figura, int cantidad) {
        this.id = id;
        this.figura = figura;
        this.cantidad = cantidad;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
