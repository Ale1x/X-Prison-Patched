package dev.passarelli.xprison.api.events.player;

import dev.passarelli.xprison.api.events.XPrisonEvent;
import lombok.Getter;
import org.bukkit.OfflinePlayer;

public abstract class XPrisonPlayerEvent extends XPrisonEvent {

	@Getter
	protected OfflinePlayer player;

	/**
	 * Abstract XPrisonPlayerEvent
	 *
	 * @param player Player
	 */
	public XPrisonPlayerEvent(OfflinePlayer player) {
		this.player = player;
	}
}
