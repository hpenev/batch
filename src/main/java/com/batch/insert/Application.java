package com.batch.insert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/postgres?reWriteBatchedInserts=true");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("Secret123");
        return driverManagerDataSource;
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPersistenceUnitName("jpaData");
        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(hibernateProperties());
        factoryBean.setPackagesToScan(new String[]{"com.batch.insert"});
        return factoryBean;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.cache.use_second_level_cache", "false");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("properties.hibernate.jdbc.batch_size", "50");
        properties.put("properties.hibernate.order_inserts", "true");
        properties.put("properties.hibernate.order_updates", "true");
        properties.put("properties.hibernate.batch_versioned_data", "true");
        properties.put("properties.hibernate.format_sql", "true");
        return properties;
    }

    @Bean(name = {"sessionFactory"})
    @Primary
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean builder = new LocalSessionFactoryBean();
        builder.setDataSource(dataSource());
        builder.setPackagesToScan("com.batch.insert");
        builder.setHibernateProperties(hibernateProperties());

        return builder;
    }

    @Bean
    @Primary
    public HibernateTransactionManager transactionManager() {
        final HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }
}
