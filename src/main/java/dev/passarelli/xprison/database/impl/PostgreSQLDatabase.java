package dev.passarelli.xprison.database.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.passarelli.xprison.XPrison;
import dev.passarelli.xprison.database.PooledSQLDatabase;
import dev.passarelli.xprison.database.model.ConnectionProperties;
import dev.passarelli.xprison.database.model.DatabaseCredentials;
import dev.passarelli.xprison.database.model.SQLDatabaseType;

public final class PostgreSQLDatabase extends PooledSQLDatabase {

    private final DatabaseCredentials credentials;
    private final ConnectionProperties connectionProperties;

    public PostgreSQLDatabase(XPrison parent, DatabaseCredentials credentials, ConnectionProperties connectionProperties) {
        super(parent);
        this.connectionProperties = connectionProperties;
        this.credentials = credentials;
    }

    @Override
    public void connect() {
        final HikariConfig hikari = new HikariConfig();

        hikari.setPoolName("xprison-" + POOL_COUNTER.getAndIncrement());

        this.applyCredentials(hikari);
        this.applyConnectionProperties(hikari, connectionProperties);
        this.addDefaultDataSourceProperties(hikari);
        this.hikari = new HikariDataSource(hikari);
    }

    private void applyCredentials(HikariConfig hikari) {

        hikari.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        hikari.addDataSourceProperty("serverName", credentials.getHost());
        hikari.addDataSourceProperty("portNumber", credentials.getPort());
        hikari.addDataSourceProperty("databaseName", credentials.getDatabaseName());
        hikari.addDataSourceProperty("user", credentials.getUserName());
        hikari.addDataSourceProperty("password", credentials.getPassword());
    }
    private void applyConnectionProperties(HikariConfig hikari, ConnectionProperties connectionProperties) {

        hikari.setConnectionTimeout(connectionProperties.getConnectionTimeout());
        hikari.setIdleTimeout(connectionProperties.getIdleTimeout());
        hikari.setKeepaliveTime(connectionProperties.getKeepAliveTime());
        hikari.setMaxLifetime(connectionProperties.getMaxLifetime());
        hikari.setMinimumIdle(connectionProperties.getMinimumIdle());
        hikari.setMaximumPoolSize(connectionProperties.getMaximumPoolSize());
        hikari.setLeakDetectionThreshold(connectionProperties.getLeakDetectionThreshold());
        hikari.setConnectionTestQuery(connectionProperties.getTestQuery());
    }

    private void addDefaultDataSourceProperties(HikariConfig hikari) {
//        hikari.addDataSourceProperty("cachePrepStmts", true);
//        hikari.addDataSourceProperty("prepStmtCacheSize", 250);
//        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
//        hikari.addDataSourceProperty("useServerPrepStmts", true);
//        hikari.addDataSourceProperty("useLocalSessionState", true);
//        hikari.addDataSourceProperty("rewriteBatchedStatements", true);
//        hikari.addDataSourceProperty("cacheResultSetMetadata", true);
//        hikari.addDataSourceProperty("cacheServerConfiguration", true);
//        hikari.addDataSourceProperty("elideSetAutoCommits", true);
    }

    @Override
    public SQLDatabaseType getDatabaseType() {
        return SQLDatabaseType.POSTGRESQL;
    }
}