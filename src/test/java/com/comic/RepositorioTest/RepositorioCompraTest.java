package com.comic.RepositorioTest;

import com.comic.entidades.Compra;
import com.comic.entidades.Figura;
import com.comic.integracion.config.HibernateTestConfig;

import com.comic.repositorios.CompraRepositorio;
import com.comic.repositorios.implementacion.CompraRepositorioImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
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
        Figura figura2 = new Figura("batman");
        figura2.setPrecio(100.0);
        compraMock1.setFiguras(Arrays.asList(figura1, figura2));

        Compra compraMock2 = new Compra();
        Figura figura3 = new Figura("naruto");
        figura3.setPrecio(100.0);
        compraMock2.setFiguras(Arrays.asList(figura3));

        this.sessionFactory.getCurrentSession().save(compraMock1);
        this.sessionFactory.getCurrentSession().save(compraMock2);

        List<Compra> compras = this.compraRepositorio.buscarTodasLasCompras();

        List<Compra> compraMockObtenida = Arrays.asList(compraMock1, compraMock2);

        assertThat(compras, equalTo(compraMockObtenida));
        assertThat(compras.size(), equalTo(2));
        assertThat(compras.size(), equalTo(compraMockObtenida.size()));
    }


}
