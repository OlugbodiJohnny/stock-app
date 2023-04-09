package com.example.stockapp2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Configuration
public class DbConfiguration {

    @Value(value = "${spring.datasource.username}")
    private String username;

    @Value(value = "${spring.datasource.password}")
    private String password;

    @Value(value = "${spring.datasource.url}")
    private String url;

    @Bean
    public DataSource getDataSource() throws URISyntaxException {
        String dbURL = System.getenv("DATABASE_URL");
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

        if(dbURL != null) {
            log.info("Starting up production Database.........");
            // production environment
            URI dbUri = new URI(dbURL);

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
            dataSourceBuilder.url(dbUrl);
            dataSourceBuilder.username(username);
            dataSourceBuilder.password(password);
        }else{
            log.info("Starting up local Database............");
            log.info("database url is : {}",url);
            // local environment
            dataSourceBuilder.url(url);
            if(!username.equals("")) dataSourceBuilder.username(username);
            if(!password.equals("")) dataSourceBuilder.password(password);

        }
        return dataSourceBuilder.build();
    }

}
