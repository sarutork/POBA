package com.obu.tech.poba.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
public class DatabaseConfiguration {

    @Autowired
    private Environment environment;

    @Value("${initial-database:false}")
    private String initialDatabase;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean(destroyMethod = "")
//    public DataSource dataSource() {
//        /*/ // SIT-UAT
//        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
//        jndiDataSourceLookup.setResourceRef(true);
//        DataSource dataSource = jndiDataSourceLookup.getDataSource("java:jboss/datasources/mysqlds");
//        /*/ // UIS
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
//        dataSource.setUrl(environment.getProperty("jdbc.url"));
//        dataSource.setUsername(environment.getProperty("jdbc.username"));
//        dataSource.setPassword(environment.getProperty("jdbc.password"));
//        //*/
//        return dataSource;
//    }
}
