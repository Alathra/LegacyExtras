package me.ShermansWorld.AlathraExtras;

import me.ShermansWorld.AlathraExtras.anitblockclimb.BlockPlaceListener;
import me.ShermansWorld.AlathraExtras.chat.announcer.Announcer;
import me.ShermansWorld.AlathraExtras.balancing.*;
import me.ShermansWorld.AlathraExtras.chat.ChatListener;
import me.ShermansWorld.AlathraExtras.chat.ShowItemCommand;
import me.ShermansWorld.AlathraExtras.crafting.*;
import me.ShermansWorld.AlathraExtras.disabledispensereggs.DispenserListener;
import me.ShermansWorld.AlathraExtras.disablespawners.DisableSpawners;
import me.ShermansWorld.AlathraExtras.disabletrapdoorflipping.TrapdoorListener;
import me.ShermansWorld.AlathraExtras.balancing.enderchersblock.EnderChestBlockListener;
import me.ShermansWorld.AlathraExtras.endermanexp.EndermanExpDropListener;
import me.ShermansWorld.AlathraExtras.farming.FarmingListener;
import me.ShermansWorld.AlathraExtras.food.FoodConsumeListener;
import me.ShermansWorld.AlathraExtras.funny.AetherPortalListener;
import me.ShermansWorld.AlathraExtras.funny.FreeOpCommand;
import me.ShermansWorld.AlathraExtras.funny.HeadScourgeListener;
import me.ShermansWorld.AlathraExtras.halloween.CandyEatListener;
import me.ShermansWorld.AlathraExtras.items.ItemUseListener;
import me.ShermansWorld.AlathraExtras.chat.joinleavemessages.JoinLeaveMessages;
import me.ShermansWorld.AlathraExtras.metrics.MetricsManager;
import me.ShermansWorld.AlathraExtras.metrics.PlayerFirstJoinListener;
import me.ShermansWorld.AlathraExtras.chat.CommandListener;
import me.ShermansWorld.AlathraExtras.items.ItemFrameListener;
import me.ShermansWorld.AlathraExtras.chat.EssentialsMsgEditor;
import me.ShermansWorld.AlathraExtras.npcs.NPCListener;
import me.ShermansWorld.AlathraExtras.playtime.PlaytimeCommand;
import me.ShermansWorld.AlathraExtras.playtime.PlaytimeTabCompleter;
import me.ShermansWorld.AlathraExtras.funny.puke.PukeCommand;
import me.ShermansWorld.AlathraExtras.repair.RepairListener;
import me.ShermansWorld.AlathraExtras.roll.RollCommand;
import me.ShermansWorld.AlathraExtras.towny.TownyListener;
import me.ShermansWorld.AlathraExtras.towny.TownyMenu;
import me.ShermansWorld.AlathraExtras.tpacooldown.CooldownManager;
import me.ShermansWorld.AlathraExtras.tpacooldown.listener.essentialsx.PreTeleportListener;
import me.ShermansWorld.AlathraExtras.tpacooldown.listener.essentialsx.TeleportRequestResponseListener;
import me.ShermansWorld.AlathraExtras.tpacooldown.listener.player.PlayerCommandPreprocessListener;
import me.ShermansWorld.AlathraExtras.tpacooldown.listener.player.PlayerJoinListener;
import me.ShermansWorld.AlathraExtras.tpacooldown.listener.player.PlayerQuitListener;
import me.ShermansWorld.AlathraExtras.tutorialbook.AnvilListener;
import me.ShermansWorld.AlathraExtras.tutorialbook.GiveTutorialBookCommand;
import me.ShermansWorld.AlathraExtras.tutorialbook.PlayerClickHelpBook;
import me.ShermansWorld.AlathraExtras.tutorialbook.PlayerFirstJoin;
import me.ShermansWorld.AlathraExtras.voting.VotingListener;
import me.ShermansWorld.AlathraExtras.funny.yeet.YeetCommand;
import net.milkbowl.vault.economy.Economy;

