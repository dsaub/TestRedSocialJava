package me.elordenador.redsocialpobreza;

import me.elordenador.redsocialpobreza.models.Permission;
import me.elordenador.redsocialpobreza.models.Token;
import me.elordenador.redsocialpobreza.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

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
                .addAnnotatedClass(Permission.class)
                .addAnnotatedClass(Token.class)
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
            System.out.println("Buscando si el usuario 'admin' existe");
            Query<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", "admin");

            User user = query.uniqueResult();
            if (user != null) {
                System.out.println("Usuario encontrado...");
            } else {
                System.out.println("Usuario no encontrado... Creando");
                User user1 = new User("admin", "admin");
                user1.setInitialized(false);
                session.persist(user1);
                session.persist(new Permission(user1 ,"*"));
                System.out.println("USERNAME: admin");
                System.out.println("PASSWORD: admin");
                System.out.println("Please enter to change your password.");

            }
        });
    }
}
