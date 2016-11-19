package com.eboy.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AriAppDatabaseConfiguration {

    @Value("${ariapp.db.url}")
    private String dbUrl;

    @Value("${ariapp.db.dbname}")
    private String dbName;

    @Value("${ariapp.db.username}")
    private String dbUser;

    @Value("${ariapp.db.password}")
    private String dbPassword;

    @Value("${ariapp.db.driverclass}")
    private String driverClassName;

    @Bean
    public DataSource mySQLDataSource() {

        DataSource datasource = DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(dbUrl + dbName)
                .username(dbUser)
                .password(dbPassword)
                .build();

        return datasource;
    }
}
