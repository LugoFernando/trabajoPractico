package com.comic.repositorios.implementacion;

import com.comic.entidades.Compra;
import com.comic.entidades.Figura;
import com.comic.entidades.Usuario;
import com.comic.repositorios.CompraRepositorio;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository("CompraRepositorio")
public class CompraRepositorioImpl implements CompraRepositorio {

    private SessionFactory sessionFactory;

    @Autowired
    public CompraRepositorioImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    @Transactional
    public List<Compra> buscarPorUsuario(Usuario usuario) {
        String hql = "SELECT c FROM Compra c WHERE c.usuario = ?1";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(1, usuario);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void guardar(Compra compra) {
        this.sessionFactory.getCurrentSession().save(compra);
    }

    @Override
    @Transactional
    public Compra buscarPorId(Long id) {
        String hql = "SELECT c FROM Compra c WHERE c.id = ?1";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(1, id);
        return (Compra) query.getSingleResult();
    }


    @Override
    @Transactional
        public List<Figura> obtenerFigurasRelacionadas(Usuario usuario) {
            try (Session session = sessionFactory.openSession()) {
                org.hibernate.Query<Figura> query = session.createQuery(
                        "SELECT f FROM Figura f JOIN f.usuario u JOIN u.compras c " +
                                "WHERE u.id = :usuarioId AND (" +
                                "f.nombre LIKE :detalle OR f.descripcion LIKE :detalle)", Figura.class);
                query.setParameter("usuarioId", usuario.getId());
                query.setParameter("detalle", "%detalle%"); // Cambia "detalle" por la palabra clave o filtro que quieras
                query.setMaxResults(3); // Limitar a 3 resultados
                return query.getResultList();
            }
        }
}


