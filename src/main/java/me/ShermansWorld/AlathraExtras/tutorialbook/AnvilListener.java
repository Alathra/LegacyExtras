package me.ShermansWorld.AlathraExtras.tutorialbook;

import me.ShermansWorld.AlathraExtras.Helper;
import me.ShermansWorld.AlathraExtras.items.Items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class AnvilListener implements Listener {
    @EventHandler
    public void commandSent(PrepareAnvilEvent e) {
        if (e.getInventory().getItem(0) != null) {
            if (e.getInventory().getItem(0).isSimilar(Items.tutorialBook())) {
                Player p = (Player) e.getView().getPlayer();
                p.closeInventory();
                p.sendMessage(Helper.Chatlabel() + Helper.color("&cYou cannot put the Player's Guide in an anvil!"));

            } else if (e.getInventory().getItem(0).getType() == Material.SHULKER_BOX || e.getInventory().getItem(1).getType() == Material.SHULKER_BOX) {
                Player p = (Player) e.getView().getPlayer();
                p.closeInventory();
                p.sendMessage(Helper.Chatlabel() + Helper.color("&cYou cannot put a shulker box in an anvil!"));
            }
        }
    }
}
