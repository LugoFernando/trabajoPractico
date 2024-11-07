package com.comic.RepositorioTest;

import com.comic.entidades.Dto.Compra;
import com.comic.entidades.Figura;
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

//    @Test
//    @Transactional
//    @Rollback
//    public void queSeObteganTodosLasComprasQueEstanEnLaBaseDeDatos() {
//        Compra compraMock1 = new Compra();
//        Figura figura1 = new Figura("superman");
//        figura1.setPrecio(100.0);
//        Figura figura2 = new Figura("batman");
//        figura2.setPrecio(100.0);
//        compraMock1.setFiguras(Arrays.asList(figura1, figura2));
//
//        Compra compraMock2 = new Compra();
//        Figura figura3 = new Figura("naruto");
//        figura3.setPrecio(100.0);
//        compraMock2.setFiguras(Arrays.asList(figura3));
//
//        this.sessionFactory.getCurrentSession().save(compraMock1);
//        this.sessionFactory.getCurrentSession().save(compraMock2);
//
//        List<Compra> compras = this.compraRepositorio.buscarTodasLasCompras();
//
//        List<Compra> compraMockObtenida = Arrays.asList(compraMock1, compraMock2);
//
//        assertThat(compras, equalTo(compraMockObtenida));
//        assertThat(compras.size(), equalTo(2));
//        assertThat(compras.size(), equalTo(compraMockObtenida.size()));
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void queSeGuardeUnaCompraNuevaEnLaBaseDeDatos() {
//        Compra compraTest = new Compra();
//        Usuario usuarioTest = new Usuario();
//        usuarioTest.setEmail("prueba@mail.com");
//
//        Figura figuraTest1 = new Figura();
//        figuraTest1.setDescripcion("Figura 1");
//        figuraTest1.setEstado("Nuevo");
//        figuraTest1.setNombre("Batman");
//        figuraTest1.setPrecio(300.0);
//
//        Figura figuraTest2 = new Figura();
//        figuraTest2.setDescripcion("Figura 2");
//        figuraTest2.setEstado("Nuevo");
//        figuraTest2.setNombre("Superman");
//        figuraTest2.setPrecio(350.0);
//
//        List<Figura> figuras = new ArrayList<>();
//        figuras.add(figuraTest1);
//        figuras.add(figuraTest2);
//
//        compraTest.setUsuario(usuarioTest);
//        compraTest.setFiguras(figuras);
//        compraTest.setMontoTotal(650.0);
//
//        Session session = sessionFactory.getCurrentSession();
//        session.save(usuarioTest);
//        session.save(figuraTest1);
//        session.save(figuraTest2);
//        session.save(compraTest);
//        session.flush();
//
//        Long idGenerado = compraTest.getId();
//
//        // Método a testear
//        this.compraRepositorio.guardar(compraTest);
//
//        String hql = "SELECT c FROM Compra c WHERE c.id = :id";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
//        query.setParameter("id", idGenerado);
//        Compra compraObtenida = (Compra) query.getSingleResult();
//
//        assertThat(compraObtenida, equalTo(compraTest));
//    }
//
}
