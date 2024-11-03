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
    private List<Pedido> pedidos = new ArrayList<>();  // Permite duplicados al eliminar restricciones

    private double total;

    public Carrito() {
        this.pedidos = new ArrayList<>();  // Asegúrate de inicializar la lista
        this.total = 0.0;
    }

    // Constructor con parámetros
    public Carrito(Usuario usuario) {
        this.pedidos = new ArrayList<>();
        this.total = 0.0;
    }

    public double getTotal() {
        return total;
    }

    // Métodos para agregar una figura al carrito
    public void agregarFigura(Figura figura) {

        boolean encontro = false;

        for (Pedido pedido : pedidos) {
            if (pedido.getFigura() == figura) {
                pedido.setCantidad(pedido.getCantidad() + 1);

                encontro = true;

                break;
            }
        }

        if (!encontro) {
            pedidos.add(new Pedido(figura, 1));
        }
//        this.pedidos.add(pedido);  // Agregar figura directamente sin verificación
        this.total += figura.getPrecio();  // Actualizar el total cada vez que se agrega
    }

    // Método para vaciar el carrito
    public void vaciarCarrito() {
        this.pedidos.clear();
        this.total = 0.0;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
