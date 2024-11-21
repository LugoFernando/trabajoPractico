package com.comic.RepositorioTest;

import com.comic.entidades.Dto.Compra;
import com.comic.entidades.Figura;
import com.comic.entidades.PedidoCarrito;
import com.comic.entidades.PedidoCompra;
import com.comic.entidades.Usuario;
import com.comic.integracion.config.HibernateTestConfig;

import com.comic.repositorios.CompraRepositorio;
import com.comic.repositorios.implementacion.CompraRepositorioImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
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
import static org.junit.jupiter.api.Assertions.*;

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

        PedidoCarrito pedidoCarrito1 = new PedidoCarrito(1L , figura1 , 10);

        this.sessionFactory.getCurrentSession().save(compraMock1);

        List<Compra> compras = this.compraRepositorio.buscarTodasLasCompras();

        List<Compra> compraMockObtenida = Arrays.asList(compraMock1);

        assertThat(compras, equalTo(compraMockObtenida));
        assertThat(compras.size(), equalTo(1));
        assertThat(compras.size(), equalTo(compraMockObtenida.size()));
    }


    @Test
    @Transactional
    @Rollback
    public void queSeGuardeCompraEnLaBaseDeDatos() {
        // Crear las entidades relacionadas
        Figura figura = new Figura("Batman");
        figura.setPrecio(150.0);

        PedidoCompra pedidoCompra = new PedidoCompra();
        pedidoCompra.setFigura(figura);
        pedidoCompra.setCantidad(2);


        Usuario usuario = new Usuario();
        usuario.setEmail("test@gmail.com");
        usuario.setPassword("password123");

        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setCantidad(2);
        compra.setPrecioTotal(300.0);
        compra.getListaDePedidosAcomprar().add(pedidoCompra);

        // guardado
        this.sessionFactory.getCurrentSession().save(usuario);
        this.sessionFactory.getCurrentSession().save(figura);
        this.sessionFactory.getCurrentSession().save(compra);


        Compra compraGuardada = this.sessionFactory.getCurrentSession()
                .get(Compra.class, compra.getId());


        assertNotNull(compraGuardada);
        assertEquals(compra.getId(), compraGuardada.getId());
        assertEquals(compra.getUsuario().getEmail(), compraGuardada.getUsuario().getEmail());
        assertEquals(1, compraGuardada.getListaDePedidosAcomprar().size());
        assertEquals(300.0, compraGuardada.getPrecioTotal(), 0.01);
        assertEquals("Batman", compraGuardada.getListaDePedidosAcomprar().get(0).getFigura().getNombre());
    }

    @Test
    @Transactional
    @Rollback
    public void queSeBusqueCompraPorIdCorrectamente() {
        // Crear las entidades relacionadas
        Figura figura = new Figura("Spider-Man");
        figura.setPrecio(200.0);

        PedidoCompra pedidoCompra = new PedidoCompra();
        pedidoCompra.setFigura(figura);
        pedidoCompra.setCantidad(3); //


        Usuario usuario = new Usuario();
        usuario.setEmail("test@gmail.com");
        usuario.setPassword("password123");

        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setCantidad(3);
        compra.setPrecioTotal(600.0);
        compra.getListaDePedidosAcomprar().add(pedidoCompra);


        this.sessionFactory.getCurrentSession().save(usuario);
        this.sessionFactory.getCurrentSession().save(figura);
        this.sessionFactory.getCurrentSession().save(compra);


        Compra compraEncontrada = compraRepositorio.buscarCompraPorId(compra.getId());


        assertNotNull(compraEncontrada);
        assertEquals(compra.getId(), compraEncontrada.getId());
        assertEquals(usuario.getEmail(), compraEncontrada.getUsuario().getEmail());
        assertEquals(1, compraEncontrada.getListaDePedidosAcomprar().size());
        assertEquals("Spider-Man", compraEncontrada.getListaDePedidosAcomprar().get(0).getFigura().getNombre());
        assertEquals(600.0, compraEncontrada.getPrecioTotal(), 0.01);
        assertTrue(Hibernate.isInitialized(compraEncontrada.getListaDePedidosAcomprar()));
    }



}
