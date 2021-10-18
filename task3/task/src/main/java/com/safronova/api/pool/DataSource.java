package com.safronova.api.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static DataSource dataSource;

    private DataSource() {
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres?currentSchema=street_level_crimes");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.addDataSourceProperty("databaseName", "postgres");
        config.addDataSourceProperty("serverName", "127.0.0.1");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private HikariDataSource getDataSource() {
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            dataSource = new DataSource();
        }
        return dataSource.getDataSource().getConnection();
    }
}
