package com.batch.insert.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class JpaService {

    @Autowired
    private JpaRepo repo;

    private static final String SQL = "INSERT INTO bulk (data) VALUES (?)";

    public void saveAll(List<JpaEntity> list) {
        StopWatch sw = new StopWatch();
        sw.start("saveAll");

        repo.saveAll(list);

        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + sw.getLastTaskInfo().getTimeSeconds());
        System.out.println("Rows: " + list.size());
    }

    public void save(List<JpaEntity> list) {
        StopWatch sw = new StopWatch();
        sw.start("Save");
        for (int i = 0; i < list.size(); i++) {
            repo.save(list.get(i));
        }
        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + sw.getLastTaskInfo().getTimeSeconds());
        System.out.println("Rows: " + list.size());
    }

    public void emPersist(List<JpaEntity> list) {
        StopWatch sw = new StopWatch();
        sw.start("emPersist");

        persist(list);

        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + sw.getLastTaskInfo().getTimeSeconds());
        System.out.println("Rows: " + list.size());
    }

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Transactional
    public void persist(List<JpaEntity> list) {
        EntityManager entityManager = emf.createEntityManager();
        for (int i = 0; i < list.size(); i++) {
            entityManager.persist(list.get(i));
        }
        entityManager.flush();
    }


    @Autowired
    private SessionFactory sessionFactory;


    private static final String INSERT_SQL = "INSERT INTO bulk (data) VALUES (?)";

    public void sessionFactory(List<JpaEntity> list) {
        StopWatch sw = new StopWatch();
        sw.start("sessionFactory");

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            //TODO try with persist
            session.save(list.get(i));
            if (i % 50 == 0) { //50, same as the JDBC batch size
                //flush a batch of inserts and release memory:
                session.flush();
                session.clear();
            }
        }

        tx.commit();
        session.close();

        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + sw.getLastTaskInfo().getTimeSeconds());
        System.out.println("Rows: " + list.size());
    }

    public void statelessSession(List<JpaEntity> list) {
        StopWatch sw = new StopWatch();
        sw.start("sessionFactory");

        StatelessSession session = sessionFactory.openStatelessSession();
        Transaction tx = session.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            session.insert(list.get(i));
        }

        tx.commit();
        session.close();

        sw.stop();
        System.out.println(sw.getLastTaskName() + " duration: " + sw.getLastTaskInfo().getTimeSeconds());
        System.out.println("Rows: " + list.size());
    }

}
