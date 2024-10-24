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

        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private List<Figura> figuras;

        private double total;

        // Constructor aaa


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
            if (!this.figuras.contains(figura)) {  // Verificar si ya está en el carrito
                this.figuras.add(figura);
                this.total += figura.getPrecio();  // Actualizar el total solo si se agrega
            }
        }

        // Método para vaciar el carrito
        public void vaciarCarrito() {
            this.figuras.clear();
            this.total = 0.0;
        }


}
