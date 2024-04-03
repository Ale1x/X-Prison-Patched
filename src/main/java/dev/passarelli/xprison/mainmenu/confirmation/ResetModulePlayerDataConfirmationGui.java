package dev.passarelli.xprison.mainmenu.confirmation;

import dev.passarelli.xprison.XPrison;
import dev.passarelli.xprison.XPrisonModule;
import dev.passarelli.xprison.utils.gui.ConfirmationGui;
import dev.passarelli.xprison.utils.player.PlayerUtils;
import org.bukkit.entity.Player;

public class ResetModulePlayerDataConfirmationGui extends ConfirmationGui {
	private final XPrisonModule module;

	public ResetModulePlayerDataConfirmationGui(Player player, XPrisonModule module) {
		super(player, module == null ? "Reset all player data ?" : "Reset " + module.getName() + " player data?");
		this.module = module;
	}

	@Override
	public void confirm(boolean confirm) {
		if (confirm) {
			if (module == null) {
				XPrison.getInstance().getModules().forEach(XPrisonModule::resetPlayerData);
				PlayerUtils.sendMessage(this.getPlayer(), "&aSuccessfully reset player data of all modules.");
			} else {
				module.resetPlayerData();
				PlayerUtils.sendMessage(this.getPlayer(), "&aSuccessfully reset player data of &e&l" + this.module.getName() + " &amodule.");
			}

		}
		this.close();
	}
}
