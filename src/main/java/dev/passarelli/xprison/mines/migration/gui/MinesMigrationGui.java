package dev.passarelli.xprison.mines.migration.gui;

import dev.passarelli.xprison.mines.migration.model.MinesMigration;
import dev.passarelli.xprison.utils.gui.ConfirmationGui;
import org.bukkit.entity.Player;

public final class MinesMigrationGui extends ConfirmationGui {

	private final MinesMigration migration;

	public MinesMigrationGui(Player player, MinesMigration migration) {
		super(player, "Migrate from " + migration.getFromPlugin() + "?");
		this.migration = migration;
	}

	@Override
	public void confirm(boolean confirm) {
		this.close();
		if (confirm) {
			migration.migrate(getPlayer());
		}
	}
}
