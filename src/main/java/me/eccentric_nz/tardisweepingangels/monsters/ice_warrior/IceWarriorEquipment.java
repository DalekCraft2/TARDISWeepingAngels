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
package me.eccentric_nz.tardisweepingangels.monsters.ice_warrior;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class IceWarriorEquipment {

    public static void set(LivingEntity livingEntity, boolean disguise) {
        ItemStack head = new ItemStack(Material.SNOWBALL, 1);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack legs = new ItemStack(Material.IRON_LEGGINGS, 1);
        ItemStack arm = new ItemStack(Material.SNOWBALL, 1);
        ItemStack dagger = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("Ice Warrior Head");
        headMeta.setCustomModelData(4);
        head.setItemMeta(headMeta);
        ItemMeta armMeta = arm.getItemMeta();
        armMeta.setDisplayName("Ice Warrior Arm");
        armMeta.setCustomModelData(2);
        arm.setItemMeta(armMeta);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName("Ice Warrior Chest");
        if (disguise) {
            Damageable chestDamage = (Damageable) chestMeta;
            chestDamage.setDamage(235);
        }
        chest.setItemMeta(chestMeta);
        ItemMeta legsMeta = legs.getItemMeta();
        legsMeta.setDisplayName("Ice Warrior Legs");
        if (disguise) {
            Damageable legsDamage = (Damageable) legsMeta;
            legsDamage.setDamage(220);
        }
        legs.setItemMeta(legsMeta);

        EntityEquipment entityEquipment = livingEntity.getEquipment();
        entityEquipment.setHelmet(head);
        entityEquipment.setChestplate(chest);
        entityEquipment.setLeggings(legs);
        entityEquipment.setBoots(null);
        if (!disguise) {
            ItemMeta daggerMeta = dagger.getItemMeta();
            daggerMeta.setDisplayName("Ice Warrior Dagger");
            daggerMeta.setCustomModelData(3);
            dagger.setItemMeta(daggerMeta);
            entityEquipment.setItemInMainHand(dagger);
            entityEquipment.setItemInOffHand(arm);
            entityEquipment.setItemInMainHandDropChance(0F);
            entityEquipment.setItemInOffHandDropChance(0F);
            entityEquipment.setHelmetDropChance(0F);
            entityEquipment.setChestplateDropChance(0F);
            entityEquipment.setLeggingsDropChance(0F);
            livingEntity.setCanPickupItems(false);
            livingEntity.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER, Monster.ICE_WARRIOR.getPersist());
        }
    }
}
