package ru.divol13.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.divol13.pft.mantis.model.UserData;
import ru.divol13.pft.mantis.model.Users;

import java.util.List;

public class DbHelper {
    private SessionFactory sessionFactory;

    public DbHelper() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public Users users() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<UserData> result = session.createQuery("from UserData where username != 'administrator'").list();
        session.getTransaction().commit();

        session.close();
        return new Users(result);
    }
}
