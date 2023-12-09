package com.habel.sb3multitenantjpa.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class JpaConfig {

    @Value("${custom.tenants}")
    private List<String> tenants;
    public static final String DEFAULT_SCHEMA = "public";

    private DriverManagerDataSource buildDatSource(DataSourceProperties dataSourceProperties, String tenant) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setSchema(tenant);
        return dataSource;
    }

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        Map<Object, Object> dataSources = new HashMap<>();
        for (String tenant : tenants) {
            var ds = buildDatSource(dataSourceProperties, tenant);
            dataSources.put(tenant, ds);
        }
        var defaultDatasource = buildDatSource(dataSourceProperties, DEFAULT_SCHEMA);
        MultitenantDataSource routingDataSource = new MultitenantDataSource();
        routingDataSource.setDefaultTargetDataSource(defaultDatasource);
        routingDataSource.setTargetDataSources(dataSources);

        return routingDataSource;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
