package com.comic.presentacion;


import com.comic.entidades.Figura;
import com.comic.repositorios.FiguraRepositorio;
import com.comic.repositorios.implementacion.FiguraRepositorioImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;


import javax.transaction.Transactional;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class FiguraRepositorioTest {

//    @Autowired
//    private SessionFactory sessionFactory;
//
//    private FiguraRepositorio figuraRepositorio;
//
//    @BeforeEach
//    public void init() {
//        this.figuraRepositorio = new FiguraRepositorioImpl(this.sessionFactory);
//    }
//
//
//
//
//
//    @Test
//    @Transactional
//    @Rollback
//    public void queSeObteganTodosLosVehiculos() {
//        List<Figura> carsMock = getCompleteCars();
//
//        List<Figura> cars = this.figuraRepositorio.buscarTodo();
//
//        assertThat(cars, equalTo(carsMock));
//        assertThat(cars.size(), equalTo(3));
//        assertThat(cars.size(), equalTo(carsMock.size()));
//    }
//
//    private List<Figura> getCompleteCars()
//    {
//
//        List<Figura> carsMock = new ArrayList<>();
//        carsMock.add(new Figura());
//        carsMock.add(new Figura());
//        carsMock.add(new Figura());
//
//        this.sessionFactory.getCurrentSession().save(carsMock.get(0));
//        this.sessionFactory.getCurrentSession().save(carsMock.get(1));
//        this.sessionFactory.getCurrentSession().save(carsMock.get(2));
//
//        return carsMock;
//    }
//

}