import net.momirealms.customfishing.api.BukkitCustomFishingPlugin;
import net.momirealms.customfishing.common.plugin.CustomFishingPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AlathraExtras extends JavaPlugin {

    public static AlathraExtras instance;
    public static ItemStack recycledLeather;

    public static Economy economy = null;
    public static Random rand;

    public static CustomFishingPlugin customFishingPlugin = null;
    
    public static AlathraExtras getInstance() {
        return AlathraExtras.instance;
    }

    public static AlathraExtrasLogger logger;

    
    
    public static void initRecipeItems() {
        recycledLeather = new ItemStack(Material.LEATHER, 1);
        ItemMeta meta = recycledLeather.getItemMeta();
        meta.setDisplayName(Helper.color("&aRecycled Leather"));
        recycledLeather.setItemMeta(meta);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public static void initLogs() {
        File logsFolder = new File("plugins" + File.separator + "AlathraExtras" + File.separator + "logs");
        if (!logsFolder.exists()) {
            logsFolder.mkdirs();
        }
        File log = new File(
            "plugins" + File.separator + "AlathraExtras" + File.separator + "logs" + File.separator + "log.txt");
        if (!log.exists()) {
            try {
                log.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().warning("[AlathraExtras] Encountered error when creating log file!");
            }
        }
        logger = new AlathraExtrasLogger();

    }

    @Override
    public void onLoad() {
        AlathraExtras.instance = this;
        JoinLeaveMessages.getInstance().onLoad();
        Announcer.getInstance().onLoad();
    }

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults();
        saveConfig();
        TownyListener.initTownyChat();

        this.getServer().getPluginManager().registerEvents(new AetherPortalListener(), this);
        this.getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockLockerTownMayor(), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        this.getServer().getPluginManager().registerEvents(new CandyEatListener(), this);
        this.getServer().getPluginManager().registerEvents(new CommandListener(), this);
        this.getServer().getPluginManager().registerEvents(new CraftingListener(), this);
        this.getServer().getPluginManager().registerEvents(new CureListener(), this);
        this.getServer().getPluginManager().registerEvents(new EnderChestBlockListener(), this);
        this.getServer().getPluginManager().registerEvents(new EndermanExpDropListener(), this);
        this.getServer().getPluginManager().registerEvents(new DisableSpawners(), this);
        this.getServer().getPluginManager().registerEvents(new DispenserListener(), this);
        this.getServer().getPluginManager().registerEvents(new FurnaceRecipesListener(), this);
        this.getServer().getPluginManager().registerEvents(new GrindstoneListener(), this);
        this.getServer().getPluginManager().registerEvents(new HeadScourgeListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemFrameListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemUseListener(), this);
        this.getServer().getPluginManager().registerEvents(new EssentialsMsgEditor(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerClickHelpBook(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerFirstJoin(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerFirstJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        this.getServer().getPluginManager().registerEvents(new PreTeleportListener(), this);
        this.getServer().getPluginManager().registerEvents(new RepairListener(), this);
        this.getServer().getPluginManager().registerEvents(new RiptideListener(), this);
        this.getServer().getPluginManager().registerEvents(new SiegeWorldBuildListener(), this);
        this.getServer().getPluginManager().registerEvents(new TeleportRequestResponseListener(), this);
        this.getServer().getPluginManager().registerEvents(new TownyListener(), this);
        this.getServer().getPluginManager().registerEvents(new VotingListener(), this);
        this.getServer().getPluginManager().registerEvents(new NPCListener(), this);
        this.getServer().getPluginManager().registerEvents(new TrapdoorListener(), this);
        this.getServer().getPluginManager().registerEvents(new FoodConsumeListener(), this);
        if (getServer().getPluginManager().isPluginEnabled("CustomFishing"))
            this.getServer().getPluginManager().registerEvents(new FarmingListener(), this);
        // this.getServer().getPluginManager().registerEvents(new BookEventsListener(), this);
        // This is broken do not enable unless you have confirmed its working.
        this.getServer().getPluginManager().registerEvents(new TownyMenu(), this);
        this.getServer().getPluginManager().registerEvents(new CauldronRecipesListener(), this);
        this.getServer().getPluginManager().registerEvents(new CauldronListener(), this);


        initRecipeItems();
        FurnaceRecipes furnaceRecipes = new FurnaceRecipes();
        furnaceRecipes.rottenFleshtoLeather();
        furnaceRecipes.mossyCobbletoAndesite();

        CraftingRecipes.registerAllCraftingRecipes();

        StoneCuttingRecipes.setWoodcuttingRecipes();
        StoneCuttingRecipes.setBamboocuttingRecipes();

        setupEconomy();
        logger = new AlathraExtrasLogger();
        new RollCommand(this);
        new FreeOpCommand(this);
        new PukeCommand(this);
        new GiveTutorialBookCommand(this);
        new AlathraExtrasCommands(this);
        new PlaytimeCommand(this);
        new ShowItemCommand(this);
        new YeetCommand(this);

        PluginCommand alathraextrasCommands = getCommand("alathraextras");
        PluginCommand playtimeCommands = getCommand("playtime");

        if (alathraextrasCommands != null) alathraextrasCommands.setTabCompleter(new AlathraExtrasTabCompleter());

        if (playtimeCommands != null) playtimeCommands.setTabCompleter(new PlaytimeTabCompleter());

        rand = new Random();
        if (instance.getServer().getPluginManager().isPluginEnabled("Essentials")) CooldownManager.getInstance();
        JoinLeaveMessages.getInstance().onEnable();
        Announcer.getInstance().onEnable();
        initLogs();
        if (instance.getServer().getPluginManager().isPluginEnabled("UnifiedMetrics")) new MetricsManager();

        // Attempt to initialize the customFishingPlugin variable
        if (getServer().getPluginManager().isPluginEnabled("CustomFishing")) {
            customFishingPlugin = BukkitCustomFishingPlugin.getInstance();
            if (customFishingPlugin == null) {
                getLogger().warning("Failed to initialize CustomFishing plugin hook.");
            } else {
                getLogger().info("CustomFishing plugin hook initialized successfully.");
            }
        } else {
            getLogger().warning("Failed to initialize CustomFishing plugin hook. CustomFishing could not be found on this server.");
        }
    }

    @Override
    public void onDisable() {
        if (instance.getServer().getPluginManager().isPluginEnabled("Essentials")) CooldownManager.onDisable();
        JoinLeaveMessages.getInstance().onDisable();
        Announcer.getInstance().onDisable();
    }

}
