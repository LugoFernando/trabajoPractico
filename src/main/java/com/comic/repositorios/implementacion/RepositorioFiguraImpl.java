package com.comic.repositorios.implementacion;

import com.comic.entidades.Figura;
import com.comic.repositorios.RepositorioFigura;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioFiguraImpl implements RepositorioFigura {

    private SessionFactory sessionFactory;
    @Autowired
    public  RepositorioFiguraImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }


    @Override
    public Figura buscarFigura(Long id) {
        final Session session =sessionFactory.getCurrentSession();
        return (Figura) session.createCriteria(Figura.class)
                .add(Restrictions.eq("id",id)).uniqueResult();
    }
}
