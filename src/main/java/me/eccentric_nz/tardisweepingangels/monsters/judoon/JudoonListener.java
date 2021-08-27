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
package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class JudoonListener implements Listener {

    private final TARDISWeepingAngelsPlugin plugin;

    public JudoonListener(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageJudoon(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ArmorStand armorStand && event.getDamager() instanceof Player player) {
            if (armorStand.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.ownerUuid, TARDISWeepingAngelsPlugin.persistentDataTypeUuid) && armorStand.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.judoon, PersistentDataType.INTEGER)) {
                event.setCancelled(true);
                player.playSound(armorStand.getLocation(), "judoon", 1.0f, 1.0f);
                if (!player.hasPermission("tardisweepingangels.judoon")) {
                    return;
                }
                UUID judoonId = armorStand.getPersistentDataContainer().get(TARDISWeepingAngelsPlugin.ownerUuid, TARDISWeepingAngelsPlugin.persistentDataTypeUuid);
                if (player.getUniqueId().equals(judoonId)) {
                    int ammo = armorStand.getPersistentDataContainer().get(TARDISWeepingAngelsPlugin.judoon, PersistentDataType.INTEGER);
                    if (Tag.SHULKER_BOXES.isTagged(player.getInventory().getItemInMainHand().getType())) {
                        // top up ammo
                        ItemStack box = player.getInventory().getItemInMainHand();
                        BlockStateMeta boxMeta = (BlockStateMeta) box.getItemMeta();
                        ShulkerBox shulkerBox = (ShulkerBox) boxMeta.getBlockState();
                        Inventory inventory = shulkerBox.getInventory();
                        if (inventory.contains(Material.ARROW)) {
                            int arrow = inventory.first(Material.ARROW);
                            ItemStack arrows = inventory.getItem(arrow);
                            ItemMeta arrowsItemMeta = arrows.getItemMeta();
                            if (arrowsItemMeta.hasCustomModelData() && arrowsItemMeta.getCustomModelData() == 13) {
                                int remove = plugin.getConfig().getInt("judoon.ammunition") - ammo;
                                if (arrows.getAmount() > remove) {
                                    arrows.setAmount(arrows.getAmount() - remove);
                                } else {
                                    remove = arrows.getAmount();
                                    arrows = null;
                                }
                                inventory.setItem(arrow, arrows);
                                shulkerBox.update();
                                boxMeta.setBlockState(shulkerBox);
                                box.setItemMeta(boxMeta);
                                armorStand.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.judoon, PersistentDataType.INTEGER, (ammo + remove));
                                armorStand.setCustomName("Ammunition: " + (ammo + remove));
                                player.sendMessage(plugin.getMessagePrefix() + "You reloaded " + remove + " Judoon ammunition.");
                            }
                        }
                    } else {
                        ItemStack arm = armorStand.getEquipment().getItemInMainHand();
                        ItemMeta armMeta = arm.getItemMeta();
                        int customModelData = armMeta.getCustomModelData();
                        if (customModelData == 4 && ammo > 0) {
                            if (!plugin.getPlayersWithGuards().contains(player.getUniqueId())) {
                                player.sendMessage(plugin.getMessagePrefix() + "Judoon ready for action.");
                                // add to repeating task
                                plugin.getGuards().add(armorStand.getUniqueId());
                                plugin.getPlayersWithGuards().add(player.getUniqueId());
                            } else {
                                player.sendMessage(plugin.getMessagePrefix() + "You already have a Judoon guard!");
                            }
                            // point weapon
                            customModelData = 9;
                            armorStand.setCustomName("Ammunition: " + ammo);
                            armorStand.setCustomNameVisible(true);
                        } else {
                            // stand easy
                            customModelData = 4;
                            armorStand.setCustomNameVisible(false);
                            player.sendMessage(plugin.getMessagePrefix() + "Judoon standing at ease.");
                            // end guarding task
                            plugin.getGuards().remove(armorStand.getUniqueId());
                            plugin.getPlayersWithGuards().remove(player.getUniqueId());
                        }
                        armMeta.setCustomModelData(customModelData);
                        arm.setItemMeta(armMeta);
                        armorStand.getEquipment().setItemInMainHand(arm);
                    }
                } else {
                    if (judoonId.equals(TARDISWeepingAngelsPlugin.unclaimed)) {
                        // claim the Judoon
                        armorStand.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.ownerUuid, TARDISWeepingAngelsPlugin.persistentDataTypeUuid, player.getUniqueId());
                        player.sendMessage(TARDISWeepingAngelsPlugin.plugin.getMessagePrefix() + "You have claimed this Judoon!");
                    }
                }
            }
        }
    }
}
