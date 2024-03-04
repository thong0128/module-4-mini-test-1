package com.codegym.service;

import com.codegym.model.Posts;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class PostsService implements IPostsService{
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;
    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Posts> findAll() {
        String query = "SELECT p FROM Posts AS p";
        TypedQuery<Posts> typedQuery = entityManager.createQuery(query, Posts.class);
        return typedQuery.getResultList();
    }

    @Override
    public void save(Posts posts) {
        Transaction transaction = null;
        Posts origin;
        if (posts.getId() == 0) {
            origin = new Posts();
        } else {
            origin = findById(posts.getId());
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setName(posts.getName());
            origin.setTitle(posts.getTitle());
            origin.setDescription(posts.getDescription());
            origin.setImg(posts.getImg());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Posts findById(int id) {
        String query = "SELECT p FROM Posts AS p WHERE p.id = :id";
        TypedQuery<Posts> typedQuery = entityManager.createQuery(query, Posts.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    @Override
    public void remove(int id) {
        entityManager.getTransaction().begin();
        String queryStr = "DELETE FROM Posts AS p WHERE p.id = :id";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("id", id);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Posts posts) {
        entityManager.getTransaction().begin();
        String queryStr = "UPDATE Posts AS p SET p.title = :title, p.img = :img, p.name = :name, p.description = :description WHERE p.id = :id";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("id", posts.getId());
        query.setParameter("title", posts.getTitle());
        query.setParameter("img", posts.getImg());
        query.setParameter("description", posts.getDescription());
        query.setParameter("name", posts.getName());

        query.executeUpdate();
        entityManager.getTransaction().commit();
    }
}
