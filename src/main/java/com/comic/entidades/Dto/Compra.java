package com.comic.entidades.Dto;

import com.comic.entidades.PedidoCarrito;
import com.comic.entidades.PedidoCompra;
import com.comic.entidades.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PedidoCompra> pedidoCarritos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_total")
    private double precioTotal;


    public Compra(){
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PedidoCompra> getListaDePedidosAcomprar() {
        return pedidoCarritos;
    }

    public void setListaDePedidosAcomprar(List<PedidoCompra> pedidoCarritos) {
        this.pedidoCarritos = pedidoCarritos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
