package dev.passarelli.xprison.ranks;

import dev.passarelli.xprison.XPrison;
import dev.passarelli.xprison.XPrisonModule;
import dev.passarelli.xprison.ranks.api.XPrisonRanksAPI;
import dev.passarelli.xprison.ranks.api.XPrisonRanksAPIImpl;
import dev.passarelli.xprison.ranks.commands.MaxRankupCommand;
import dev.passarelli.xprison.ranks.commands.RankupCommand;
import dev.passarelli.xprison.ranks.commands.SetRankCommand;
import dev.passarelli.xprison.ranks.config.RanksConfig;
import dev.passarelli.xprison.ranks.listener.RanksListener;
import dev.passarelli.xprison.ranks.manager.RanksManager;
import dev.passarelli.xprison.ranks.repo.RanksRepository;
import dev.passarelli.xprison.ranks.repo.impl.RanksRepositoryImpl;
import dev.passarelli.xprison.ranks.service.RanksService;
import dev.passarelli.xprison.ranks.service.impl.RanksServiceImpl;
import lombok.Getter;

@Getter
public final class XPrisonRanks implements XPrisonModule {

	public static final String MODULE_NAME = "Ranks";

	@Getter
	private RanksConfig ranksConfig;
	@Getter
	private RanksManager ranksManager;
	@Getter
	private XPrisonRanksAPI api;
	@Getter
	private final XPrison core;

	@Getter
	private RanksRepository ranksRepository;

	@Getter
	private RanksService ranksService;

	private boolean enabled;

	public XPrisonRanks(XPrison core) {
		this.core = core;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void reload() {
		this.ranksConfig.reload();
	}

	@Override
	public void enable() {
		this.enabled = true;
		this.ranksConfig = new RanksConfig(this);
		this.ranksConfig.load();
		this.ranksRepository = new RanksRepositoryImpl(this.core.getPluginDatabase());
		this.ranksRepository.createTables();
		this.ranksService = new RanksServiceImpl(this.ranksRepository);
		this.ranksManager = new RanksManager(this);
		this.ranksManager.enable();
		this.api = new XPrisonRanksAPIImpl(this.ranksManager);
		this.registerCommands();
		this.registerListeners();
	}

	private void registerListeners() {
		new RanksListener(this).register();
	}

	@Override
	public void disable() {
		this.ranksManager.disable();
		this.enabled = false;
	}

	@Override
	public String getName() {
		return MODULE_NAME;
	}

	@Override
	public boolean isHistoryEnabled() {
		return true;
	}

	@Override
	public void resetPlayerData() {
		this.ranksRepository.clearTableData();
	}

	private void registerCommands() {
		new RankupCommand(this).register();
		new MaxRankupCommand(this).register();
		new SetRankCommand(this).register();
	}
}
