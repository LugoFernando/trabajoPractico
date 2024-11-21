package com.comic.repositorios.implementacion;

import com.comic.entidades.Dto.Compra;
import com.comic.repositorios.CompraRepositorio;
import org.hibernate.Hibernate;
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
        List<Compra> resultList =  (List<Compra>)query.getResultList();

        for (Compra o : resultList) {
            Hibernate.initialize(o.getListaDePedidosAcomprar());
        }
        return resultList;
    }

    @Override
    @Transactional
    public void guardar(Compra compra) {
        Session session = sessionFactory.getCurrentSession();
        session.save(compra);
    }

    @Override
    @Transactional
    public Compra buscarCompraPorId(Long id) {

        Compra compra = (Compra) sessionFactory.getCurrentSession()
                .createQuery("FROM Compra c WHERE c.id = :id")
                .setParameter("id", id)
                .uniqueResult();

        if (compra != null) {
            Hibernate.initialize(compra.getListaDePedidosAcomprar());
        }

        return compra;
    }


}
