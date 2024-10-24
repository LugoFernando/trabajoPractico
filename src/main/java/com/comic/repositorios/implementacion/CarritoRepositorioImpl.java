package com.comic.repositorios.implementacion;

import com.comic.entidades.Carrito;
import com.comic.entidades.Usuario;
import com.comic.repositorios.CarritoRepositorio;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class CarritoRepositorioImpl implements CarritoRepositorio {

    private SessionFactory sessionFactory;

    @Autowired
    public CarritoRepositorioImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    @Transactional
    public void guardar(Carrito carrito) {
        Session session = sessionFactory.getCurrentSession();
        session.save(carrito);
    }

    @Override
    @Transactional
    public Carrito obtenerCarritoPorUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Carrito WHERE usuario = :usuario"; // Consulta HQL
        return session.createQuery(hql, Carrito.class)
                .setParameter("usuario", usuario)
                .uniqueResult();
    }
}
