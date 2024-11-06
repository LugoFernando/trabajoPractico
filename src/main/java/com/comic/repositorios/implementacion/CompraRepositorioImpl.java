package com.comic.repositorios.implementacion;

import com.comic.entidades.Dto.Compra;
import com.comic.repositorios.CompraRepositorio;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository ("CompraRepositorio")
public class CompraRepositorioImpl implements CompraRepositorio {

    private SessionFactory sessionFactory;

    @Autowired
    public CompraRepositorioImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    @Transactional
    public List<Compra> buscarTodasLasCompras() {
        String hql = "SELECT c FROM Compra c";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void guardar(Compra compra) {
        Session session = sessionFactory.getCurrentSession();
        session.save(compra);
    }


}
