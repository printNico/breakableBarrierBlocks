package listener.blockEventListener;


import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;

import java.util.Random;
import main.main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.meta.Damageable;

public class onBlockDamage implements Listener {

  @EventHandler
  public void onBlockDamage(BlockDamageEvent event) {
    Block block = event.getBlock();
    Location cords = block.getLocation();

    if ("BARRIER".equals(block.getBlockData().getMaterial().toString())) {
      if (!"IRON_PICKAXE".equals(event.getItemInHand().getType().toString())) {
        return;
      }
      if (!event.getItemInHand().getItemMeta().hasCustomModelData()) {
        return;
      }
      if (event.getItemInHand().getItemMeta().getCustomModelData() != 3347619) {
        return;
      }

      ItemStack pickaxe = event.getItemInHand();
      ItemMeta pickaxeMeta = pickaxe.getItemMeta();

      int unbreakLevel = pickaxeMeta.getEnchantLevel(Enchantment.DURABILITY);
      int efficLevel = pickaxeMeta.getEnchantLevel(Enchantment.DIG_SPEED);

      new BukkitRunnable() {
        @Override
        public void run() {
          if (event.isCancelled()) {
            cancel();
          } else {
            Entity entity = cords.getWorld().dropItem(cords, new ItemStack(Material.BARRIER, 1));
            event.getBlock().breakNaturally();
            Item item = (Item) entity;

            if (pickaxeMeta instanceof Damageable) {
              int damage = ((Damageable) pickaxeMeta).getDamage() + 1;
              Random r = new Random();
              int rPercent = r.nextInt(100);
              if (rPercent <= (100 / (unbreakLevel + 1))) {
                ((Damageable) pickaxeMeta).setDamage(damage);
              } else {
                cancel();
              }
            }
            pickaxe.setItemMeta(pickaxeMeta);
          }
        }
      }.runTaskLater(main.plugin, 20 - (efficLevel * 3));

      ProtocolLibrary.getProtocolManager()
          .addPacketListener(new PacketAdapter(main.plugin, ListenerPriority.NORMAL,
              Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
              PacketContainer packet = e.getPacket();
              EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().getValues().get(0);
              if ("ABORT_DESTROY_BLOCK".equals(digType.name())) {
                event.setCancelled(true);
              }
            }
          });

      /*

      TODO
      maybe consider adding it, in a different way. It might be possible and would look cool
      11.07.2020 | Nico

      PacketContainer blockBreakAnimation = new PacketContainer(Server.BLOCK_BREAK_ANIMATION);
      blockBreakAnimation.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
      blockBreakAnimation.getIntegers().write(0, new Random().nextInt(2000));
      blockBreakAnimation.getIntegers().write(1, 5);

      try {
        ProtocolLibrary.getProtocolManager().sendServerPacket(event.getPlayer(), blockBreakAnimation);
      } catch (InvocationTargetException e) {
        throw new RuntimeException(
            "Cannot send packet " + blockBreakAnimation, e);
      }

      */

    } else if ("IRON_PICKAXE".equals(event.getItemInHand().getType().toString())) {
      if (!event.getItemInHand().getItemMeta().hasCustomModelData()) {
        return;
      }
      if (event.getItemInHand().getItemMeta().getCustomModelData() != 3347619) {
        return;
      }
      event.setCancelled(true);
    }
  }
}
