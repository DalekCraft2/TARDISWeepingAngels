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
package me.eccentric_nz.tardisweepingangels.death;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.monsters.cyberman.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eccentric_nz
 */
public class Death implements Listener {

    private final TARDISWeepingAngelsPlugin plugin;
    private final List<Material> weepingAngelDrops = new ArrayList<>();
    private final List<Material> cybermanDrops = new ArrayList<>();
    private final List<Material> dalekDrops = new ArrayList<>();
    private final List<Material> emptyChildDrops = new ArrayList<>();
    private final List<Material> hathDrops = new ArrayList<>();
    private final List<Material> silentDrops = new ArrayList<>();
    private final List<Material> iceWarriorDrops = new ArrayList<>();
    private final List<Material> silurianDrops = new ArrayList<>();
    private final List<Material> sontaranDrops = new ArrayList<>();
    private final List<Material> vashtaNeradaDrops = new ArrayList<>();
    private final List<Material> zygonDrops = new ArrayList<>();

    public Death(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        plugin.getConfig().getStringList("angels.drops").forEach((weepingAngel) -> weepingAngelDrops.add(Material.valueOf(weepingAngel)));
        plugin.getConfig().getStringList("cybermen.drops").forEach((cyberman) -> cybermanDrops.add(Material.valueOf(cyberman)));
        plugin.getConfig().getStringList("daleks.drops").forEach((dalek) -> dalekDrops.add(Material.valueOf(dalek)));
        plugin.getConfig().getStringList("empty_child.drops").forEach((emptyChild) -> emptyChildDrops.add(Material.valueOf(emptyChild)));
        plugin.getConfig().getStringList("hath.drops").forEach((hath) -> hathDrops.add(Material.valueOf(hath)));
        plugin.getConfig().getStringList("ice_warriors.drops").forEach((iceWarrior) -> iceWarriorDrops.add(Material.valueOf(iceWarrior)));
        plugin.getConfig().getStringList("sontarans.drops").forEach((sontaran) -> sontaranDrops.add(Material.valueOf(sontaran)));
        plugin.getConfig().getStringList("silent.drops").forEach((silent) -> silentDrops.add(Material.valueOf(silent)));
        plugin.getConfig().getStringList("silurians.drops").forEach((silurian) -> silurianDrops.add(Material.valueOf(silurian)));
        plugin.getConfig().getStringList("vashta_nerada.drops").forEach((vashtaNerada) -> vashtaNeradaDrops.add(Material.valueOf(vashtaNerada)));
        plugin.getConfig().getStringList("zygons.drops").forEach((zygon) -> zygonDrops.add(Material.valueOf(zygon)));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        PersistentDataContainer persistentDataContainer = event.getEntity().getPersistentDataContainer();
        if (event.getEntityType().equals(EntityType.SKELETON)) {
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack itemStack;
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.BRICK, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Weeping Angel Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(weepingAngelDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(weepingAngelDrops.size())), TARDISWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack itemStack;
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.FEATHER, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Silurian Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(silurianDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(silurianDrops.size())), TARDISWeepingAngelsPlugin.random.nextInt(2) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack itemStack;
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.SLIME_BALL, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Dalek Head");
                    itemMeta.setCustomModelData(10000004);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(dalekDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(dalekDrops.size())), 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
        }
        if (event.getEntityType().equals(EntityType.ZOMBIFIED_PIGLIN)) {
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack itemStack;
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.SNOWBALL, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Ice Warrior Head");
                    itemMeta.setCustomModelData(4);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(iceWarriorDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(iceWarriorDrops.size())), TARDISWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.hath, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                ItemStack itemStack;
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.PUFFERFISH, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Hath Head");
                    itemMeta.setCustomModelData(4);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(hathDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(hathDrops.size())), TARDISWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
        }
        if (event.getEntityType().equals(EntityType.ZOMBIE)) {
            ItemStack itemStack;
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.IRON_INGOT, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Cyberman Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 6) {
                    itemStack = new ItemStack(Material.IRON_INGOT, 1);
                } else {
                    itemStack = new ItemStack(cybermanDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(cybermanDrops.size())), 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.SUGAR, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Empty Child Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 6) {
                    itemStack = new ItemStack(Material.POTION);
                    PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                    potionMeta.setBasePotionData(new PotionData(PotionType.REGEN));
                    itemStack.setItemMeta(potionMeta);
                } else {
                    itemStack = new ItemStack(emptyChildDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(emptyChildDrops.size())), TARDISWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.sontaran, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.POTATO, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Sontaran Head");
                    itemMeta.setCustomModelData(4);
                    itemStack.setItemMeta(itemMeta);
                } else if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 6) {
                    itemStack = new ItemStack(Material.MILK_BUCKET, 1);
                } else {
                    itemStack = new ItemStack(sontaranDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(sontaranDrops.size())), 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.BOOK, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Vashta Nerada Head");
                    itemMeta.setCustomModelData(4);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(vashtaNeradaDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(vashtaNeradaDrops.size())), TARDISWeepingAngelsPlugin.random.nextInt(2) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.zygon, PersistentDataType.INTEGER)) {
                event.getDrops().clear();
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.PAINTING, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Zygon Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(zygonDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(zygonDrops.size())), TARDISWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
                return;
            }
        }
        if (event.getEntityType().equals(EntityType.VILLAGER) || event.getEntityType().equals(EntityType.PLAYER)) {
            if (!plugin.getConfig().getBoolean("cybermen.can_upgrade")) {
                return;
            }
            if (plugin.isCitizensEnabled() && CitizensAPI.getNPCRegistry().isNPC(event.getEntity())) {
                return;
            }
            EntityDamageEvent damage = event.getEntity().getLastDamageCause();
            if (damage != null && damage.getCause().equals(DamageCause.ENTITY_ATTACK)) {
                Entity attacker = (((EntityDamageByEntityEvent) damage).getDamager());
                PersistentDataContainer attackerPersistentDataContainer = attacker.getPersistentDataContainer();
                if (attacker instanceof Zombie && attackerPersistentDataContainer.has(TARDISWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER)) {
                    Location location = event.getEntity().getLocation();
                    LivingEntity livingEntity = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                    livingEntity.setSilent(true);
                    CybermanEquipment.set(livingEntity, false);
                    plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(livingEntity, EntityType.ZOMBIE, Monster.CYBERMAN, location));
                    if (event.getEntity() instanceof Player) {
                        String name = event.getEntity().getName();
                        livingEntity.setCustomName(name);
                        livingEntity.setCustomNameVisible(true);
                    }
                    return;
                }
                if (attackerPersistentDataContainer.has(TARDISWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                    if (event.getEntity() instanceof Player player) {
                        plugin.getEmpty().add(player.getUniqueId());
                    }
                }
            }
        }
        if (event.getEntityType().equals(EntityType.ENDERMAN)) {
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.silent, PersistentDataType.INTEGER)) {
                // remove the guardian as well
                Entity guardian = (event.getEntity().getPassengers().size() > 0) ? event.getEntity().getPassengers().get(0) : null;
                if (guardian != null) {
                    guardian.remove();
                }
                event.getDrops().clear();
                ItemStack itemStack;
                if (TARDISWeepingAngelsPlugin.random.nextInt(100) < 3) {
                    itemStack = new ItemStack(Material.END_STONE, 1);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.monsterHead, PersistentDataType.INTEGER, 99);
                    itemMeta.setDisplayName("Silent Head");
                    itemMeta.setCustomModelData(3);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack = new ItemStack(silentDrops.get(TARDISWeepingAngelsPlugin.random.nextInt(silentDrops.size())), TARDISWeepingAngelsPlugin.random.nextInt(1) + 1);
                }
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), itemStack);
            }
        }
    }
}
