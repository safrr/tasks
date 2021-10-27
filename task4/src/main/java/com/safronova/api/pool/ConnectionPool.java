package com.safronova.api.pool;

import javax.sql.DataSource;

public interface ConnectionPool {
    void init();

    DataSource getSource();

    void dispose();
}
