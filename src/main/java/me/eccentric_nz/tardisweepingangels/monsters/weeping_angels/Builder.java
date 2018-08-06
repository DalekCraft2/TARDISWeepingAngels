/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.weeping_angels;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.equip.MonsterEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author eccentric_nz
 */
public class Builder implements Listener {

    private final TARDISWeepingAngels plugin;
    private final MonsterEquipment equipper;

    public Builder(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        equipper = new MonsterEquipment();
    }

    @EventHandler(ignoreCancelled = true)
    public void onSkullPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.SKELETON_SKULL)) {
            Block placed = event.getBlockPlaced();
            // check the blocks below
            Block below = placed.getRelative(BlockFace.DOWN);
            if (!below.getType().equals(Material.COBBLESTONE_WALL)) {
                return;
            }
            Block bottom = below.getRelative(BlockFace.DOWN);
            if (!bottom.getType().equals(Material.COBBLESTONE_WALL)) {
                return;
            }
            Block east = below.getRelative(BlockFace.EAST);
            Block west = below.getRelative(BlockFace.WEST);
            Block north = below.getRelative(BlockFace.NORTH);
            Block south = below.getRelative(BlockFace.SOUTH);
            if ((east.getType().equals(Material.COBBLESTONE_WALL) && west.getType().equals(Material.COBBLESTONE_WALL)) || (north.getType().equals(Material.COBBLESTONE_WALL) && south.getType().equals(Material.COBBLESTONE_WALL))) {
                if (!event.getPlayer().hasPermission("tardisweepingangels.build")) {
                    event.getPlayer().sendMessage(plugin.pluginName + "You don't have permission to build a Weeping Angel!");
                    return;
                }
                // we're building an angel
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    placed.setType(Material.AIR);
                    below.setType(Material.AIR);
                    bottom.setType(Material.AIR);
                    if (east.getType().equals(Material.COBBLESTONE_WALL)) {
                        east.setType(Material.AIR);
                        west.setType(Material.AIR);
                    } else {
                        north.setType(Material.AIR);
                        south.setType(Material.AIR);
                    }
                    Location l = bottom.getLocation();
                    LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(l, EntityType.SKELETON);
                    e.setSilent(true);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        equipper.setAngelEquipment(e, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.SKELETON, Monster.WEEPING_ANGEL, l));
                    }, 5L);
                }, 20L);
            }
        }
    }
}
