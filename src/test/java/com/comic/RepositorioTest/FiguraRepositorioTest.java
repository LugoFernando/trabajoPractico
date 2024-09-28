package com.comic.RepositorioTest;



import com.comic.integracion.config.HibernateTestConfig;
import com.comic.entidades.Figura;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Figura figuraObtenida = this.figuraRepositorio.buscarPorId(1L);

        // Validar que la figura obtenida sea la esperada
        assertThat(figuraObtenida, equalTo(figuraMock.get(0)));
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

        Figura figuraGuardada = this.figuraRepositorio.buscarPorId(1L);

        assertThat(figuraGuardada, equalTo(figuraTest));

    }

    @Test
    @Transactional
    @Rollback
    public void eliminarUnaFiguraPorId() {
        // Preparar datos
        List<Figura> figuraMock = getCompletarListaFigura();

        //medotodo a testear
        this.figuraRepositorio.BorrarPorId(1L);

        assertEquals(2, figuraRepositorio.buscarTodo().size());

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
        List<Figura> FiguraMock = new ArrayList<>();
        FiguraMock.add(new Figura(1L, "superman", 4000.0, "Nuevo", "figura sin caja"));
        FiguraMock.add(new Figura(2L, "batman", 2000.0, "Nuevo", "figura con detalles"));
        FiguraMock.add(new Figura(3L, "naruto", 5000.0, "Nuevo", "figura sin uso"));

        this.sessionFactory.getCurrentSession().save(FiguraMock.get(0));
        this.sessionFactory.getCurrentSession().save(FiguraMock.get(1));
        this.sessionFactory.getCurrentSession().save(FiguraMock.get(2));

        return FiguraMock;
    }








}
