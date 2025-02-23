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
package me.eccentric_nz.tardisweepingangels.equip;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsAPI;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.monsters.cyberman.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.dalek.DalekEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.hath.HathEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.ice_warrior.IceWarriorEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonWalkRunnable;
import me.eccentric_nz.tardisweepingangels.monsters.k9.K9Equipment;
import me.eccentric_nz.tardisweepingangels.monsters.ood.OodEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silent.SilentEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.silurian.SilurianEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.SontaranEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.sontaran.StraxEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.toclafane.ToclafaneEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.vashta_nerada.VashtaNeradaEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.weeping_angel.AngelEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.zygon.ZygonEquipment;
import me.eccentric_nz.tardisweepingangels.utils.FollowerChecker;
import me.eccentric_nz.tardisweepingangels.utils.HeadBuilder;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

/**
 * @author eccentric_nz
 */
public class MonsterEquipment implements TARDISWeepingAngelsAPI {

    private final TARDISWeepingAngelsPlugin plugin;

    public MonsterEquipment(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setWeepingAngelEquipment(LivingEntity livingEntity, boolean disguise) {
        AngelEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setIceWarriorEquipment(LivingEntity livingEntity, boolean disguise) {
        IceWarriorEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setCyberEquipment(LivingEntity livingEntity, boolean disguise) {
        CybermanEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setDalekEquipment(LivingEntity livingEntity, boolean disguise) {
        DalekEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setEmptyChildEquipment(LivingEntity livingEntity, boolean disguise) {
        EmptyChildEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setHathEquipment(LivingEntity livingEntity, boolean disguise) {
        HathEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setJudoonEquipment(Player player, Entity armorStand, boolean disguise) {
        JudoonEquipment.set(player, armorStand, disguise);
    }

    @Override
    public void setK9Equipment(Player player, Entity armorStand, boolean disguise) {
        K9Equipment.set(player, armorStand, disguise);
    }

    @Override
    public void setOodEquipment(Player player, Entity armorStand, boolean disguise) {
        OodEquipment.set(player, armorStand, disguise);
    }

    @Override
    public void setSilentEquipment(LivingEntity livingEntity) {
        SilentEquipment.set(livingEntity, false);
    }

    @Override
    public void setSilentEquipment(LivingEntity livingEntity, boolean disguise) {
        SilentEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setSilurianEquipment(LivingEntity livingEntity, boolean disguise) {
        SilurianEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setSontaranEquipment(LivingEntity livingEntity, boolean disguise) {
        SontaranEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setStraxEquipment(LivingEntity livingEntity, boolean disguise) {
        StraxEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setToclafaneEquipment(Entity armorStand, boolean disguise) {
        ToclafaneEquipment.set(armorStand, disguise);
    }

    @Override
    public void setVashtaNeradaEquipment(LivingEntity livingEntity, boolean disguise) {
        VashtaNeradaEquipment.set(livingEntity, disguise);
    }

    @Override
    public void setZygonEquipment(LivingEntity livingEntity, boolean disguise) {
        ZygonEquipment.set(livingEntity, disguise);
    }

    @Override
    public void removeEquipment(Player player) {
        RemoveEquipment.set(player);
    }

    @Override
    public boolean isWeepingAngelMonster(Entity entity) {
        if (entity instanceof Zombie || entity instanceof Skeleton) {
            PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
            return persistentDataContainer.has(TARDISWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER) || persistentDataContainer.has(TARDISWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER) || persistentDataContainer.has(TARDISWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER) || persistentDataContainer.has(TARDISWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER) || persistentDataContainer.has(TARDISWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER) || persistentDataContainer.has(TARDISWeepingAngelsPlugin.sontaran, PersistentDataType.INTEGER) || persistentDataContainer.has(TARDISWeepingAngelsPlugin.strax, PersistentDataType.INTEGER) || persistentDataContainer.has(TARDISWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER) || persistentDataContainer.has(TARDISWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER) || persistentDataContainer.has(TARDISWeepingAngelsPlugin.zygon, PersistentDataType.INTEGER);
        } else if (entity instanceof Enderman) {
            if (!entity.getPassengers().isEmpty()) {
                Entity passenger = entity.getPassengers().get(0);
                return passenger != null && passenger.getType().equals(EntityType.GUARDIAN);
            }
        } else if (entity instanceof Bee) {
            if (!entity.getPassengers().isEmpty()) {
                Entity passenger = entity.getPassengers().get(0);
                return passenger != null && passenger.getType().equals(EntityType.ARMOR_STAND);
            }
        } else if (entity instanceof ArmorStand) {
            return entity.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.ownerUuid, TARDISWeepingAngelsPlugin.persistentDataTypeUuid);
        }
        return false;
    }

    @Override
    public Monster getWeepingAngelMonsterType(Entity entity) {
        if (entity instanceof Zombie || entity instanceof Skeleton || entity instanceof Enderman) {
            PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER)) {
                return Monster.CYBERMAN;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER)) {
                return Monster.DALEK;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                return Monster.EMPTY_CHILD;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER)) {
                return Monster.ICE_WARRIOR;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                return Monster.SILURIAN;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.sontaran, PersistentDataType.INTEGER)) {
                return Monster.SONTARAN;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.strax, PersistentDataType.INTEGER)) {
                return Monster.STRAX;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER)) {
                return Monster.VASHTA_NERADA;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER)) {
                return Monster.WEEPING_ANGEL;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.zygon, PersistentDataType.INTEGER)) {
                return Monster.ZYGON;
            }
            if (persistentDataContainer.has(TARDISWeepingAngelsPlugin.silent, PersistentDataType.INTEGER)) {
                return Monster.SILENT;
            }
        }
        return null;
    }

    @Override
    public FollowerChecker isClaimedMonster(Entity entity, UUID uuid) {
        return new FollowerChecker(plugin, entity, uuid);
    }

    @Override
    public void setJudoonEquipment(Player player, Entity armorStand, int ammunition) {
        setJudoonEquipment(player, armorStand, false);
        armorStand.getPersistentDataContainer().set(TARDISWeepingAngelsPlugin.judoon, PersistentDataType.INTEGER, ammunition);
    }

    @Override
    public void setFollowing(ArmorStand armorStand, Player player) {
        int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new JudoonWalkRunnable(armorStand, 0.15d, player), 2L, 2L);
        plugin.getFollowTasks().put(player.getUniqueId(), taskId);
    }

    @Override
    public ItemStack getHead(Monster monster) {
        return HeadBuilder.getItemStack(monster);
    }

    @Override
    public ItemStack getK9() {
        ItemStack itemStack = new ItemStack(Material.BONE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("K9");
        itemMeta.setCustomModelData(1);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
