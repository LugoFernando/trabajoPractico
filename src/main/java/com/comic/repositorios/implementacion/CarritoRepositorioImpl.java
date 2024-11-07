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

    @Override //hacer
    @Transactional
    public void guardar(Carrito carrito) {
        Session session = sessionFactory.getCurrentSession();
        session.save(carrito);
    }

//    @Override
//    @Transactional
//    public Carrito obtenerCarritoPorUsuario(Usuario usuario) {
//        Session session = sessionFactory.getCurrentSession();
//        String hql = "FROM Carrito WHERE usuario = :usuario"; // Consulta HQL
//        return session.createQuery(hql, Carrito.class)
//                .setParameter("usuario", usuario)
//                .uniqueResult();
//    }

//    @Override //hacer
//    @Transactional
//    public Carrito obtenerCarritoPorUsuario(Usuario usuario) {
//        Session session = sessionFactory.getCurrentSession();
//        String hql = "FROM Carrito WHERE usuario.id = :usuarioId"; // Cambiar a usuario.id para comparar por ID
//        return session.createQuery(hql, Carrito.class)
//                .setParameter("usuarioId", usuario.getId()) // Pasar el ID del usuario
//                .uniqueResult();
//    }

    @Override
    @Transactional
    public Carrito obtenerCarritoPorUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT u.carrito FROM Usuario u WHERE u.id = :usuarioId";
        return session.createQuery(hql, Carrito.class)
                .setParameter("usuarioId", usuario.getId())
                .uniqueResult();
    }



    @Override
    @Transactional//hacer
    public void modificarCarrito(Carrito carrito) {
        sessionFactory.getCurrentSession().saveOrUpdate(carrito);
    }

    @Override
    @Transactional
    public void eliminarCarritoDeLaBD(Carrito carrito){
        sessionFactory.getCurrentSession().delete(carrito);
    }



}
