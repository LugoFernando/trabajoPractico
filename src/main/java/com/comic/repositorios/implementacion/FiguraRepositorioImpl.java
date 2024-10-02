package com.comic.repositorios.implementacion;

import com.comic.entidades.Figura;
import com.comic.entidades.Preferencias;
import com.comic.repositorios.FiguraRepositorio;
import org.hibernate.SessionFactory;
import org.hibernate.boot.jaxb.cfg.spi.JaxbCfgEventTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.ArrayList;
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



//    @Override
//    @Transactional
//    public List<Figura> darUnaListaBuscandoUnaPalabra(String palabra) {
//        String hql = "SELECT f FROM Figura f WHERE CONCAT(f.nombre, f.precio, f.descripcion) LIKE :palabra";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
//        query.setParameter("palabra", "%" + palabra + "%");
//        return query.getResultList();
//    }

    @Override
    @Transactional
    public List<Figura> darUnaListaBuscandoUnaPalabra(String palabra) {
        String hql = "SELECT f FROM Figura f " +
                "LEFT JOIN f.preferenciasList p " +
                "WHERE CONCAT(f.nombre, f.precio, f.descripcion) LIKE :palabra " +
                "OR p = :preferencia";

        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("palabra", "%" + palabra + "%");

        // Convertir la palabra en un valor de tipo enum Preferencias, si es válido
        Preferencias preferencia = null;
        try {
            preferencia = Preferencias.valueOf(palabra.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Si no es un valor válido del enum, podemos manejar la excepción o devolver una lista vacía
            System.err.println("Preferencia no válida: " + palabra);
        }

        if (preferencia != null) {
            query.setParameter("preferencia", preferencia); // Aquí pasamos el valor del enum
        } else {
            // Si no hay preferencia válida, devolver una lista vacía o manejar el caso según convenga
            return new ArrayList<>();
        }

        return query.getResultList();
    }

}

