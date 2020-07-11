package listener.onPlayerEvent;

import main.main;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;

public class onPlayerDiscover implements Listener {

  @EventHandler
  public void onPlayerDiscover(PlayerRecipeDiscoverEvent event) {
    NamespacedKey recipe = event.getRecipe();
    if("minecraft:glass".equals(recipe.toString())) {
      NamespacedKey barrier_item = new NamespacedKey(main.plugin, "barrier_item");
      NamespacedKey barrier_pickaxe = new NamespacedKey(main.plugin, "barrier_pickaxe");
      event.getPlayer().discoverRecipe(barrier_pickaxe);
      event.getPlayer().discoverRecipe(barrier_item);
    }
  }
}
