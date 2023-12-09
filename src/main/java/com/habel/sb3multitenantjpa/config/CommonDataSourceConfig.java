package com.habel.sb3multitenantjpa.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.habel.sb3multitenantjpa.entity.common",
        entityManagerFactoryRef = "commonEntityManagerFactory",
        transactionManagerRef = "commonTransactionManager"
)
public class CommonDataSourceConfig extends BaseDataSourceConfig {

    @Primary
    @Bean(name = "commonDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.common")
    public DataSource commonDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "commonEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    commonEntityManagerFactory(EntityManagerFactoryBuilder builder,
                               @Qualifier("commonDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.habel.sb3multitenantjpa.entity.common")
                .persistenceUnit("common")
                .properties(hibernateProperties())
                .build();
    }

    @Primary
    @Bean(name = "commonTransactionManager")
    public PlatformTransactionManager commonTransactionManager(
            @Qualifier("commonEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}