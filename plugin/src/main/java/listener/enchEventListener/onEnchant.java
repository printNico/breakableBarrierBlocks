package listener.enchEventListener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class onEnchant implements Listener {

  @EventHandler
  public void onEnchant(EnchantItemEvent event) {

    ItemStack pickaxe = event.getItem();
    ItemMeta pickaxeMeta = pickaxe.getItemMeta();

    if ("IRON_PICKAXE".equals(pickaxe.getType().toString())) {
      if (pickaxeMeta.hasCustomModelData()) {
        if (pickaxeMeta.getCustomModelData() == 3347619) {
          /*
          TODO
          Maybe consider redoing this part.
          Might not work if custom Color Codes are parent in the name.
           */
          String oldname = pickaxeMeta.getDisplayName().replace("§f", "");
          pickaxeMeta.setDisplayName(ChatColor.AQUA + oldname);
          pickaxe.setItemMeta(pickaxeMeta);
        }
      }
    }
  }
}
