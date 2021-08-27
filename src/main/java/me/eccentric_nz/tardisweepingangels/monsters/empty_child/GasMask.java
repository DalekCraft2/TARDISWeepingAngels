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
package me.eccentric_nz.tardisweepingangels.monsters.empty_child;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

/**
 * @author eccentric_nz
 */
public class GasMask implements Listener {

    private final TARDISWeepingAngelsPlugin plugin;

    public GasMask(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!plugin.getEmpty().contains(uuid)) {
            return;
        }
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            PlayerInventory inventory = player.getInventory();
            ItemStack helmet = inventory.getHelmet();
            if (helmet != null) {
                // move it to the first free slot
                int freeSlot = inventory.firstEmpty();
                if (freeSlot != -1) {
                    inventory.setItem(freeSlot, helmet);
                } else {
                    player.getWorld().dropItemNaturally(player.getLocation(), helmet);
                }
            }
            // set helmet to pumpkin
            ItemStack gasMask = new ItemStack(Material.CARVED_PUMPKIN, 1);
            ItemMeta gasMaskMeta = gasMask.getItemMeta();
            gasMaskMeta.setDisplayName("Gas Mask");
            gasMaskMeta.setCustomModelData(1);
            gasMask.setItemMeta(gasMaskMeta);
            inventory.setHelmet(gasMask);
            player.updateInventory();
            // schedule delayed task
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                plugin.getEmpty().remove(uuid);
                plugin.getTimesUp().add(uuid);
            }, 600L);
        }, 5L);
    }

    @EventHandler
    public void onHelmetClick(InventoryClickEvent event) {
        if (event.getInventory().getType().equals(InventoryType.CRAFTING) && event.getRawSlot() == 5) {
            Player player = (Player) event.getWhoClicked();
            if (plugin.getEmpty().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
            if (plugin.getTimesUp().contains(player.getUniqueId())) {
                event.setCancelled(true);
                player.getInventory().setHelmet(null);
                player.updateInventory();
                plugin.getTimesUp().remove(player.getUniqueId());
            }
        }
    }
}
