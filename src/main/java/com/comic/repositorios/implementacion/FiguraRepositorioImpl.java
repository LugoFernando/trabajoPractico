package com.comic.repositorios.implementacion;

import com.comic.entidades.Figura;
import com.comic.repositorios.FiguraRepositorio;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.List;

@Repository ("FiguraRepositorio")
public class FiguraRepositorioImpl implements FiguraRepositorio {


    private SessionFactory sessionFactory;

    @Autowired
    public FiguraRepositorioImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    @Transactional
    public List<Figura> buscarTodo() {
        String hql = "SELECT f FROM Figura f";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void guardar(Figura figura) {
        this.sessionFactory.getCurrentSession().save(figura);
    }



    @Override
    @Transactional
    public Figura buscarPorId(Long id) {
        String hql = "SELECT f FROM Figura f WHERE f.id = ?1";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(1, id);
        return (Figura) query.getSingleResult();
    }

    @Override
    @Transactional
    public void BorrarPorId(Long id) {
        Figura figura = buscarPorId(id);
        if (figura != null) {
            this.sessionFactory.getCurrentSession().delete(figura);
        }
    }



    @Override
    @Transactional
    public List<Figura> darUnaListaBuscandoUnaPalabra(String palabra) {
        String hql = "SELECT f FROM Figura f WHERE CONCAT(f.nombre, f.precio, f.descripcion) LIKE :palabra";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("palabra", "%" + palabra + "%");
        return query.getResultList();
    }



}
