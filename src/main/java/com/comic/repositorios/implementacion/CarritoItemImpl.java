package com.comic.repositorios.implementacion;

import com.comic.entidades.CarritoItem;
import com.comic.repositorios.CarritoItemRepositorio;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class CarritoItemImpl implements CarritoItemRepositorio {

   private SessionFactory sessionFactory;

   public CarritoItemImpl (SessionFactory sessionFactory){
       this.sessionFactory=sessionFactory;
   }

    @Override
    public CarritoItem buscarItemCarrito(Long id) {
        String hql = "FROM CarritoItem c WHERE c.id = :id";
        Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (CarritoItem) query.getSingleResult();
    }

    @Override
    public List<CarritoItem> traerListaDeCarritoItem() {
        String hql = "SELECT c FROM CarritoItem c";
        Query query =this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public void guardarITemCarrito(CarritoItem carrito){
        sessionFactory.getCurrentSession().save(carrito);
    }

    @Override
    public void modificar(CarritoItem carrito) {
        sessionFactory.getCurrentSession().update(carrito);
    }


}
