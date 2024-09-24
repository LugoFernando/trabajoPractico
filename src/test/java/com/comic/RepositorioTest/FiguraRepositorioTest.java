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
        List<Figura> FiguraMock = getCompletarFigura();

        List<Figura> figuras = this.figuraRepositorio.buscarTodo();

        assertThat(figuras, equalTo(FiguraMock));
        assertThat(figuras.size(), equalTo(3));
        assertThat(figuras.size(), equalTo(FiguraMock.size()));
    }

    @Test
    @Transactional
    @Rollback
    public void queSeObteganUnaFiguraPorID() {
        // Preparar datos
        List<Figura> FiguraMock = getCompletarFigura();

        // Ejecutar el método a testear
        Figura figuraObtenida = this.figuraRepositorio.buscarPorId(1L);

        // Validar que la figura obtenida sea la esperada
        assertThat(figuraObtenida, equalTo(FiguraMock.get(0)));
    }




    private List<Figura> getCompletarFigura() {
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
