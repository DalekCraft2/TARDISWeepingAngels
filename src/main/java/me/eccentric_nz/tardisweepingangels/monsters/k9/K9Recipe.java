/*
 * Copyright (C) 2021 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.tardisweepingangels.monsters.k9;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class K9Recipe {

    private final TARDISWeepingAngelsPlugin plugin;

    public K9Recipe(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public void addRecipe() {
        ItemStack itemStack = new ItemStack(Material.BONE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("K9");
        itemMeta.setCustomModelData(1);
        itemStack.setItemMeta(itemMeta);
        ShapedRecipe recipe = new ShapedRecipe(TARDISWeepingAngelsPlugin.k9, itemStack);
        recipe.shape("III", "RRR", "BBB");
        // set ingredients
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('B', Material.BONE);
        plugin.getServer().addRecipe(recipe);
    }
}
