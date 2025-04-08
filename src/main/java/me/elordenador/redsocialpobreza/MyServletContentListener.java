package me.elordenador.redsocialpobreza;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import me.elordenador.redsocialpobreza.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static org.hibernate.cfg.AvailableSettings.*;

public class MyServletContentListener implements ServletContextListener {
    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
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

    }
}
