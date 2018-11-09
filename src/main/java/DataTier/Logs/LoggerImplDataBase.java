package DataTier.Logs;

import DataTier.DataModels.Event;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class LoggerImplDataBase implements Logger {

    private SessionFactory factory;

    public LoggerImplDataBase(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void logEvent(Event e) {

        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

    }
}
