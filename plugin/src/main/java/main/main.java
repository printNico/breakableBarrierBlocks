package main;

import listener.anvilEventListener.onAnvilPrepare;
import listener.blockEventListener.onBlockDamage;
import listener.enchEventListener.onEnchant;
import listener.onPlayerEvent.onPlayerDiscover;
import listener.onPlayerEvent.onPlayerItemEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import recipes.barrierPickaxe;
import recipes.barrierRecipe;

public class main extends JavaPlugin {

  public static main plugin;
  public final FileConfiguration config = this.getConfig();

  @Override
  public void onEnable() {

    //Make plugin reachable from other classes
    plugin = this;

    //Config
    config.addDefault("pickaxeName", "Barrier Pickaxe");
    config.options().copyDefaults(true);
    saveConfig();

    //Load Events
    getServer().getPluginManager().registerEvents(new onBlockDamage(), this);
    getServer().getPluginManager().registerEvents(new onAnvilPrepare(), this);
    getServer().getPluginManager().registerEvents(new onEnchant(), this);
    getServer().getPluginManager().registerEvents(new onPlayerItemEvent(), this);
    getServer().getPluginManager().registerEvents(new onPlayerDiscover(), this);

    //Load Recipies
    new barrierPickaxe(config);
    new barrierRecipe();


    //Give alive sign
    getLogger().info("Plugin is now active.");

    System.out.println(config.getString("pickaxeName"));
  }

  @Override
  public void onDisable() {
    //Disableing plugin interface
    plugin = null;

    //Give Disable Message
    getLogger().info("Plugin is now not more active.");
  }
}
