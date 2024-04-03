package dev.passarelli.xprison.enchants.command;

import dev.passarelli.xprison.enchants.XPrisonEnchants;
import dev.passarelli.xprison.enchants.gui.EnchantGUI;
import dev.passarelli.xprison.utils.inventory.InventoryUtils;
import dev.passarelli.xprison.utils.player.PlayerUtils;
import me.lucko.helper.Commands;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantMenuCommand {

	private final XPrisonEnchants plugin;

	public EnchantMenuCommand(XPrisonEnchants plugin) {

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

					openEnchantMenu(pickAxe, c.sender());
				}).registerAndBind(this.plugin.getCore(), "enchantmenu", "enchmenu");
	}

	private void openEnchantMenu(ItemStack pickAxe, Player player) {
		int pickaxeSlot = InventoryUtils.getInventorySlot(player, pickAxe);
		this.plugin.getCore().debug("Pickaxe slot is: " + pickaxeSlot, this.plugin);
		new EnchantGUI(this.plugin, player, pickAxe, pickaxeSlot).open();
	}

	private boolean validatePickaxe(ItemStack pickAxe) {
		return pickAxe != null && this.plugin.getCore().isPickaxeSupported(pickAxe.getType());
	}
}
