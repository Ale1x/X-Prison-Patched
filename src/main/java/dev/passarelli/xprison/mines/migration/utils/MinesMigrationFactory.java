package dev.passarelli.xprison.mines.migration.utils;

import dev.passarelli.xprison.mines.migration.exception.MinesMigrationNotSupportedException;
import dev.passarelli.xprison.mines.migration.model.MinesMigration;
import dev.passarelli.xprison.mines.migration.model.impl.MineResetLiteMigration;

public class MinesMigrationFactory {

	public static MinesMigration fromPlugin(String pluginName) throws MinesMigrationNotSupportedException {

		if ("mineresetlite".equalsIgnoreCase(pluginName)) {
			return new MineResetLiteMigration();
		}
		throw new MinesMigrationNotSupportedException(pluginName);

	}
}
