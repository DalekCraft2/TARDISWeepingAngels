/*
 *  Copyright 2015 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.monsters.dalek;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.monsters.cyberman.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.ice_warrior.IceWarriorEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silurian.SilurianEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.SontaranEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.StraxEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.vashta_nerada.VashtaNeradaEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angel.AngelEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.zygon.ZygonEquipment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * @author eccentric_nz
 */
public class ChunkLoad implements Listener {

    private final TARDISWeepingAngelsPlugin plugin;

    public ChunkLoad(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
            if (entity instanceof Skeleton skeleton) {
                if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER) && (skeleton.getEquipment().getHelmet() == null || skeleton.getEquipment().getHelmet().getType() == Material.MUSHROOM_STEM)) {
                    DalekEquipment.set(skeleton, false);
                } else if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER) && (skeleton.getEquipment().getHelmet() != null && skeleton.getEquipment().getHelmet().getType() == Material.STONE_BUTTON)) {
                    AngelEquipment.set(skeleton, false);
                } else if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER) && skeleton.getEquipment().getHelmet() != null && skeleton.getEquipment().getHelmet().getType() == Material.GOLDEN_HELMET) {
                    SilurianEquipment.set(skeleton, false);
                }
            } else if (entity instanceof PigZombie pigZombie) {
                if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER) && pigZombie.getEquipment().getHelmet() != null && pigZombie.getEquipment().getHelmet().getType() == Material.IRON_HELMET) {
                    IceWarriorEquipment.set(pigZombie, false);
                } else if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.strax, PersistentDataType.INTEGER) && pigZombie.getEquipment().getHelmet() != null && pigZombie.getEquipment().getHelmet().getType() == Material.CHAINMAIL_HELMET) {
                    StraxEquipment.set(pigZombie, false);
                }
            } else if (entity instanceof Drowned drowned) {
                if (drowned.getEquipment().getHelmet() != null) {
                    ItemMeta itemMeta = drowned.getEquipment().getHelmet().getItemMeta();
                    if (itemMeta != null && itemMeta.hasDisplayName() && itemMeta.getDisplayName().endsWith(" Head")) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, drowned::remove, 2L);
                    }
                }
            } else if (entity instanceof Zombie zombie) {
                if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER) && zombie.getEquipment().getHelmet() != null && zombie.getEquipment().getHelmet().getType() == Material.IRON_HELMET) {
                    CybermanEquipment.set(zombie, false);
                } else if (zombie.getEquipment().getHelmet() != null && zombie.getEquipment().getHelmet().getType() == Material.GOLDEN_HELMET) {
                    if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                        EmptyChildEquipment.set(zombie, false);
                    } else if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.zygon, PersistentDataType.INTEGER)) {
                        ZygonEquipment.set(zombie, false);
                    } else if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.sontaran, PersistentDataType.INTEGER)) {
                        SontaranEquipment.set(zombie, false);
                    } else if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER)) {
                        VashtaNeradaEquipment.set(zombie, false);
                    }
                }
            }
        }
    }
}
