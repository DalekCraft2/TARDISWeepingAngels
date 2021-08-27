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

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SilurianEquipment {

    public static void set(LivingEntity livingEntity, boolean disguise) {
        ItemStack head = new ItemStack(Material.FEATHER, 1);
        ItemStack arm = new ItemStack(Material.BOW, 1);
        ItemStack chest = new ItemStack(Material.GOLDEN_CHESTPLATE, 1);
        ItemStack legs = new ItemStack(Material.GOLDEN_LEGGINGS, 1);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("Silurian Head");
        headMeta.setCustomModelData(3);
        head.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Silurian Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName("Silurian Chest");
        if (disguise) {
            Damageable chestDamage = (Damageable) chestMeta;
            chestDamage.setDamage(235);
        }
        chest.setItemMeta(chestMeta);
        ItemMeta legsMeta = legs.getItemMeta();
        legsMeta.setDisplayName("Silurian Legs");
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
            ItemStack bow = new ItemStack(Material.BOW, 1);
            ItemMeta bowMeta = bow.getItemMeta();
            bowMeta.setDisplayName("Silurian Weapon");
            bowMeta.setCustomModelData(3);
            bow.setItemMeta(bowMeta);
            entityEquipment.setItemInMainHand(bow);
            entityEquipment.setItemInOffHand(arm);
            entityEquipment.setItemInMainHandDropChance(0F);
            entityEquipment.setItemInOffHandDropChance(0F);
            entityEquipment.setHelmetDropChance(0F);
            entityEquipment.setChestplateDropChance(0F);
            entityEquipment.setLeggingsDropChance(0F);
            livingEntity.setCanPickupItems(false);
            livingEntity.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER, Monster.SILURIAN.getPersist());
        }
    }
}
