package dev.passarelli.xprison.tokens.repo.impl;

import dev.passarelli.xprison.database.SQLDatabase;
import dev.passarelli.xprison.tokens.repo.TokensRepository;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class TokensRepositoryImpl implements TokensRepository {

    private static final String TABLE_NAME_TOKENS = "UltraPrison_Tokens";

    private static final String TOKENS_UUID_COLNAME = "UUID";
    private static final String TOKENS_TOKENS_COLNAME = "Tokens";

    private final SQLDatabase database;

    public TokensRepositoryImpl(SQLDatabase database) {
        this.database = database;
    }

    @Override
    public long getPlayerTokens(OfflinePlayer p) {
        try (Connection con = this.database.getConnection(); PreparedStatement statement = database.prepareStatement(con,"SELECT * FROM " + TABLE_NAME_TOKENS + " WHERE " + TOKENS_UUID_COLNAME + "=?")) {
            statement.setString(1, p.getUniqueId().toString());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return set.getLong(TOKENS_TOKENS_COLNAME);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void updateTokens(OfflinePlayer p, long amount) {
        this.database.executeSql("UPDATE " + TABLE_NAME_TOKENS + " SET " + TOKENS_TOKENS_COLNAME + "=? WHERE " + TOKENS_UUID_COLNAME + "=?", amount, p.getUniqueId().toString());
    }

    @Override
    public Map<UUID, Long> getTopTokens(int amountOfRecords) {
        Map<UUID, Long> top10Tokens = new LinkedHashMap<>();
        try (Connection con = this.database.getConnection(); PreparedStatement statement = database.prepareStatement(con, "SELECT " + TOKENS_UUID_COLNAME + "," + TOKENS_TOKENS_COLNAME + " FROM " + TABLE_NAME_TOKENS + " ORDER BY " + TOKENS_TOKENS_COLNAME + " DESC LIMIT " + amountOfRecords); ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                top10Tokens.put(UUID.fromString(set.getString(TOKENS_UUID_COLNAME)), set.getLong(TOKENS_TOKENS_COLNAME));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top10Tokens;
    }

    @Override
    public void addIntoTokens(OfflinePlayer player, long startingTokens) {
        String sql;
        Object[] params = switch (database.getDatabaseType()) {
            case SQLITE -> {
                sql = "INSERT OR REPLACE INTO " + TABLE_NAME_TOKENS + " (" + TOKENS_UUID_COLNAME + ", " + TOKENS_TOKENS_COLNAME + ") VALUES (?, ?)";
                yield new Object[]{player.getUniqueId().toString(), startingTokens};
            }
            case MYSQL -> {
                sql = "INSERT INTO " + TABLE_NAME_TOKENS + " (" + TOKENS_UUID_COLNAME + ", " + TOKENS_TOKENS_COLNAME + ") VALUES (?, ?) ON DUPLICATE KEY UPDATE " + TOKENS_TOKENS_COLNAME + " = ?";
                yield new Object[]{player.getUniqueId().toString(), startingTokens, startingTokens};
            }
            case POSTGRESQL -> {
                sql = "INSERT INTO " + TABLE_NAME_TOKENS + " (" + TOKENS_UUID_COLNAME + ", " + TOKENS_TOKENS_COLNAME + ") VALUES (?, ?) ON CONFLICT (" + TOKENS_UUID_COLNAME + ") DO UPDATE SET " + TOKENS_TOKENS_COLNAME + " = ?";
                yield new Object[]{player.getUniqueId().toString(), startingTokens, startingTokens};
            }
            default ->
                    throw new IllegalStateException("Tipo di database non supportato: " + database.getDatabaseType());
        };

        try {
            this.database.executeSql(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void createTables() {
        this.database.executeSql("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TOKENS + "(UUID varchar(36) NOT NULL UNIQUE, Tokens bigint, primary key (UUID))");
    }

    @Override
    public void clearTableData() {
        this.database.executeSqlAsync("DELETE FROM " + TABLE_NAME_TOKENS);
    }
}
