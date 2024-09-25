package com.comic.RepositorioTest;

import com.comic.entidades.Usuario;
import com.comic.integracion.config.HibernateTestConfig;
import com.comic.repositorios.RepositorioUsuario;
import com.comic.repositorios.implementacion.RepositorioUsuarioImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
public class UsuarioRepositorioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioUsuario repositorioUsuario;

    @BeforeEach
    public void init(){
        this.repositorioUsuario=new RepositorioUsuarioImpl(sessionFactory);
    }


    @Test
    @Transactional
    @Rollback
    public void buscarUnUsuarioPorEmailYPasswordYSiExisteLoDevuelve(){
        Usuario usuario=new Usuario();
        usuario.setEmail("selgadis25@gmail.com");
        usuario.setPassword("1234");
        this.repositorioUsuario.guardar(usuario);

        String hql="FROM Usuario WHERE email = :email AND password = :password";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email","selgadis25@gmail.com");
        query.setParameter("password", "1234");
        Usuario usuarioObtenido = (Usuario)query.getSingleResult();

        assertThat(usuarioObtenido, equalTo(usuario));


    }

    @Test
    @Transactional
    @Rollback
    public void buscarUnUsuarioPorEmailYPasswordYSiNOExisteDevuelveNull(){

        String hql="FROM Usuario WHERE email = :email AND password = :password";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email","selgadis25@gmail.com");
        query.setParameter("password", "1234");

        List<Usuario> usuarios = query.getResultList();


        Usuario usuarioObtenido = usuarios.isEmpty() ? null : usuarios.get(0);

        assertThat(usuarioObtenido, equalTo(null));
    }

    @Test
    @Transactional
    @Rollback
    public void quePuedaGuardarUnUsuarioCorrecatamenteEnLaBaseDeDatos(){
        Usuario usuario=new Usuario();
        usuario.setEmail("prueba@example.com");
        usuario.setPassword("password123");
        this.repositorioUsuario.guardar(usuario);

        Session session = sessionFactory.getCurrentSession();
        Usuario usuarioGuardado = (Usuario) session.createQuery("FROM Usuario WHERE email = :email")
                .setParameter("email", "prueba@example.com")
                .uniqueResult();


        assertThat("El usuario debería haberse guardado en la base de datos.", usuarioGuardado, is(notNullValue()));
        assertThat(usuarioGuardado.getEmail(), is("prueba@example.com"));
        assertThat(usuarioGuardado.getPassword(), is("password123"));
    }

    @Test
    @Transactional
    @Rollback
    public void verificaQueSeVeanReflejadosLosCambiosAUnUsuario(){
        Usuario usuario=new Usuario();
        usuario.setEmail("prueba@example.com");
        usuario.setPassword("password123");
        this.repositorioUsuario.guardar(usuario);
        usuario.setEmail("selgadis25@gmail.com");
        this.repositorioUsuario.modificar(usuario);

        String hql="FROM Usuario WHERE email = :email";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email","selgadis25@gmail.com");
        Usuario usuarioObtenido = (Usuario)query.getSingleResult();

        assertThat(usuarioObtenido,equalTo(usuario));
    }



}
