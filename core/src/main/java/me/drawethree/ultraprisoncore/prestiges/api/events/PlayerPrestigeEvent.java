package me.drawethree.ultraprisoncore.prestiges.api.events;

import lombok.Getter;
import lombok.Setter;
import me.drawethree.ultraprisoncore.api.events.player.UltraPrisonPlayerEvent;
import me.drawethree.ultraprisoncore.prestiges.model.Prestige;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerPrestigeEvent extends UltraPrisonPlayerEvent implements Cancellable {


	private static final HandlerList handlers = new HandlerList();

	@Getter
	private Prestige oldPrestige;

	@Getter
	@Setter
	private Prestige newPrestige;

	@Getter
	@Setter
	private boolean cancelled;

	/**
	 * Called when player receive gems
	 *
	 * @param player      Player
	 * @param oldPrestige old prestige
	 * @param newPrestige new prestige
	 */
	public PlayerPrestigeEvent(Player player, Prestige oldPrestige, Prestige newPrestige) {
		super(player);
		this.oldPrestige = oldPrestige;
		this.newPrestige = newPrestige;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}