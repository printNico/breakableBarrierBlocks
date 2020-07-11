package recipes;

import main.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class barrierPickaxe {

  ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE, 1);
  ItemMeta meta = pickaxe.getItemMeta();

  private main main;

  public barrierPickaxe(FileConfiguration config) {
    this.main = main;
    recipe(meta, config);
  }

  public void recipe(ItemMeta meta, FileConfiguration config) {

    meta.setDisplayName(ChatColor.WHITE + config.getString("pickaxeName"));
    meta.setCustomModelData(3347619);

    pickaxe.setItemMeta(meta);

    NamespacedKey key = new NamespacedKey(main.plugin, "barrier_pickaxe");
    ShapedRecipe recipe = new ShapedRecipe(key, pickaxe);

    recipe.shape(" B ", "BPB", " B ");

    recipe.setIngredient('B', Material.BARRIER);
    recipe.setIngredient('P', Material.IRON_PICKAXE);

    Bukkit.addRecipe(recipe);
  }
}
