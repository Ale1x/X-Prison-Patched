package dev.passarelli.xprison.autominer.service;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface AutominerService {

	int getPlayerAutoMinerTime(OfflinePlayer player);

	void removeExpiredAutoMiners();

	void setAutoMiner(Player p, int timeLeft);
}
