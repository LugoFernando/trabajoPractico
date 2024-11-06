package com.comic.RepositorioTest;

import com.comic.entidades.Carrito;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.integracion.config.HibernateTestConfig;
import com.comic.repositorios.CarritoRepositorio;
import com.comic.repositorios.implementacion.CarritoRepositorioImpl;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class CarritoRepositorioTest {

    @Autowired
    private SessionFactory sessionFactory;

    private CarritoRepositorio carritoRepositorio;

    @BeforeEach
    public void init() {
        this.carritoRepositorio = new CarritoRepositorioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSeGuardeUnCarritoNuevoEnLaBaseDeDatos() {

        Carrito carritoTest = new Carrito();
        carritoTest.setId(1L);

        carritoRepositorio.guardar(carritoTest);

        Long idGenerado = carritoTest.getId();
        String hql = "SELECT c FROM Carrito c WHERE c.id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", idGenerado);

        Carrito carritoObtenido = (Carrito) query.getSingleResult();

        assertThat(carritoObtenido, equalTo(carritoTest));
    }

    @Test
    @Transactional
    @Rollback
    public void queObtengaCarritoPorUsuarioQueEstaEnLaBaseDeDatos() {

        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@test.com");
        usuario.setPassword("password");

        Carrito carrito = new Carrito();
        usuario.setCarrito(carrito);

        Session session = sessionFactory.getCurrentSession();
        session.save(usuario);
        session.flush();

        Carrito carritoObtenido = carritoRepositorio.obtenerCarritoPorUsuario(usuario);

        assertNotNull(carritoObtenido);
        assertEquals(carrito.getId(), carritoObtenido.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void queModifiqueCarritoQueestaEnLaBD() {
        // Preparar datos
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@test.com");
        usuario.setPassword("password");

        Carrito carrito = new Carrito();
        usuario.setCarrito(carrito);
        carrito.setTotal(100.0);


        Session session = sessionFactory.getCurrentSession();
        session.save(usuario);
        session.flush();

        carrito.setTotal(200.0);

        carritoRepositorio.modificarCarrito(carrito);


        Carrito carritoModificado = session.get(Carrito.class, carrito.getId());


        assertNotNull(carritoModificado);
        assertEquals(200.0, carritoModificado.getTotal(), 0.01);
    }

}
