package DataTier.Logs;

import DataTier.DataModels.Event;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

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

    @Override
    public List<Event> getEvents(int limit) {
        Transaction tx = null;
        List<Event> result = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Event E ORDER BY E.date DESC");
            q.setMaxResults(limit);
            result = q.list();
            tx.commit();

        } catch (HibernateException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return result;
    }
}
