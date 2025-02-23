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
package me.eccentric_nz.tardisweepingangels.monsters.silurian;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import me.eccentric_nz.tardisweepingangels.utils.WorldGuardChecker;
import me.eccentric_nz.tardisweepingangels.utils.WorldProcessor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.logging.Level;

/**
 * @author eccentric_nz
 */
public class SilurianRunnable implements Runnable {

    private final TARDISWeepingAngelsPlugin plugin;
    private final int spawnRate;

    public SilurianRunnable(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        spawnRate = plugin.getConfig().getInt("spawn_rate.how_many");
    }

    @Override
    public void run() {
        plugin.getServer().getWorlds().forEach((world) -> {
            // only configured worlds
            String name = WorldProcessor.sanitiseName(world.getName());
            if (plugin.getConfig().getInt("silurians.worlds." + name) > 0) {
                // check the world generator
                if (world.getGenerator() != null) {
                    plugin.getConfig().set("silurians.worlds." + name, 0);
                    plugin.saveConfig();
                    plugin.getServer().getLogger().log(Level.WARNING, "TARDISWeepingAngels cannot safely spawn Silurians in custom worlds!");
                } else {
                    // get the current silurian count
                    int silurians = 0;
                    Collection<Skeleton> skeletons = world.getEntitiesByClass(Skeleton.class);
                    for (Skeleton silurian : skeletons) {
                        PersistentDataContainer persistentDataContainer = silurian.getPersistentDataContainer();
                        if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                            silurians++;
                        }
                    }
                    if (silurians < plugin.getConfig().getInt("silurians.worlds." + name)) {
                        // if less than maximum, spawn some more
                        for (int i = 0; i < spawnRate; i++) {
                            spawnSilurian(world);
                        }
                    }
                }
            }
        });
    }

    private void spawnSilurian(World world) {
        Chunk[] chunks = world.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk chunk = chunks[TARDISWeepingAngelsPlugin.random.nextInt(chunks.length)];
            int x = chunk.getX() * 16 + TARDISWeepingAngelsPlugin.random.nextInt(16);
            int z = chunk.getZ() * 16 + TARDISWeepingAngelsPlugin.random.nextInt(16);
            int y = world.getHighestBlockYAt(x, z);
            Location location = new Location(world, x, y + 1, z);
            Location search = CaveFinder.searchCave(location);
            Location cave = ((search == null)) ? location : search;
            if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null && !WorldGuardChecker.canSpawn(cave)) {
                return;
            }
            LivingEntity silurian = (LivingEntity) world.spawnEntity(cave, EntityType.SKELETON);
            silurian.setSilent(true);
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 360000, 3, true, false);
            silurian.addPotionEffect(potionEffect);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                SilurianEquipment.set(silurian, false);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(silurian, EntityType.SKELETON, Monster.SILURIAN, cave));
            }, 5L);
        }
    }
}
