package course_project.back.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public abstract class AbstractRepository<T> implements IRepository<T> {
    private static SessionFactory sessionFactory;

    private Session session;
    private Transaction transaction;

    public AbstractRepository() {
        configure();
    }

    private void configure() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    void startSession() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    final void closeSession() {
        transaction.commit();
        session.close();
        transaction = null;
    }

    public Session currentSession() {
        return session;
    }

    public Object runQuery(IQuery query) {
        startSession();
        Object result = query.run();
        closeSession();
        return result;
    }
}
