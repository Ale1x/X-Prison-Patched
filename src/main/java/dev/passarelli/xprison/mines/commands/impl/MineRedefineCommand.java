package dev.passarelli.xprison.mines.commands.impl;

import dev.passarelli.xprison.mines.XPrisonMines;
import dev.passarelli.xprison.mines.commands.MineCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MineRedefineCommand extends MineCommand {

	public MineRedefineCommand(XPrisonMines plugin) {
		super(plugin, "redefine");
	}

	@Override
	public boolean execute(CommandSender sender, List<String> args) {
		if (args.size() != 1) {
			return false;
		}
		if (!(sender instanceof Player)) {
			return false;
		}

		this.plugin.getManager().redefineMine((Player) sender, args.get(0));
		return true;
	}

	@Override
	public String getUsage() {
		return "&cUsage: /mines redefine <name> - Redefines region for a mine";
	}

	@Override
	public boolean canExecute(CommandSender sender) {
		return sender.hasPermission(XPrisonMines.MINES_ADMIN_PERM);
	}
}
