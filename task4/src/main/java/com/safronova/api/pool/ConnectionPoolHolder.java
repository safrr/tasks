package com.safronova.api.pool;

import com.safronova.api.pool.impl.HikariConnectionPoolImpl;

public final class ConnectionPoolHolder {

    private static final ConnectionPool connectionPool = new HikariConnectionPoolImpl();

    private ConnectionPoolHolder() {
    }

    public static ConnectionPoolHolder getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }


    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public static class SingletonHolder {
        public static ConnectionPoolHolder HOLDER_INSTANCE = new ConnectionPoolHolder();
    }
}

