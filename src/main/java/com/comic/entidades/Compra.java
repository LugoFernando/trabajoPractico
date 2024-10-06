package com.comic.entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monto_total")
    private Double montoTotal;

    // Relación ManyToOne con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Relación OneToMany con Figura (una compra puede tener varias figuras)
    @OneToMany
    @JoinTable(
            name = "compra_figura",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "figura_id")
    )
    private List<Figura> figuras = new ArrayList<>();

    public Compra() {}

    public Compra(Usuario usuario, List<Figura> figuras) {
        this.usuario = usuario;
        this.figuras = figuras;
        calcularMontoTotal();
    }

    // Método para calcular el monto total sumando los precios de las figuras
    public void calcularMontoTotal() {
        this.montoTotal = figuras.stream()
                .mapToDouble(Figura::getPrecio)
                .sum();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Figura> getFiguras() {
        return figuras;
    }

    public void setFiguras(List<Figura> figuras) {
        this.figuras = figuras;
    }
}
