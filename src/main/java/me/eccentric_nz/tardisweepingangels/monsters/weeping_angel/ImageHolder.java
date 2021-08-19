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
package me.eccentric_nz.tardisweepingangels.monsters.weeping_angel;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eccentric_nz
 */
public class ImageHolder implements Listener {

    private final TARDISWeepingAngelsPlugin plugin;
    private final List<BlockFace> faces = new ArrayList<>();

    public ImageHolder(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        faces.add(BlockFace.EAST);
        faces.add(BlockFace.NORTH);
        faces.add(BlockFace.WEST);
        faces.add(BlockFace.SOUTH);
    }

    @EventHandler(ignoreCancelled = true)
    public void onChatAboutWeepingAngel(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if (message.toLowerCase().contains("angel") && TARDISWeepingAngelsPlugin.random.nextInt(100) < plugin.getConfig().getInt("angels.spawn_from_chat.chance")) {
            int distance = plugin.getConfig().getInt("angels.spawn_from_chat.distance_from_player");
            Block block = event.getPlayer().getLocation().getBlock().getRelative(faces.get(TARDISWeepingAngelsPlugin.random.nextInt(4)), distance);
            // get the highest block in a random direction
            Location highest = block.getWorld().getHighestBlockAt(block.getLocation()).getLocation();
            Location location = highest.add(0, 1, 0);
            // spawn an angel
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                LivingEntity weepingAngel = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.SKELETON);
                weepingAngel.setSilent(true);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    AngelEquipment.set(weepingAngel, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(weepingAngel, EntityType.SKELETON, Monster.WEEPING_ANGEL, location));
                }, 5L);
            }, 20L);
        }
    }
}
