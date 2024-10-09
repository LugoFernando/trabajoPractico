package com.comic.RepositorioTest;

import com.comic.integracion.config.HibernateTestConfig;
import com.comic.entidades.entidades.Figura;
import com.comic.repositorios.FiguraRepositorio;
import com.comic.repositorios.implementacion.FiguraRepositorioImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class FiguraRepositorioTest {

    @Autowired
    private SessionFactory sessionFactory;

    private FiguraRepositorio figuraRepositorio;

    @BeforeEach
    public void init() {
        this.figuraRepositorio = new FiguraRepositorioImpl(sessionFactory);
    }


    @Test
    @Transactional
    @Rollback
    public void queSeGuardeUnaFiguraNuevaEnLaBaseDeDatos() {
        // Preparar datos
        Figura figuraTest = new Figura();
        figuraTest.setDescripcion("test");
        figuraTest.setEstado("Nuevo");
        figuraTest.setNombre("Robin");
        figuraTest.setPrecio(200.0);

        Session session = sessionFactory.getCurrentSession();
        session.save(figuraTest);
        session.flush();
        Long idGenerado= figuraTest.getId();
        //medotodo a testear

        this.figuraRepositorio.guardar(figuraTest);
//        this.figuraRepositorio.guardar(figuraTest2);

        String hql = "SELECT f FROM Figura f WHERE f.id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id",idGenerado);
        Figura figuraObtenida = (Figura)query.getSingleResult();

        assertThat(figuraObtenida, equalTo(figuraTest));



    }

    @Test
    @Transactional
    @Rollback
    public void queSeObteganTodosLasFigurasQueEstanEnLaBaseDeDatos() {
        List<Figura> figuraMock = new ArrayList<>();
        figuraMock.add(new Figura("superman"));
        figuraMock.add(new Figura("batman"));
        figuraMock.add(new Figura("naruto"));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(0));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(1));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(2));

        List<Figura> figuras = this.figuraRepositorio.buscarTodo();

        assertThat(figuras, equalTo(figuraMock));
        assertThat(figuras.size(), equalTo(3));
        assertThat(figuras.size(), equalTo(figuraMock.size()));
    }

    @Test
    @Transactional
    @Rollback
    public void cuandoBuscoLaFiguraDeId2MeDevuelveLaFiguraCorrespondiente() {
        // Preparar datos
        List<Figura> figuraMock = new ArrayList<>();
        figuraMock.add(new Figura("superman"));
        figuraMock.add(new Figura("batman"));
        figuraMock.add(new Figura("naruto"));
        figuraMock.add(new Figura("naruto"));

        Session session = sessionFactory.getCurrentSession();
        for (Figura figura : figuraMock) {
            session.save(figura);
        }
        Long idPrimeraFigura = figuraMock.get(1).getId();

        Figura figuraObtenida = this.figuraRepositorio.buscarPorId(idPrimeraFigura);

        // Validar que la figura obtenida sea la esperada
        assertThat(figuraObtenida, equalTo(figuraMock.get(1)));
    }



    @Test
    @Transactional
    @Rollback
    public void teniendoUnaListade3ElementosAlEliminarElId2ObtengounaListade2Elementos() {
        // Preparar datos
        List<Figura> figuraMock = new ArrayList<>();
        figuraMock.add(new Figura(1L, "superman", 4000.0, "Nuevo", "figura sin caja"));
        figuraMock.add(new Figura(2L, "batman", 2000.0, "Nuevo", "figura en bolsa"));
        figuraMock.add(new Figura(3L, "naruto", 2000.0, "Nuevo", "figura sin uso"));

        Session session = sessionFactory.getCurrentSession();
        for (Figura figura : figuraMock) {
            session.save(figura);
        }
        Long idPrimeraFigura = figuraMock.get(1).getId();;

        //medotodo a testear
        this.figuraRepositorio.BorrarPorId(idPrimeraFigura);

        assertEquals(2, figuraRepositorio.buscarTodo().size());

    }

    @Test
    @Transactional
    @Rollback
    public void buscoEnBaseAlNombreDeUnaFiguraYMedevuelveUnaLista() {
        List<Figura> figuraMock = new ArrayList<>();
        figuraMock.add(new Figura(1L, "superman", 4000.0, "Nuevo", "figura sin caja"));
        figuraMock.add(new Figura(2L, "batman", 2000.0, "Nuevo", "figura en bolsa"));
        figuraMock.add(new Figura(3L, "naruto", 2000.0, "Nuevo", "figura sin uso"));

        this.sessionFactory.getCurrentSession().save(figuraMock.get(0));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(1));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(2));
        List<Figura> figuras = this.figuraRepositorio.darUnaListaBuscandoUnaPalabra("superman");

        assertNotNull(figuras);
        assertEquals(1, figuras.size());
        assertEquals("superman", figuras.get(0).getNombre());
    }

    @Test
    @Transactional
    @Rollback
    public void buscoEnBaseAlPrecioDeUnaFiguraYMedevuelveUnaLista() {
        List<Figura> figuraMock = new ArrayList<>();
        figuraMock.add(new Figura(1L, "superman", 4000.0, "Nuevo", "figura sin caja"));
        figuraMock.add(new Figura(2L, "batman", 2000.0, "Nuevo", "figura en bolsa"));
        figuraMock.add(new Figura(3L, "naruto", 2000.0, "Nuevo", "figura sin uso"));

        this.sessionFactory.getCurrentSession().save(figuraMock.get(0));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(1));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(2));

        List<Figura> figuras = this.figuraRepositorio.darUnaListaBuscandoUnaPalabra("2000");

        assertNotNull(figuras);
        assertEquals(2, figuras.size());
        assertEquals(2000.0, figuras.get(0).getPrecio());
    }

    @Test
    @Transactional
    @Rollback
    public void buscoEnBaseAlDetalleDeUnaFiguraYMedevuelveUnaLista() {
        List<Figura> figuraMock = new ArrayList<>();
        figuraMock.add(new Figura(1L, "superman", 4000.0, "Nuevo", "figura sin caja"));
        figuraMock.add(new Figura(2L, "batman", 2000.0, "Nuevo", "figura en bolsa"));
        figuraMock.add(new Figura(3L, "naruto", 2000.0, "Nuevo", "figura sin uso"));

        this.sessionFactory.getCurrentSession().save(figuraMock.get(0));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(1));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(2));
        List<Figura> figuras = this.figuraRepositorio.darUnaListaBuscandoUnaPalabra("figura");

        assertNotNull(figuras);
        assertEquals(3, figuras.size());
        assertEquals("figura sin caja", figuras.get(0).getDescripcion());
    }

    @Test
    @Transactional
    @Rollback
    public void cambioLosdatosDeUnaFiguraExistenteYalConsultarObtengoLosDatosCargados() {
        List<Figura> figuraMock = new ArrayList<>();
        figuraMock.add(new Figura(1L, "superman", 4000.0, "Nuevo", "figura sin caja"));
        figuraMock.add(new Figura(2L, "batman", 2000.0, "Nuevo", "figura en bolsa"));
        figuraMock.add(new Figura(3L, "naruto", 2000.0, "Nuevo", "figura sin uso"));

        Session session = sessionFactory.getCurrentSession();
        for (Figura figura : figuraMock) {
            session.save(figura);
        }
        Long idPrimeraFigura = figuraMock.get(1).getId();

        Figura figuraObtenida = this.figuraRepositorio.buscarPorId(idPrimeraFigura);

        Figura figuraParaActualizar = figuraMock.get(1); // Batman
        figuraParaActualizar.setNombre("nuevo batman");

        figuraRepositorio.actualizarFigura(figuraParaActualizar);

        Figura figuraActualizada = figuraRepositorio.buscarPorId(idPrimeraFigura);

        // Verifica si esta actualizado
        assertEquals("nuevo batman", figuraActualizada.getNombre());
        // y los otros parametros no
        assertEquals(2000.0, figuraActualizada.getPrecio());
        assertEquals("Nuevo", figuraActualizada.getEstado());
        assertEquals("figura en bolsa", figuraActualizada.getDescripcion());
    }


}
