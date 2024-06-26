package dev.passarelli.xprison.gangs.gui.panel;

import dev.passarelli.xprison.gangs.XPrisonGangs;
import dev.passarelli.xprison.gangs.model.Gang;
import dev.passarelli.xprison.utils.gui.ConfirmationGui;
import org.bukkit.entity.Player;

public final class DisbandGangGUI extends ConfirmationGui {

	private final XPrisonGangs plugin;
	private final Gang gang;

	public DisbandGangGUI(XPrisonGangs plugin, Player player, Gang gang) {
		super(player, plugin.getConfig().getGangDisbandGUITitle());
		this.plugin = plugin;
		this.gang = gang;
	}

	@Override
	public void confirm(boolean confirm) {
		if (confirm) {
			this.plugin.getGangsManager().disbandGang(getPlayer(), this.gang, false);
		}
		this.close();
	}
}
