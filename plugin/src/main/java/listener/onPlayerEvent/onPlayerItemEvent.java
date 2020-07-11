package listener.onPlayerEvent;

import main.main;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class onPlayerItemEvent implements Listener {

  @EventHandler
  public void onPlayerItemEvent(PlayerItemHeldEvent event) {
    Player player = event.getPlayer();
    PlayerInventory inventory = ((HumanEntity) player).getInventory();

    int newSlot = event.getNewSlot();
    ItemStack newItem = inventory.getItem(newSlot);

    if (newItem == null) {
      return;
    }

    if (newItem.getItemMeta().hasCustomModelData()
        && newItem.getItemMeta().getCustomModelData() == 3347619) {
      revealBarrier(player, inventory);
    }

    if ("BARRIER".equals(newItem.getType().toString())) {
      if (!"CREATIVE".equals(player.getGameMode().toString())) {
        revealBarrier(player, inventory);
      }
    }
  }

  public void revealBarrier(Player player, PlayerInventory inventory) {
    Location cords = player.getLocation();
    new BukkitRunnable() {
      @Override
      public void run() {
        int heldItemInt = inventory.getHeldItemSlot();

        if(heldItemInt == -1) {
          cancel();
          return;
        }

        ItemStack heldItem = inventory.getItem(heldItemInt);

        if(heldItem == null) {
          cancel();
          return;
        }

        if(!"BARRIER".equals(heldItem.getType().toString())) {
          if(heldItem.getItemMeta().hasCustomModelData()) {
            if(heldItem.getItemMeta().getCustomModelData() != 3347619) {
              cancel();
              return;
            }
          } else {
            cancel();
            return;
          }
        }

        List<Block> blocks = getNearbyBlocks(cords, 10);
        for (
            int i = 0; i < blocks.size(); i++) {
          double x = blocks.get(i).getX() + 0.5;
          double y = blocks.get(i).getY() + 0.5;
          double z = blocks.get(i).getZ() + 0.5;
          Location newLoc = new Location(player.getWorld(), x, y, z);
          player.spawnParticle(Particle.BARRIER, newLoc, 1);
        }
      }
    }.runTaskTimer(main.plugin, 0L, 40L);
  }

  public static List<Block> getNearbyBlocks(Location location, int radius) {
    List<Block> blocks = new ArrayList<Block>();
    for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
      for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
        for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
          if (location.getWorld().getBlockAt(x, y, z).getType().toString().equals("BARRIER")) {
            blocks.add(location.getWorld().getBlockAt(x, y, z));
          }
        }
      }
    }
    return blocks;
  }
}
