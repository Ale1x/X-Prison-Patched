package dev.passarelli.xprison.autominer.service.impl;

import dev.passarelli.xprison.autominer.repo.AutominerRepository;
import dev.passarelli.xprison.autominer.service.AutominerService;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class AutominerServiceImpl implements AutominerService {

	private final AutominerRepository repository;

	public AutominerServiceImpl(AutominerRepository repository) {

		this.repository = repository;
	}

	@Override
	public int getPlayerAutoMinerTime(OfflinePlayer player) {
		return repository.getPlayerAutoMinerTime(player);
	}

	@Override
	public void removeExpiredAutoMiners() {
		repository.removeExpiredAutoMiners();
	}

	@Override
	public void setAutoMiner(Player p, int timeLeft) {
		repository.saveAutoMiner(p, timeLeft);
	}

}
