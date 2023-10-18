package me.ShermansWorld.AlathraExtras.tutorialbook;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.ShermansWorld.AlathraExtras.misc.CustomItems;

public class PlayerFirstJoin implements Listener {
	@EventHandler
	public void commandSent(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!(p.hasPlayedBefore())) {
			p.getInventory().addItem(CustomItems.tutorialBook());
		}
	}
}
