package me.ShermansWorld.AlathraExtras.tutorialbook;

import com.destroystokyo.paper.MaterialTags;
import me.ShermansWorld.AlathraExtras.Helper;
import me.ShermansWorld.AlathraExtras.items.Items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class AnvilListener implements Listener {
    @EventHandler
    public void commandSent(PrepareAnvilEvent e) {
        List<Material> shulkerBoxMaterials = Arrays.asList(Material.SHULKER_BOX, Material.BLACK_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.CYAN_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIGHT_GRAY_SHULKER_BOX, Material.LIME_SHULKER_BOX, Material.MAGENTA_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.RED_SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX);
        Player p = (Player) e.getView().getPlayer();
        if (e.getInventory().getItem(0) != null) {
            if (e.getInventory().getItem(0).isSimilar(Items.tutorialBook())) {
                p.closeInventory();
                p.sendMessage(Helper.Chatlabel() + Helper.color("&cYou cannot put the Player's Guide in an anvil!"));

            } else if (shulkerBoxMaterials.contains(e.getInventory().getItem(0).getType()) || shulkerBoxMaterials.contains(e.getInventory().getItem(1).getType())) {
                e.setResult(new ItemStack(Material.AIR));

                p.closeInventory();
                p.sendMessage(Helper.Chatlabel() + Helper.color("&cYou cannot put a shulker box in an anvil!"));
            }
        }
    }
}
