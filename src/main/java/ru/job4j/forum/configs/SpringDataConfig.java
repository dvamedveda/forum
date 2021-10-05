package ru.job4j.forum.configs;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Конфигурация доступа к данным.
 */
@Configuration
@EnableJpaRepositories("ru.job4j.forum.repositories")
@EnableTransactionManagement
@PropertySource("classpath:/application.properties")
public class SpringDataConfig {

    /**
     * Источник данных (бд).
     *
     * @param url      координаты бд.
     * @param username имя пользователя.
     * @param password пароль пользователя.
     * @param driver   имя класса драйвера бд.
     * @return объект источника данных.
     */
    @Bean
    @Qualifier("mainDataSource")
    public DataSource dataSource(@Value("${spring.datasource.url}") String url,
                                 @Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password,
                                 @Value("${spring.datasource.driver-class-name}") String driver) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Фабрика объектов EntityManager.
     *
     * @param dataSource источник данных.
     * @return объект фабрики.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("mainDataSource") DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean entityFactory = new LocalContainerEntityManagerFactoryBean();
        entityFactory.setJpaVendorAdapter(vendorAdapter);
        entityFactory.setPackagesToScan("ru.job4j.forum");
        entityFactory.setDataSource(dataSource);
        return entityFactory;
    }

    /**
     * Менеджер транзакций для операций с бд.
     *
     * @param entityManagerFactory объект фабрики EntityManager.
     * @return объект менеджера транзакций.
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}

