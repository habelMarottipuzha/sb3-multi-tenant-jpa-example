package com.habel.sb3multitenantjpa.config;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseDataSourceConfig {


    protected Map<String, Object> hibernateProperties() {
        Map<String, Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.put("hibernate.show_sql", "true");
        return hibernateProperties;
    }
}
