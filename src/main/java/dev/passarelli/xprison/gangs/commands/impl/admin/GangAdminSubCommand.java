package dev.passarelli.xprison.gangs.commands.impl.admin;

import dev.passarelli.xprison.gangs.commands.GangCommand;
import dev.passarelli.xprison.gangs.commands.GangSubCommand;
import dev.passarelli.xprison.gangs.utils.GangsConstants;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public final class GangAdminSubCommand extends GangSubCommand {

	public GangAdminSubCommand(GangCommand command) {
		super(command, "admin");
		registerSubCommand(new GangAdminJoinSubCommand(command));
		registerSubCommand(new GangAdminKickSubCommand(command));
		registerSubCommand(new GangAdminDisbandSubCommand(command));
		registerSubCommand(new GangAdminRenameSubCommand(command));
	}

	@Override
	public String getUsage() {
		return ChatColor.RED + "/gang admin <add/kick/disband/rename> <player> <gang>";
	}

	@Override
	public boolean execute(CommandSender sender, List<String> args) {
		if (args.size() > 0) {
			GangSubCommand subCommand = getSubCommand(args.get(0));
			if (subCommand != null) {
				return subCommand.execute(sender, args.subList(1, args.size()));
			}
		}
		return false;
	}

	@Override
	public boolean canExecute(CommandSender sender) {
		return sender.hasPermission(GangsConstants.GANGS_ADMIN_PERM);
	}

	@Override
	public List<String> getTabComplete() {
		return new ArrayList<>(this.subCommands.keySet());
	}
}
