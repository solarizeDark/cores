package ru.fedusiv.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static HikariDataSource hikariDataSource() {

        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src\\main\\resources\\db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver"));
        hikariConfig.setUsername(properties.getProperty("db.username"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.max-pool-size")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return dataSource;
    }

    public static JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(hikariDataSource());
        SqlExceptionsTranslator sqlViolation = new SqlExceptionsTranslator();
        sqlViolation.setDataSource(hikariDataSource());
        jdbcTemplate.setExceptionTranslator(sqlViolation);

        return jdbcTemplate;
    }

    public static void main(String[] args) {
        PeopleJdbcRepository repository = new PeopleJdbcRepository(jdbcTemplate());

        Person p = Person.builder()
                .surname("Ili")
                .patronymic("Olo")
                .age(25)
                .build();

        repository.save(p);
    }

}
