package recipes;

import main.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class barrierRecipe {

  ItemStack barrier = new ItemStack(Material.BARRIER, 4);

  public barrierRecipe() {
    recipe(barrier);
  }

  public void recipe(ItemStack barrier) {

    NamespacedKey key = new NamespacedKey(main.plugin, "barrier_item");
    ShapedRecipe recipe = new ShapedRecipe(key, barrier);

    recipe.shape("BB", "BB");

    recipe.setIngredient('B', Material.GLASS);

    Bukkit.addRecipe(recipe);
  }
}
