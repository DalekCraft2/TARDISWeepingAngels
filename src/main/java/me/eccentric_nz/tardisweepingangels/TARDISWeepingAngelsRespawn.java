/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author eccentric_nz
 */
public class TARDISWeepingAngelsRespawn implements Listener {

    private final TARDISWeepingAngels plugin;

    public TARDISWeepingAngelsRespawn(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        if (!plugin.getEmpty().contains(uuid)) {
            return;
        }
        plugin.debug(player.getName() + " respawned");
        final PlayerInventory inv = player.getInventory();
        ItemStack helmet = inv.getHelmet();
        if (helmet != null) {
            // move it to the first free slot
            int free_slot = inv.firstEmpty();
            if (free_slot != -1) {
                inv.setItem(free_slot, helmet);
            } else {
                player.getWorld().dropItemNaturally(player.getLocation(), helmet);
            }
        }
        // set helmet to pumpkin
        inv.setHelmet(new ItemStack(Material.PUMPKIN, 1));
        player.updateInventory();
        // schedule delayed task
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.getEmpty().remove(uuid);
                plugin.getTimesUp().add(uuid);
            }
        }, 600L);
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onHelmetClick(InventoryClickEvent event) {
        if (event.getInventory() instanceof PlayerInventory && event.getRawSlot() == 5) {
            Player player = (Player) event.getWhoClicked();
            if (plugin.getEmpty().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
            if (plugin.getTimesUp().contains(player.getUniqueId())) {
                plugin.debug("Times up!");
                event.setCancelled(true);
                event.getInventory().setItem(event.getSlot(), null);
                player.updateInventory();
                plugin.getTimesUp().remove(player.getUniqueId());
            }
        }
    }
}
