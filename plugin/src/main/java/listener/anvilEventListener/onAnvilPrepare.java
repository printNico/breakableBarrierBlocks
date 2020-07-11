package listener.anvilEventListener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class onAnvilPrepare implements Listener {

  /*
  TODO
  organize each check into its own class
  redo repairing with custom items. Missing MetaData in the Item might be the reason (no repair-cost tag)
  bundle everything and do one result update
  11.07.2020 | Nico
  */

  @EventHandler
  public void onAnvilPrepare(PrepareAnvilEvent event) {

    AnvilInventory inventory = event.getInventory();
    ItemStack resultPickaxe = new ItemStack(Material.IRON_PICKAXE);
    int pickaxeInt = inventory.first(Material.IRON_PICKAXE);

    if (pickaxeInt != -1) {
      ItemStack pickaxe = inventory.getItem(pickaxeInt);
      ItemMeta pickaxeMeta = pickaxe.getItemMeta();
/*
      if (inventory.contains(Material.IRON_INGOT)) {
        if (inventory.contains(Material.IRON_PICKAXE)) {
          if (pickaxeMeta.hasCustomModelData()) {
            if (pickaxeMeta.getCustomModelData() == 3347619) {
              event.setResult(null);
              return;
            }
          }
        }
      }

      if (inventory.contains(Material.BARRIER)) {
        if (inventory.contains(Material.IRON_PICKAXE)) {
          if (pickaxeMeta.hasCustomModelData()) {
            if (pickaxeMeta.getCustomModelData() == 3347619) {

              int barrierInt = inventory.first(Material.BARRIER);
              ItemStack barrier = inventory.getItem(barrierInt);
              int barrierAmount = barrier.getAmount();
              int repairAmount = 0;

              if (barrierAmount >= 4) {
                repairAmount = repairAmount + 250;
              } else if (barrierAmount == 3) {
                repairAmount = repairAmount + 188;
              } else if (barrierAmount == 2) {
                repairAmount = repairAmount + 125;
              } else if (barrierAmount == 1) {
                repairAmount = repairAmount + 63;
              }

              if (pickaxeMeta instanceof Damageable) {

                int damage = ((Damageable) pickaxeMeta).getDamage();
                int repairedDamage = Math.max(0, damage - repairAmount);

                if(inventory.getRepairCost() < 3) {
                  inventory.setRepairCost(3);
                }

                ((Damageable) pickaxeMeta).setDamage(repairedDamage);
                resultPickaxe.setItemMeta(pickaxeMeta);
                event.setResult(resultPickaxe);
              }
            }
          }
        }
      }
      */
      if (inventory.contains(Material.ENCHANTED_BOOK)) {
        if (inventory.contains(Material.IRON_PICKAXE)) {
          if (pickaxeMeta.hasCustomModelData()) {
            if (pickaxeMeta.getCustomModelData() == 3347619) {

              int enchantmentInt = inventory.first(Material.ENCHANTED_BOOK);
              ItemMeta enchantment = inventory.getItem(enchantmentInt).getItemMeta();

              if (enchantment instanceof EnchantmentStorageMeta) {

                int efficLevel = ((EnchantmentStorageMeta) enchantment)
                    .getStoredEnchantLevel(Enchantment.DIG_SPEED);
                int unbrekLevel = ((EnchantmentStorageMeta) enchantment)
                    .getStoredEnchantLevel(Enchantment.DURABILITY);

                pickaxeMeta.addEnchant(Enchantment.DIG_SPEED, efficLevel, false);
                pickaxeMeta.addEnchant(Enchantment.DURABILITY, unbrekLevel, false);
                pickaxeMeta.setDisplayName(ChatColor.AQUA + inventory.getRenameText());

                resultPickaxe.setItemMeta(pickaxeMeta);
                event.setResult(resultPickaxe);
              }
            }
          }
        }
      }
/*
      if (pickaxeMeta.hasCustomModelData()) {
        if (pickaxeMeta.getCustomModelData() == 3347619) {
          if (!"Barrier Spitzhacke".equals(pickaxeMeta.getDisplayName().replace("§f", ""))) {
            pickaxeMeta.setDisplayName(ChatColor.AQUA + inventory.getRenameText());
            resultPickaxe.setItemMeta(pickaxeMeta);
            event.setResult(resultPickaxe);
          }
        }
      }

 */
    }
  }
}
