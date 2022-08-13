package me.ShermansWorld.AlathraExtras.tutorialbook;

import java.util.Arrays;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import me.ShermansWorld.AlathraExtras.Helper;

public class CraftingEvent implements Listener {
	@EventHandler
	public static void craftEvent(CraftItemEvent e) {
		if (Arrays.asList(e.getInventory().getStorageContents()).contains(CustomItems.tutorialBook())) {
			e.getWhoClicked().sendMessage(Helper.Chatlabel() + Helper.color("&cYou cannot craft with the Player's Guide!"));
			e.setCancelled(true);
		}
	}
}
