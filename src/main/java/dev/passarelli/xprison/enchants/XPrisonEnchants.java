package dev.passarelli.xprison.enchants;

import dev.passarelli.xprison.XPrison;
import dev.passarelli.xprison.XPrisonModule;
import dev.passarelli.xprison.autosell.XPrisonAutoSell;
import dev.passarelli.xprison.enchants.api.XPrisonEnchantsAPI;
import dev.passarelli.xprison.enchants.api.XPrisonEnchantsAPIImpl;
import dev.passarelli.xprison.enchants.command.*;
import dev.passarelli.xprison.enchants.config.EnchantsConfig;
import dev.passarelli.xprison.enchants.gui.DisenchantGUI;
import dev.passarelli.xprison.enchants.gui.EnchantGUI;
import dev.passarelli.xprison.enchants.listener.EnchantsListener;
import dev.passarelli.xprison.enchants.managers.CooldownManager;
import dev.passarelli.xprison.enchants.managers.EnchantsManager;
import dev.passarelli.xprison.enchants.managers.RespawnManager;
import dev.passarelli.xprison.enchants.repo.EnchantsRepository;
import dev.passarelli.xprison.mines.XPrisonMines;
import dev.passarelli.xprison.multipliers.XPrisonMultipliers;
import lombok.Getter;
import me.lucko.helper.utils.Players;
import org.bukkit.entity.Player;

public final class XPrisonEnchants implements XPrisonModule {


	public static final String MODULE_NAME = "Enchants";

	@Getter
	private static XPrisonEnchants instance;

	@Getter
	private XPrisonEnchantsAPI api;

	@Getter
	private EnchantsManager enchantsManager;

	@Getter
	private CooldownManager cooldownManager;

	@Getter
	private RespawnManager respawnManager;

	@Getter
	private EnchantsConfig enchantsConfig;

	@Getter
	private EnchantsListener enchantsListener;

	@Getter
	private EnchantsRepository enchantsRepository;

	@Getter
	private final XPrison core;

	private boolean enabled;

	public XPrisonEnchants(XPrison core) {
		instance = this;
		this.core = core;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void reload() {

		this.enchantsConfig.reload();
		this.enchantsRepository.reload();

		EnchantGUI.init();
		DisenchantGUI.init();

	}

	@Override
	public void enable() {

		this.enchantsConfig = new EnchantsConfig(this);
		this.enchantsConfig.load();

		this.cooldownManager = new CooldownManager(this);
		this.respawnManager = new RespawnManager(this);

		this.enchantsManager = new EnchantsManager(this);
		this.enchantsManager.enable();

		this.enchantsListener = new EnchantsListener(this);
		this.enchantsListener.register();

		this.registerCommands();

		this.enchantsRepository = new EnchantsRepository(this);
		this.enchantsRepository.loadDefaultEnchantments();

		EnchantGUI.init();
		DisenchantGUI.init();

		this.api = new XPrisonEnchantsAPIImpl(this.enchantsManager, this.enchantsRepository);


		this.enabled = true;
	}


	private void registerCommands() {
		DisenchantCommand disenchantCommand = new DisenchantCommand(this);
		disenchantCommand.register();

		EnchantMenuCommand enchantMenuCommand = new EnchantMenuCommand(this);
		enchantMenuCommand.register();

		GiveFirstJoinPickaxeCommand giveFirstJoinPickaxeCommand = new GiveFirstJoinPickaxeCommand(this);
		giveFirstJoinPickaxeCommand.register();

		GivePickaxeCommand givePickaxeCommand = new GivePickaxeCommand(this);
		givePickaxeCommand.register();

		ValueCommand valueCommand = new ValueCommand(this);
		valueCommand.register();
	}


	@Override
	public void disable() {
		for (Player p : Players.all()) {
			p.closeInventory();
		}
		this.enchantsManager.disable();
		this.enabled = false;
	}

	@Override
	public String getName() {
		return MODULE_NAME;
	}

	@Override
	public boolean isHistoryEnabled() {
		return false;
	}

	@Override
	public void resetPlayerData() {
	}

	public boolean isAutoSellModuleEnabled() {
		return this.core.isModuleEnabled(XPrisonAutoSell.MODULE_NAME);
	}

	public boolean isMultipliersModuleEnabled() {
		return this.core.isModuleEnabled(XPrisonMultipliers.MODULE_NAME);
	}

	public boolean isMinesModuleEnabled() {
		return this.core.isModuleEnabled(XPrisonMines.MODULE_NAME);
	}

}
