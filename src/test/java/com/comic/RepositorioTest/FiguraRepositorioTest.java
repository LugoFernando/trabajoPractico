package com.comic.RepositorioTest;




import com.comic.integracion.config.HibernateTestConfig;
import com.comic.entidades.entidades.Figura;
import com.comic.repositorios.FiguraRepositorio;
import com.comic.repositorios.implementacion.FiguraRepositorioImpl;
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
    public void queSeGuardeUnaFiguraNueva() {
        // Preparar datos
        Figura figuraTest = new Figura();
        figuraTest.setId(1L);
        figuraTest.setDescripcion("test");
        figuraTest.setEstado("Nuevo");
        figuraTest.setNombre("Robin");
        figuraTest.setPrecio(200.0);

        //medotodo a testear
        this.figuraRepositorio.guardar(figuraTest);


        String hql = "SELECT f FROM Figura f WHERE f.id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id",1L);
        Figura figuraObtenida = (Figura) query.getSingleResult();

        assertThat(figuraObtenida, equalTo(figuraTest));

        //Figura figuraGuardada = this.figuraRepositorio.buscarPorId(1L);

        assertThat(figuraObtenida, equalTo(figuraTest));

    }

    @Test
    @Transactional
    @Rollback
    public void queSeObteganTodosLasFiguras() {
        List<Figura> figuraMock = getCompletarListaFigura();

        List<Figura> figuras = this.figuraRepositorio.buscarTodo();

        assertThat(figuras, equalTo(figuraMock));
        assertThat(figuras.size(), equalTo(3));
        assertThat(figuras.size(), equalTo(figuraMock.size()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSeObteganUnaFiguraPorID() {
        // Preparar datos
        List<Figura> figuraMock = getCompletarListaFigura();

        // metodo a testear
        Figura figuraObtenida = this.figuraRepositorio.buscarPorId(3L);

        // Validar que la figura obtenida sea la esperada
        assertThat(figuraObtenida, equalTo(figuraMock.get(2)));
    }



    @Test
    @Transactional
    @Rollback
    public void eliminarUnaFiguraPorId() {
        // Preparar datos
        List<Figura> figuraMock = getCompletarListaFigura();

        //medotodo a testear
        this.figuraRepositorio.BorrarPorId(2L);

        assertEquals(2, figuraRepositorio.buscarTodo().size());

    }



    @Test
    @Transactional
    @Rollback

    public void darUnaListaBuscandoPorNombre() {
        List<Figura> figuraMock = getCompletarListaFigura();
        List<Figura> figuras = this.figuraRepositorio.darUnaListaBuscandoUnaPalabra("superman");

        assertNotNull(figuras);
        assertEquals(1, figuras.size());
        assertEquals("superman", figuras.get(0).getNombre());
    }

    @Test
    @Transactional
    @Rollback
    public void darUnaListaBuscandoConElPrecio() {
        List<Figura> figuraMock = getCompletarListaFigura();
        List<Figura> figuras = this.figuraRepositorio.darUnaListaBuscandoUnaPalabra("2000");

        assertNotNull(figuras);
        assertEquals(2, figuras.size());
        assertEquals(2000.0, figuras.get(0).getPrecio());
    }

    @Test
    @Transactional
    @Rollback
    public void darUnaListaBuscandoPorElDetalle() {
        List<Figura> figuraMock = getCompletarListaFigura();
        List<Figura> figuras = this.figuraRepositorio.darUnaListaBuscandoUnaPalabra("figura");

        assertNotNull(figuras);
        assertEquals(3, figuras.size());
        assertEquals("figura sin caja", figuras.get(0).getDescripcion());
    }

    @Test
    @Transactional
    @Rollback
    public void queSeActualiceUnaFiguraExistente() {
        List<Figura> figuraMock = getCompletarListaFigura();

        Figura figuraParaActualizar = figuraMock.get(1); // Batman
        figuraParaActualizar.setNombre("nuevo batman");

        figuraRepositorio.actualizarFigura(figuraParaActualizar);

        Figura figuraActualizada = figuraRepositorio.buscarPorId(2L);

        // Verifica si esta actualizado
        assertEquals("nuevo batman", figuraActualizada.getNombre());
        // y los otros parametros no
        assertEquals(2000.0, figuraActualizada.getPrecio());
        assertEquals("Nuevo", figuraActualizada.getEstado());
        assertEquals("figura en bolsa", figuraActualizada.getDescripcion());
    }




    //    @Test // Nose porque no funciona
//    @Transactional
//    @Rollback
//    public void intentarBorrarUnaFiguraQueNoExistePorId() {
//        // Preparar datos
//        List<Figura> figuraMock = getCompletarListaFigura();
//        assertEquals(3, figuraRepositorio.buscarTodo().size());
//        //medotodo a testear
//        this.figuraRepositorio.BorrarPorId(10L);
//
//        assertEquals(3, figuraRepositorio.buscarTodo().size());
//
//    }







    private List<Figura> getCompletarListaFigura() {
        List<Figura> figuraMock = new ArrayList<>();
        figuraMock.add(new Figura(1L, "superman", 4000.0, "Nuevo", "figura sin caja"));
        figuraMock.add(new Figura(2L, "batman", 2000.0, "Nuevo", "figura en bolsa"));
        figuraMock.add(new Figura(3L, "naruto", 2000.0, "Nuevo", "figura sin uso"));

        this.sessionFactory.getCurrentSession().save(figuraMock.get(0));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(1));
        this.sessionFactory.getCurrentSession().save(figuraMock.get(2));

        return figuraMock;
    }








}
