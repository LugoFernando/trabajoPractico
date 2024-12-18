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

//    @Override
//    public List<Figura> buscarFiguraPorIDUsurio(Long id) {
//        String hql = "SELECT f FROM Figura f WHERE f.usuario.id = :usuarioId";
//        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
//        query.setParameter("usuarioId", id);
//        return query.getResultList();
//    }

    @Override
    @Transactional
    public List<Figura> buscarFiguraPorIDUsurio(Long usuarioId) {
        String hql = "FROM Figura f WHERE f.usuario.id = :usuarioId AND f.activo = true";
        return this.sessionFactory
                .getCurrentSession()
                .createQuery(hql, Figura.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
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
    public void actualizarFigura(Figura figura) {
        String hql = "UPDATE Figura SET nombre = :nombre, precio = :precio, estado = :estado, descripcion = :descripcion, imagen = :imagen WHERE id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", figura.getNombre());
        query.setParameter("precio", figura.getPrecio());
        query.setParameter("estado", figura.getEstado());
        query.setParameter("descripcion", figura.getDescripcion());
        query.setParameter("imagen", figura.getImagen()); // Agregado para la imagen
        query.setParameter("id", figura.getId());
        query.executeUpdate();
    }


//    @Override
//    @Transactional
//    public List<Figura> darUnaListaBuscandoUnaPalabra(String palabra) {
//        String hql = "SELECT f FROM Figura f " +
//                "LEFT JOIN f.preferenciasList pref " +
//                "WHERE f.nombre LIKE :palabra " +
//                "OR f.precio LIKE :palabra " +
//                "OR f.descripcion LIKE :palabra " +
//                "OR pref LIKE :palabra";
//
//        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
//        query.setParameter("palabra", "%" + palabra + "%");
//        return query.getResultList();
//    }

    @Override
    @Transactional
    public List<Figura> darUnaListaBuscandoUnaPalabra(String palabra) {
        String hql = "SELECT f FROM Figura f " +
                "LEFT JOIN f.preferenciasList pref " +
                "WHERE f.activo = true AND (" +
                "f.nombre LIKE :palabra " +
                "OR f.precio LIKE :palabra " +
                "OR f.descripcion LIKE :palabra " +
                "OR pref LIKE :palabra)";

        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("palabra", "%" + palabra + "%");
        return query.getResultList();
    }

    @Override
    @Transactional
    public void actualizar(Figura figura) {
        this.sessionFactory.getCurrentSession().update(figura);
    }

    @Override
    public List<Figura> listarFigurasActivas() {
        String hql = "FROM Figura f WHERE f.activo = true";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Figura.class)
                .getResultList();
    }



}
