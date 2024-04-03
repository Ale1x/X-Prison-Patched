package dev.passarelli.xprison.tokens;

import dev.passarelli.xprison.XPrison;
import dev.passarelli.xprison.XPrisonModule;
import dev.passarelli.xprison.tokens.api.XPrisonTokensAPI;
import dev.passarelli.xprison.tokens.api.XPrisonTokensAPIImpl;
import dev.passarelli.xprison.tokens.config.BlockRewardsConfig;
import dev.passarelli.xprison.tokens.config.TokensConfig;
import dev.passarelli.xprison.tokens.listener.TokensListener;
import dev.passarelli.xprison.tokens.managers.CommandManager;
import dev.passarelli.xprison.tokens.managers.TokensManager;
import dev.passarelli.xprison.tokens.repo.BlocksRepository;
import dev.passarelli.xprison.tokens.repo.TokensRepository;
import dev.passarelli.xprison.tokens.repo.impl.BlocksRepositoryImpl;
import dev.passarelli.xprison.tokens.repo.impl.TokensRepositoryImpl;
import dev.passarelli.xprison.tokens.service.BlocksService;
import dev.passarelli.xprison.tokens.service.TokensService;
import dev.passarelli.xprison.tokens.service.impl.BlocksServiceImpl;
import dev.passarelli.xprison.tokens.service.impl.TokensServiceImpl;
import dev.passarelli.xprison.tokens.task.SavePlayerDataTask;
import lombok.Getter;

public final class XPrisonTokens implements XPrisonModule {

	public static final String MODULE_NAME = "Tokens";

	@Getter
	private static XPrisonTokens instance;

	@Getter
	private BlockRewardsConfig blockRewardsConfig;

	@Getter
	private TokensConfig tokensConfig;

	@Getter
	private XPrisonTokensAPI api;

	@Getter
	private TokensManager tokensManager;

	@Getter
	private CommandManager commandManager;

	@Getter
	private TokensRepository tokensRepository;

	@Getter
	private TokensService tokensService;

	@Getter
	private BlocksRepository blocksRepository;

	@Getter
	private BlocksService blocksService;

	@Getter
	private final XPrison core;

	private SavePlayerDataTask savePlayerDataTask;

	private boolean enabled;


	public XPrisonTokens(XPrison prisonCore) {
		instance = this;
		this.core = prisonCore;
	}


	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void reload() {
		this.tokensConfig.reload();
		this.blockRewardsConfig.reload();
		this.tokensManager.reload();
		this.commandManager.reload();
	}


	@Override
	public void enable() {

		this.tokensConfig = new TokensConfig(this);
		this.blockRewardsConfig = new BlockRewardsConfig(this);

		this.tokensConfig.load();
		this.blockRewardsConfig.load();

		this.tokensRepository = new TokensRepositoryImpl(this.core.getPluginDatabase());
		this.tokensRepository.createTables();

		this.tokensService = new TokensServiceImpl(this.tokensRepository);

		this.blocksRepository = new BlocksRepositoryImpl(this.core.getPluginDatabase());
		this.blocksRepository.createTables();

		this.blocksService = new BlocksServiceImpl(this.blocksRepository);

		this.tokensManager = new TokensManager(this);
		this.tokensManager.enable();

		this.commandManager = new CommandManager(this);
		this.commandManager.enable();

		this.savePlayerDataTask = new SavePlayerDataTask(this);
		this.savePlayerDataTask.start();

		this.registerListeners();

		this.commandManager.enable();

		this.api = new XPrisonTokensAPIImpl(this.tokensManager);

		this.enabled = true;
	}

	private void registerListeners() {
		new TokensListener(this).subscribeToEvents();
	}


	@Override
	public void disable() {
		this.tokensManager.disable();

		this.savePlayerDataTask.stop();

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
		this.tokensRepository.clearTableData();
		this.blocksRepository.clearTableData();
	}

}
