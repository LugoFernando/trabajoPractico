package com.comic.entidades;
import com.comic.entidades.Preferencias;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;

    @ElementCollection(fetch = FetchType.EAGER,targetClass = Preferencias.class)
    @CollectionTable(name = "usuario_preferencias", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "preferencia")
    @Enumerated(EnumType.STRING)  // Almacenar el enum como un String
    private List<Preferencias> preferenciasList =new ArrayList<>();


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }

    public List<Preferencias> getPreferenciasList() {
        return preferenciasList;
    }

    public void setPreferenciasList(List<Preferencias> preferenciasList) {
        this.preferenciasList = preferenciasList;
    }
}
