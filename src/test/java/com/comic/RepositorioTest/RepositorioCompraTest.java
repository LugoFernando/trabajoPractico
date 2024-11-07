package com.comic.RepositorioTest;

import com.comic.entidades.Dto.Compra;
import com.comic.entidades.Figura;
import com.comic.entidades.Pedido;
import com.comic.entidades.Usuario;
import com.comic.integracion.config.HibernateTestConfig;

import com.comic.repositorios.CompraRepositorio;
import com.comic.repositorios.implementacion.CompraRepositorioImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class RepositorioCompraTest {

    @Autowired
    private SessionFactory sessionFactory;

    private CompraRepositorio compraRepositorio;

    @BeforeEach
    public void init() {
        this.compraRepositorio = new CompraRepositorioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSeObteganTodosLasComprasQueEstanEnLaBaseDeDatos() {

        Compra compraMock1 = new Compra();

        Figura figura1 = new Figura("superman");
        figura1.setPrecio(100.0);

        Pedido pedido1 = new Pedido(1L , figura1 , 10);

        this.sessionFactory.getCurrentSession().save(compraMock1);

        List<Compra> compras = this.compraRepositorio.buscarTodasLasCompras();

        List<Compra> compraMockObtenida = Arrays.asList(compraMock1);

        assertThat(compras, equalTo(compraMockObtenida));
        assertThat(compras.size(), equalTo(1));
        assertThat(compras.size(), equalTo(compraMockObtenida.size()));
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void queSeGuardeUnaCompraNuevaEnLaBaseDeDatos() {
//        Compra compraMock1 = new Compra();
//
//        Figura figura1 = new Figura("superman");
//        figura1.setPrecio(100.0);
//
//        Pedido pedido1 = new Pedido(1L , figura1 , 10);
//
//        this.sessionFactory.getCurrentSession().save(compraMock1);
//
//        this.compraRepositorio.guardar(compraMock1);
//
//
//
//    }

}
