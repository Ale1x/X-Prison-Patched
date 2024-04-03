package dev.passarelli.xprison.ranks.repo.impl;

import dev.passarelli.xprison.database.SQLDatabase;
import dev.passarelli.xprison.ranks.repo.RanksRepository;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RanksRepositoryImpl implements RanksRepository {

	private static final String TABLE_NAME = "UltraPrison_Ranks";
	private static final String RANKS_UUID_COLNAME = "UUID";
	private static final String RANKS_RANK_COLNAME = "id_rank";

	private final SQLDatabase database;

	public RanksRepositoryImpl(SQLDatabase database) {
		this.database = database;
	}

	@Override
	public int getPlayerRank(OfflinePlayer player) {
		try (Connection con = this.database.getConnection(); PreparedStatement statement = database.prepareStatement(con, "SELECT * FROM " + TABLE_NAME + " WHERE " + RANKS_UUID_COLNAME + "=?")) {
			statement.setString(1, player.getUniqueId().toString());
			try (ResultSet set = statement.executeQuery()) {
				if (set.next()) {
					return set.getInt(RANKS_RANK_COLNAME);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void updateRank(OfflinePlayer player, int newRank) {
		this.database.executeSql("UPDATE " + TABLE_NAME + " SET " + RANKS_RANK_COLNAME + "=? WHERE " + RANKS_UUID_COLNAME + "=?", newRank, player.getUniqueId().toString());
	}

	@Override
	public void addIntoRanks(OfflinePlayer player) {
		String sql = "INSERT INTO " + TABLE_NAME + "(UUID, id_rank) VALUES(?, ?) ON CONFLICT DO NOTHING";
		this.database.executeSql(sql, player.getUniqueId().toString(), 0);
	}


	@Override
	public void createTables() {
		this.database.executeSql("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(UUID varchar(36) NOT NULL UNIQUE, id_rank int, primary key (UUID))");
	}

	@Override
	public void clearTableData() {
		this.database.executeSqlAsync("DELETE FROM " + TABLE_NAME);
	}

}
