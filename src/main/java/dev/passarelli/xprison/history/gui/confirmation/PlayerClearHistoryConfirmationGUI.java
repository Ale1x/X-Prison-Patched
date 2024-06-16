package dev.passarelli.xprison.history.gui.confirmation;

import dev.passarelli.xprison.history.XPrisonHistory;
import dev.passarelli.xprison.utils.gui.ConfirmationGui;
import dev.passarelli.xprison.utils.player.PlayerUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerClearHistoryConfirmationGUI extends ConfirmationGui {

	private final OfflinePlayer target;
	private XPrisonHistory plugin;

	public PlayerClearHistoryConfirmationGUI(Player player, OfflinePlayer target, XPrisonHistory plugin) {
		super(player, "Clear " + target.getName() + "?");
		this.target = target;
		this.plugin = plugin;
	}

	@Override
	public void confirm(boolean confirm) {
		if (confirm) {
			this.plugin.getHistoryManager().clearPlayerHistory(this.target);
			PlayerUtils.sendMessage(this.getPlayer(),"&aYou have cleared history data of player &e" + target.getName());
		}
		this.close();
	}
}
