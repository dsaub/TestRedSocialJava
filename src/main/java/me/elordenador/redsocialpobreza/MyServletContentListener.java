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

    }
}
