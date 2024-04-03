package dev.passarelli.xprison.enchants.command;

import dev.passarelli.xprison.enchants.XPrisonEnchants;
import dev.passarelli.xprison.enchants.gui.DisenchantGUI;
import dev.passarelli.xprison.utils.inventory.InventoryUtils;
import dev.passarelli.xprison.utils.player.PlayerUtils;
import me.lucko.helper.Commands;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DisenchantCommand {

	private final XPrisonEnchants plugin;

	public DisenchantCommand(XPrisonEnchants plugin) {

		this.plugin = plugin;
	}

	public void register() {
		Commands.create()
				.assertPlayer()
				.handler(c -> {
					ItemStack pickAxe = c.sender().getItemInHand();

					if (!validatePickaxe(pickAxe)) {
						PlayerUtils.sendMessage(c.sender(), this.plugin.getEnchantsConfig().getMessage("no_pickaxe_found"));
						return;
					}

					openDisenchantGui(pickAxe, c.sender());

				}).registerAndBind(this.plugin.getCore(), "disenchant", "dise", "de", "disenchantmenu", "dismenu");
	}

	private void openDisenchantGui(ItemStack pickAxe, Player player) {
		int pickaxeSlot = InventoryUtils.getInventorySlot(player, pickAxe);
		this.plugin.getCore().debug("Pickaxe slot is: " + pickaxeSlot, this.plugin);
		new DisenchantGUI(this.plugin, player, pickAxe, pickaxeSlot).open();
	}

	private boolean validatePickaxe(ItemStack pickAxe) {
		return pickAxe != null && this.plugin.getCore().isPickaxeSupported(pickAxe.getType());
	}
}
