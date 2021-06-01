/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

/**
 * @author eccentric_nz
 */
public class JudoonBuilder implements Listener {

	private final TARDISWeepingAngels plugin;

	public JudoonBuilder(TARDISWeepingAngels plugin) {
		this.plugin = plugin;
	}

	@EventHandler(ignoreCancelled = true)
	public void onIronTrapdoorPlace(BlockPlaceEvent event) {
		if (event.getBlock().getType().equals(Material.IRON_TRAPDOOR)) {
			Block placed = event.getBlockPlaced();
			// check the blocks below
			Block below = placed.getRelative(BlockFace.DOWN);
			if (!below.getType().equals(Material.OBSIDIAN)) {
				return;
			}
			Block bottom = below.getRelative(BlockFace.DOWN);
			if (!bottom.getType().equals(Material.OBSIDIAN)) {
				return;
			}
			Block east = below.getRelative(BlockFace.EAST);
			Block west = below.getRelative(BlockFace.WEST);
			Block north = below.getRelative(BlockFace.NORTH);
			Block south = below.getRelative(BlockFace.SOUTH);
			if ((east.getType().equals(Material.RED_NETHER_BRICK_WALL) &&
				 west.getType().equals(Material.RED_NETHER_BRICK_WALL)) ||
				(north.getType().equals(Material.RED_NETHER_BRICK_WALL) &&
				 south.getType().equals(Material.RED_NETHER_BRICK_WALL))) {
				if (!event.getPlayer().hasPermission("tardisweepingangels.build.judoon")) {
					event.getPlayer().sendMessage(plugin.pluginName + "You don't have permission to build a Judoon!");
					return;
				}
				// we're building an Judoon
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
					placed.setType(Material.AIR);
					below.setType(Material.AIR);
					bottom.setType(Material.AIR);
					if (east.getType().equals(Material.RED_NETHER_BRICK_WALL)) {
						east.setType(Material.AIR);
						west.setType(Material.AIR);
					} else {
						north.setType(Material.AIR);
						south.setType(Material.AIR);
					}
					Location l = bottom.getLocation().add(0.5d, 0, 0.5d);
					Entity e = Objects.requireNonNull(l.getWorld()).spawnEntity(l, EntityType.ARMOR_STAND);
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
						JudoonEquipment.set(event.getPlayer(), e, false);
						plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ARMOR_STAND, Monster.JUDOON, l));
					}, 2L);
				}, 20L);
			}
		}
	}
}
