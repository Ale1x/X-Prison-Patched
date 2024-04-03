package dev.passarelli.xprison.ranks.api;

import dev.passarelli.xprison.ranks.manager.RanksManager;
import dev.passarelli.xprison.ranks.model.Rank;
import org.bukkit.entity.Player;

import java.util.Optional;

public final class XPrisonRanksAPIImpl implements XPrisonRanksAPI {

	private final RanksManager manager;

	public XPrisonRanksAPIImpl(RanksManager manager) {
		this.manager = manager;
	}

	@Override
	public Rank getPlayerRank(Player p) {
		return manager.getPlayerRank(p);
	}

	@Override
	public Optional<Rank> getNextPlayerRank(Player player) {
		return manager.getNextRank(this.getPlayerRank(player).getId());
	}

	@Override
	public int getRankupProgress(Player player) {
		return manager.getRankupProgress(player);
	}

	@Override
	public void setPlayerRank(Player player, Rank rank) {
		manager.setRank(player, rank, null);
	}
}
