package com.comic.entidades.Dto;

import com.comic.entidades.Figura;
import com.comic.entidades.Pedido;
import com.comic.entidades.Usuario;
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

//    @OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "compra_figuras",
//            joinColumns = @JoinColumn(name = "compra_id"),
//            inverseJoinColumns = @JoinColumn(name = "figura_id")
//    )
//    @Fetch(FetchMode.SUBSELECT)  // Añadir FetchMode.SUBSELECT para evitar MultipleBagFetchException
//    private List<Figura> figuras = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_id")
    private List<Pedido>listaDePedidosAcomprar=new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "cantidad")
    private double cantidad;

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

    public List<Pedido> getListaDePedidosAcomprar() {
        return listaDePedidosAcomprar;
    }

    public void setListaDePedidosAcomprar(List<Pedido> listaDePedidosAcomprar) {
        this.listaDePedidosAcomprar = listaDePedidosAcomprar;
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

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    //    @PrePersist
//    @PreUpdate
//    public void preSave() {
//        calcularCantidad();
//        calcularPrecioTotal();
//    }

//    public void calcularCantidad() {
//        this.cantidad = this.figuras.size();
//    }
//
//    public void calcularPrecioTotal() {
//
//        this.precioTotal = this.figuras.stream().mapToDouble(Figura::getPrecio).sum();
//    }



}
