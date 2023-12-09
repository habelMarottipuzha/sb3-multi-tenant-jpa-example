package com.habel.sb3multitenantjpa.config;

import com.habel.sb3multitenantjpa.entity.common.ProfileRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.habel.sb3multitenantjpa.entity.tenant",
        entityManagerFactoryRef = "tenantEntityManagerFactory",
        transactionManagerRef = "tenantTransactionManager"
)
@RequiredArgsConstructor
public class TenantDataSourceConfig extends BaseDataSourceConfig {

    @Value("${custom.tenants}")
    private List<String> tenants;

    private final ProfileRepository repository;

    private DataSource buildDatSource(HikariDataSource originalDataSource, String tenant) {
        HikariConfig config = new HikariConfig();

        // Copy properties from the original HikariDataSource to the new HikariConfig
        config.setJdbcUrl(originalDataSource.getJdbcUrl());
        config.setUsername(originalDataSource.getUsername());
        config.setPassword(originalDataSource.getPassword());
        config.setDriverClassName(originalDataSource.getDriverClassName());
        config.setSchema(tenant);

        // Copy other Hikari-specific configurations
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(2);
        // Add more configurations as needed

        var newDs = new HikariDataSource(config);

//        newDs.setSchema(tenant);
        return newDs;
    }


    @Bean(name = "tenantBaseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.tenant")
    public DataSource tenantBaseDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "tenantDataSource")
    public DataSource tenantDataSource(@Qualifier("tenantBaseDataSource") DataSource hikariDataSource) {

        Map<Object, Object> dataSources = new HashMap<>();
        //TODO fetch tenant info from database
        System.out.println(repository.findAll());
        for (String tenant : tenants) {
            var ds = buildDatSource((HikariDataSource) hikariDataSource, tenant);
            dataSources.put(tenant, ds);
        }
        MultitenantDataSource routingDataSource = new MultitenantDataSource();
        routingDataSource.setDefaultTargetDataSource(hikariDataSource);
        routingDataSource.setTargetDataSources(dataSources);

        return routingDataSource;
    }

    @Bean(name = "tenantEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    tenantEntityManagerFactory(EntityManagerFactoryBuilder builder,
                               @Qualifier("tenantDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.habel.sb3multitenantjpa.entity.tenant")
                .persistenceUnit("tenant")
                .properties(hibernateProperties())
                .build();
    }

    @Bean(name = "tenantTransactionManager")
    public PlatformTransactionManager tenantTransactionManager(
            @Qualifier("tenantEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}