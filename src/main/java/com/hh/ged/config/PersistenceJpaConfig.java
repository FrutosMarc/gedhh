package com.hh.ged.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by romain on 02/07/2017.
 */
@Configuration
@EnableTransactionManagement
@PropertySource("file:/opt/gedhh/config/application.properties")
@EnableJpaRepositories(value = "com.hh.ged.repositories")
public class PersistenceJpaConfig {

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"com.hh.ged.entities"});

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driver-class-name"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        if (getDbType().equalsIgnoreCase("mysql")) {
            dataSource.addDataSourceProperty("cachePrepStmts", env.getProperty("cachePrepStmts"));
            dataSource.addDataSourceProperty("prepStmtCacheSize", env.getProperty("prepStmtCacheSize"));
            dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", env.getProperty("prepStmtCacheSqlLimit"));
            dataSource.addDataSourceProperty("useServerPrepStmts", env.getProperty("useServerPrepStmts"));
        }
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.search.default.directory_provider","filesystem");
        properties.setProperty("hibernate.search.default.indexBase","/opt/gedhh/indexes");

        //Wildfly 11 specific settings
//        properties.setProperty("jboss.as.jpa.providerModule","org.hibernate:5.2");
        properties.setProperty("wildfly.jpa.hibernate.search.module","org.hibernate.search.orm:main");

//        properties.setProperty("wildfly.jpa.hibernate.search.module","org.hibernate.search.orm:5.8.2.Final");

        if (getDbType().equalsIgnoreCase("mysql")) {
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        } else if (getDbType().equalsIgnoreCase("postgresql")) {
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
          //TODO to remove --> only for debug purpose
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.format_sql", "true");
        }

        return properties;
    }

    private String getDbType() {
        try {
            return env.getProperty("jdbc.url").split(":")[1];
        } catch (IndexOutOfBoundsException iob) {
            return "";
        }
    }


}
