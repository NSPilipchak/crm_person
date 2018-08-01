package dal;

import blogic.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

import static properties.Properies.*;
import static view.MainFrame.statusBar;

/**
 * Created by hammer on 14.07.2017.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory
            = configureSessionFactory();
    private static ServiceRegistry serviceRegistry;

    private static SessionFactory configureSessionFactory() {
        Configuration configuration;

        // Настройки hibernate
        statusBar.setStatus("Конфигурирование сессии...");
        configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://" + DB_URL)
                .setProperty("hibernate.connection.username", DB_LOGIN)
                .setProperty("hibernate.connection.password", DB_PASS)
                .setProperty("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider")
                .setProperty("hibernate.c3p0.acquire_increment", "1")
                .setProperty("hibernate.c3p0.idle_test_period", "60")
                .setProperty("hibernate.c3p0.min_size", "1")
                .setProperty("hibernate.c3p0.max_size", "2")
                .setProperty("hibernate.c3p0.max_statements", "50")
                .setProperty("hibernate.c3p0.timeout", "0")
                .setProperty("hibernate.c3p0.acquireRetryAttempts", "1")
                .setProperty("hibernate.c3p0.acquireRetryDelay", "250")
                .setProperty("hibernate.connection.characterEncoding", "UTF-8")
                .setProperty("hibernate.connection.characterEncoding", "utf8")
                .setProperty("hibernate.connection.CharSet", "utf8")
                .setProperty("hibernate.connection.useUnicode", "true")
                .setProperty("hibernate.connection.autocommit", "false")
                .setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider")
                .setProperty("hibernate.cache.use_second_level_cache", "false")
                .setProperty("hibernate.cache.use_query_cache", "false")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.show_sql", "false")
                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.hbm2ddl.auto", DB_HDM2DDL)
                .addPackage("ru.miralab.db")
                .addAnnotatedClass(DistrictPassport.class)
                .addAnnotatedClass(App.class)
                .addAnnotatedClass(Cognate.class)
                .addAnnotatedClass(District.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(PersonData.class)
                .addAnnotatedClass(Smena.class)
                .addAnnotatedClass(Family.class)
                .addAnnotatedClass(Person.class);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();

        statusBar.setStatus("Сессия сконфигурированна.");

        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
