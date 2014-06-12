/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author eccentric_nz
 */
public class TARDISSilurianRunnable implements Runnable {

    private final TARDISWeepingAngels plugin;
    private final int spawn_rate;
    private final int maximum;
    private final TARDISWeepingAngelEquipment equipper;

    public TARDISSilurianRunnable(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        this.spawn_rate = plugin.getConfig().getInt("silurian.spawn_rate.how_many");
        this.maximum = plugin.getConfig().getInt("silurian.spawn_rate.max_per_world");
        this.equipper = new TARDISWeepingAngelEquipment();
    }

    @Override
    public void run() {
        for (World w : plugin.getServer().getWorlds()) {
            // only configured worlds
            if (plugin.getConfig().getStringList("silurian.worlds").contains(w.getName())) {
                long time = w.getTime();
                // get the current silurian
                List<Skeleton> silurians = new ArrayList<Skeleton>();
                Collection<Skeleton> skeletons = w.getEntitiesByClass(Skeleton.class);
                for (Skeleton s : skeletons) {
                    EntityEquipment ee = s.getEquipment();
                    if (ee.getHelmet().getType().equals(Material.GOLD_HELMET)) {
                        ItemStack is = ee.getHelmet();
                        if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Silurian")) {
                            silurians.add(s);
                        }
                    }
                }
                // count the current silurians
                if (silurians.size() < maximum) {
                    // if less than maximum, spawn some more
                    for (int i = 0; i < spawn_rate; i++) {
                        spawnSilurian(w);
                    }
                }
            }
        }
    }

    private void spawnSilurian(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        if (chunks.length > 0) {
            Chunk c = chunks[plugin.getRandom().nextInt(chunks.length)];
            int x = c.getX() * 16 + plugin.getRandom().nextInt(16);
            int z = c.getZ() * 16 + plugin.getRandom().nextInt(16);
            int y = w.getHighestBlockYAt(x, z);
            Location l = new Location(w, x, y + 1, z);
            Location cave = TARDISWeepingAngelsCaveFinder.searchCave(l);
            if (cave == null) {
                cave = l;
            }
            final LivingEntity e = (LivingEntity) w.spawnEntity(cave, EntityType.SKELETON);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    equipper.setSilurianEquipment(e, false);
                }
            }, 5L);
        }
    }
}
