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
package me.eccentric_nz.tardisweepingangels.monsters.vashta_nerada;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class VashtaNeradaEquipment {

    public static void set(LivingEntity livingEntity, boolean disguise) {
        ItemStack head = new ItemStack(Material.BOOK, 1);
        ItemStack arm = new ItemStack(Material.BOOK, 1);
        ItemStack chest = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack legs = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("Vashta Nerada Head");
        headMeta.setCustomModelData(4);
        head.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Vashta Nerada Arm");
        armMeta.setCustomModelData(3);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName("Vashta Nerada Chest");
        if (disguise) {
            Damageable chestDamage = (Damageable) chestMeta;
            chestDamage.setDamage(235);
        }
        chest.setItemMeta(chestMeta);
        ItemMeta legsMeta = legs.getItemMeta();
        legsMeta.setDisplayName("Vashta Nerada Legs");
        if (disguise) {
            Damageable legsDamage = (Damageable) legsMeta;
            legsDamage.setDamage(220);
        }
        legs.setItemMeta(legsMeta);

        EntityEquipment entityEquipment = livingEntity.getEquipment();
        entityEquipment.setChestplate(chest);
        entityEquipment.setLeggings(legs);
        entityEquipment.setBoots(null);
        entityEquipment.setHelmet(head);
        if (!disguise) {
            entityEquipment.setItemInMainHand(arm);
            entityEquipment.setItemInOffHand(arm.clone());
            entityEquipment.setItemInMainHandDropChance(0F);
            entityEquipment.setItemInOffHandDropChance(0F);
            entityEquipment.setHelmetDropChance(0F);
            entityEquipment.setChestplateDropChance(0F);
            entityEquipment.setLeggingsDropChance(0F);
            livingEntity.setCanPickupItems(false);
            livingEntity.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER, Monster.VASHTA_NERADA.getPersist());
        }
    }
}
