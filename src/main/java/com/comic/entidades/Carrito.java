package com.comic.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PedidoCarrito> pedidoCarritos = new ArrayList<>();

    private double total;

    public Carrito() {
        this.pedidoCarritos = new ArrayList<>();
        this.total = 0.0;
    }

    // Constructor con parámetros
    public Carrito(Usuario usuario) {
        this.pedidoCarritos = new ArrayList<>();
        this.total = 0.0;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    } //ver si esta bien

    // Métodos para agregar una figura al carrito
    public void agregarFigura(Figura figura) {

        boolean encontro = false;

        for (PedidoCarrito pedidoCarrito : pedidoCarritos) {
            if (pedidoCarrito.getFigura() == figura) {
                pedidoCarrito.setCantidad(pedidoCarrito.getCantidad() + 1);

                encontro = true;

                break;
            }
        }

        if (!encontro) {
            pedidoCarritos.add(new PedidoCarrito(figura, 1));
        }
        this.total += figura.getPrecio();
    }

    // Método para vaciar el carrito
    public void vaciarCarrito() {
        this.pedidoCarritos.clear();
        this.total = 0.0;
    }

    public List<PedidoCarrito> getPedidos() {
        return pedidoCarritos;
    }

    public void setPedidos(List<PedidoCarrito> pedidoCarritos) {
        this.pedidoCarritos = pedidoCarritos;
    }

    public void eliminarFigura(Figura figura) {
        PedidoCarrito pedidoCarritoAEliminar = null;

        for (PedidoCarrito pedidoCarrito : pedidoCarritos) {
            if (pedidoCarrito.getFigura().equals(figura)) {
                // Resta el precio
                this.total -= pedidoCarrito.getFigura().getPrecio() * pedidoCarrito.getCantidad();
                pedidoCarritoAEliminar = pedidoCarrito;
                break;
            }
        }

        if (pedidoCarritoAEliminar != null) {
            pedidoCarritos.remove(pedidoCarritoAEliminar);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
