package dev.passarelli.xprison.pickaxelevels;

import dev.passarelli.xprison.XPrison;
import dev.passarelli.xprison.XPrisonModule;
import dev.passarelli.xprison.pickaxelevels.api.XPrisonPickaxeLevelsAPI;
import dev.passarelli.xprison.pickaxelevels.api.XPrisonPickaxeLevelsAPIImpl;
import dev.passarelli.xprison.pickaxelevels.config.PickaxeLevelsConfig;
import dev.passarelli.xprison.pickaxelevels.listener.PickaxeLevelsListener;
import dev.passarelli.xprison.pickaxelevels.manager.PickaxeLevelsManager;
import lombok.Getter;

public final class XPrisonPickaxeLevels implements XPrisonModule {

    public static final String MODULE_NAME = "Pickaxe Levels";

    @Getter
    private PickaxeLevelsConfig pickaxeLevelsConfig;
    @Getter
    private PickaxeLevelsManager pickaxeLevelsManager;
    @Getter
    private XPrisonPickaxeLevelsAPI api;

    @Getter
    private final XPrison core;

    private boolean enabled;

    public XPrisonPickaxeLevels(XPrison core) {
        this.core = core;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void reload() {
        this.pickaxeLevelsConfig.reload();
	}

    @Override
    public void enable() {
        this.pickaxeLevelsConfig = new PickaxeLevelsConfig(this);
        this.pickaxeLevelsConfig.load();
        this.pickaxeLevelsManager = new PickaxeLevelsManager(this);

        this.registerListeners();

        this.api = new XPrisonPickaxeLevelsAPIImpl(this.pickaxeLevelsManager);
        this.enabled = true;
    }

    private void registerListeners() {
        new PickaxeLevelsListener(this).register();
    }

    @Override
    public void disable() {
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
}
