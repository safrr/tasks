package com.safronova.api.pool.impl;

import com.safronova.api.pool.ConnectionPool;
import com.safronova.api.pool.DBParameter;
import com.safronova.api.pool.DBResourceManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public final class HikariConnectionPoolImpl implements ConnectionPool {

    private HikariDataSource hikariDataSource;

    @Override
    public void init() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbResourceManager.getValue(DBParameter.DB_DATA_URL));
        config.setUsername(dbResourceManager.getValue(DBParameter.DB_USER));
        config.setPassword(dbResourceManager.getValue(DBParameter.DB_PASSWORD));
        config.addDataSourceProperty("databaseName", dbResourceManager.getValue(DBParameter.DB_NAME));
        config.addDataSourceProperty("serverName", dbResourceManager.getValue(DBParameter.DB_SERVER_NAME));
        hikariDataSource = new HikariDataSource(config);
    }

    @Override
    public HikariDataSource getSource() {
        return hikariDataSource;
    }

    @Override
    public void dispose() {
        hikariDataSource.close();
    }
}
