package me.elordenador.redsocialpobreza;

import me.elordenador.redsocialpobreza.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static org.hibernate.cfg.JdbcSettings.*;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;

public class DBManager {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            initialize();
        }
        return sessionFactory;
    }
    public static void initialize() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperty(URL, System.getenv("JDBC_URL"))
                .setProperty(USER, System.getenv("JDBC_USER"))
                .setProperty(PASS, System.getenv("JDBC_PASS"))
                .setProperty("hibernate.agroal.maxSize", 20)
                .setProperty(SHOW_SQL ,true)
                .setProperty(FORMAT_SQL, true)
                .setProperty(HIGHLIGHT_SQL, true)
                .buildSessionFactory();

        sessionFactory.getSchemaManager().exportMappedObjects(true);
        sessionFactory.inTransaction(session -> {
            session.persist(new User("admin", "admin"));
        });
    }
}
