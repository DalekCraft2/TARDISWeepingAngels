/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.tardisweepingangels.utils;

import java.util.ArrayList;
import java.util.List;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author eccentric_nz
 */
public class HelmetChecker implements Listener {

    private final TARDISWeepingAngels plugin;
    private final List<EntityType> heads = new ArrayList<EntityType>();

    public HelmetChecker(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        heads.add(EntityType.PIG_ZOMBIE);
        heads.add(EntityType.SKELETON);
        heads.add(EntityType.ZOMBIE);
    }

    @EventHandler
    public void onLoseHead(EntityCombustEvent event) {
        Entity e = event.getEntity();
        EntityType et = e.getType();
        EntityEquipment ee;
        if (heads.contains(et)) {
            switch (et) {
                case PIG_ZOMBIE:
                    PigZombie pz = (PigZombie) e;
                    ee = pz.getEquipment();
                    break;
                case SKELETON:
                    Skeleton s = (Skeleton) e;
                    ee = s.getEquipment();
                    break;
                default:
                    Zombie z = (Zombie) e;
                    ee = z.getEquipment();
                    break;
            }
            // check chestplate
            ItemStack c = ee.getChestplate();
            if (c.hasItemMeta() && c.getItemMeta().hasDisplayName()) {
                String dn = c.getItemMeta().getDisplayName();
                if (dn.startsWith("Cyberman") || dn.startsWith("Ice") || dn.startsWith("Silurian") || dn.startsWith("Sontaran") || dn.startsWith("Zygon")) {
                    event.setCancelled(true);
                    // restore head
                    ItemStack helmet;
                    String name;
                    if (dn.startsWith("Cyberman")) {
                        helmet = new ItemStack(Material.IRON_HELMET, 1);
                        name = "Cyberman Head";
                    } else if (dn.startsWith("Ice")) {
                        helmet = new ItemStack(Material.IRON_HELMET, 1);
                        name = "Ice Warrior Head";
                    } else if (dn.startsWith("Silurian")) {
                        helmet = new ItemStack(Material.GOLD_HELMET, 1);
                        name = "Silurian Head";
                    } else if (dn.startsWith("Sontaran")) {
                        helmet = new ItemStack(Material.GOLD_HELMET, 1);
                        name = "Sontaran Head";
                    } else {
                        helmet = new ItemStack(Material.GOLD_HELMET, 1);
                        name = "Zygon Head";
                    }
                    ItemMeta hmeta = helmet.getItemMeta();
                    hmeta.setDisplayName(name);
                    helmet.setItemMeta(hmeta);
                    ee.setHelmet(helmet);
                    ee.setHelmetDropChance(0F);
                }
            }
        }
    }
}
