package me.drawethree.ultraprisoncore.history.api;

import me.drawethree.ultraprisoncore.UltraPrisonModule;
import me.drawethree.ultraprisoncore.history.UltraPrisonHistory;
import me.drawethree.ultraprisoncore.history.model.HistoryLine;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class UltraPrisonHistoryAPIImpl implements UltraPrisonHistoryAPI {

	private final UltraPrisonHistory plugin;

	public UltraPrisonHistoryAPIImpl(UltraPrisonHistory plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<HistoryLine> getPlayerHistory(OfflinePlayer player) {
		return this.plugin.getHistoryManager().getPlayerHistory(player);
	}

	@Override
	public void createHistoryLine(OfflinePlayer player, UltraPrisonModule module, String context) {
		this.plugin.getHistoryManager().createPlayerHistoryLine(player, module, context);
	}
}