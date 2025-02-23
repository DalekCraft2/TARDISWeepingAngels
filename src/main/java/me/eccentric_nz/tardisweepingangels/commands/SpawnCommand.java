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
package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardischunkgenerator.TARDISHelper;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.monsters.cyberman.CybermanEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.dalek.DalekEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.empty_child.EmptyChildEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.hath.HathEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.ice_warrior.IceWarriorEquipment;
import me.eccentric_nz.tardisweepingangels.monsters.judoon.JudoonEquipment;
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
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class SpawnCommand {

    private final TARDISWeepingAngelsPlugin plugin;

    public SpawnCommand(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean spawn(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return false;
        }
        String upper = args[1].toUpperCase();
        // check monster type
        Monster monster;
        try {
            monster = Monster.valueOf(upper);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(plugin.getMessagePrefix() + "Invalid monster type!");
            return true;
        }
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (player == null) {
            sender.sendMessage(plugin.getMessagePrefix() + "Command can only be used by a player!");
            return true;
        }
        // check player has permission for this monster
        if (!player.hasPermission("tardisweepingangels.spawn." + monster.getPermission())) {
            sender.sendMessage(plugin.getMessagePrefix() + "You don't have permission to spawn a " + monster + "!");
            return true;
        }
        Location eyeLocation = player.getTargetBlock(null, 50).getLocation();
        eyeLocation.add(0.5, 1.0, 0.5);
        eyeLocation.setYaw(player.getLocation().getYaw() - 180.0f);
        World world = eyeLocation.getWorld();
        switch (monster) {
            case WEEPING_ANGEL -> {
                LivingEntity weepingAngel = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                weepingAngel.setSilent(true);
                weepingAngel.setNoDamageTicks(75);
                AngelEquipment.set(weepingAngel, false);
                player.playSound(weepingAngel.getLocation(), "blink", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(weepingAngel, EntityType.SKELETON, Monster.WEEPING_ANGEL, eyeLocation));
            }
            case CYBERMAN -> {
                LivingEntity cyberman = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                cyberman.setSilent(true);
                cyberman.setNoDamageTicks(75);
                ((Ageable) cyberman).setAdult();
                player.playSound(cyberman.getLocation(), "cyberman", 1.0f, 1.0f);
                CybermanEquipment.set(cyberman, false);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(cyberman, EntityType.ZOMBIE, Monster.CYBERMAN, eyeLocation));
            }
            case DALEK -> {
                LivingEntity dalek = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                dalek.setSilent(true);
                dalek.setNoDamageTicks(75);
                DalekEquipment.set(dalek, false);
                player.playSound(dalek.getLocation(), "dalek", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(dalek, EntityType.SKELETON, Monster.DALEK, eyeLocation));
                if (args[1].equalsIgnoreCase("flying") && plugin.getServer().getPluginManager().isPluginEnabled("TARDISChunkGenerator")) {
                    TARDISHelper tardisHelper = (TARDISHelper) plugin.getServer().getPluginManager().getPlugin("TARDISChunkGenerator");
                    // make the Dalek fly
                    EntityEquipment entityEquipment = dalek.getEquipment();
                    entityEquipment.setChestplate(new ItemStack(Material.ELYTRA, 1));
                    // teleport them straight up
                    dalek.teleport(dalek.getLocation().add(0.0d, 20.0d, 0.0d));
                    dalek.setGliding(true);
                    tardisHelper.setFallFlyingTag(dalek);
                    entityEquipment.setChestplate(new ItemStack(Material.AIR));
                }
            }
            case EMPTY_CHILD -> {
                LivingEntity emptyChild = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                emptyChild.setSilent(true);
                emptyChild.setNoDamageTicks(75);
                ((Ageable) emptyChild).setBaby();
                EmptyChildEquipment.set(emptyChild, false);
                player.playSound(emptyChild.getLocation(), "empty_child", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(emptyChild, EntityType.ZOMBIE, Monster.EMPTY_CHILD, eyeLocation));
            }
            case HATH -> {
                LivingEntity hath = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIFIED_PIGLIN);
                hath.setSilent(true);
                hath.setNoDamageTicks(75);
                HathEquipment.set(hath, false);
                player.playSound(hath.getLocation(), "hath", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(hath, EntityType.ZOMBIFIED_PIGLIN, Monster.HATH, eyeLocation));
            }
            case ICE_WARRIOR -> {
                LivingEntity iceWarrior = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIFIED_PIGLIN);
                iceWarrior.setSilent(true);
                IceWarriorEquipment.set(iceWarrior, false);
                player.playSound(iceWarrior.getLocation(), "warrior", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(iceWarrior, EntityType.ZOMBIFIED_PIGLIN, Monster.ICE_WARRIOR, eyeLocation));
                PigZombie pigZombie = (PigZombie) iceWarrior;
                pigZombie.setAngry(true);
                pigZombie.setAnger(Integer.MAX_VALUE);
                ((Ageable) iceWarrior).setAdult();
            }
            case JUDOON -> {
                Entity judoon = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
                JudoonEquipment.set(null, judoon, false);
                player.playSound(judoon.getLocation(), "judoon", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(judoon, EntityType.ARMOR_STAND, Monster.JUDOON, eyeLocation));
            }
            case K9 -> {
                Entity k9 = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
                K9Equipment.set(player, k9, false);
                player.playSound(k9.getLocation(), "k9", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(k9, EntityType.ARMOR_STAND, Monster.K9, eyeLocation));
            }
            case OOD -> {
                Entity ood = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
                OodEquipment.set(null, ood, false);
                player.playSound(ood.getLocation(), "ood", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(ood, EntityType.ARMOR_STAND, Monster.OOD, eyeLocation));
            }
            case SILENT -> {
                LivingEntity silent = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ENDERMAN);
                silent.setSilent(true);
                SilentEquipment.set(silent, false);
                player.playSound(silent.getLocation(), "silence", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(silent, EntityType.ENDERMAN, Monster.SILENT, eyeLocation));
            }
            case SILURIAN -> {
                LivingEntity silurian = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                silurian.setSilent(true);
                silurian.setNoDamageTicks(75);
                SilurianEquipment.set(silurian, false);
                player.playSound(silurian.getLocation(), "silurian", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(silurian, EntityType.SKELETON, Monster.SILURIAN, eyeLocation));
            }
            case SONTARAN -> {
                LivingEntity sontaran = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                sontaran.setSilent(true);
                sontaran.setNoDamageTicks(75);
                ((Ageable) sontaran).setAdult();
                SontaranEquipment.set(sontaran, false);
                player.playSound(sontaran.getLocation(), "sontaran", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(sontaran, EntityType.ZOMBIE, Monster.SONTARAN, eyeLocation));
            }
            case STRAX -> {
                LivingEntity strax = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIFIED_PIGLIN);
                strax.setSilent(true);
                strax.setNoDamageTicks(75);
                PigZombie pigZombie = (PigZombie) strax;
                pigZombie.setAngry(false);
                StraxEquipment.set(strax, false);
                ((Ageable) strax).setAdult();
                player.playSound(strax.getLocation(), "strax", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(strax, EntityType.ZOMBIFIED_PIGLIN, Monster.STRAX, eyeLocation));
            }
            case TOCLAFANE -> {
                Entity toclafane = world.spawnEntity(eyeLocation, EntityType.ARMOR_STAND);
                ToclafaneEquipment.set(toclafane, false);
                player.playSound(toclafane.getLocation(), "toclafane", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(toclafane, EntityType.ARMOR_STAND, Monster.TOCLAFANE, eyeLocation));
            }
            case VASHTA_NERADA -> {
                LivingEntity vashtaNerada = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                vashtaNerada.setSilent(true);
                vashtaNerada.setNoDamageTicks(75);
                ((Ageable) vashtaNerada).setAdult();
                VashtaNeradaEquipment.set(vashtaNerada, false);
                player.playSound(vashtaNerada.getLocation(), "vashta", 1.0f, 1.0f);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(vashtaNerada, EntityType.ZOMBIE, Monster.VASHTA_NERADA, eyeLocation));
            }
            case ZYGON -> {
                LivingEntity zygon = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                zygon.setSilent(true);
                zygon.setNoDamageTicks(75);
                ((Ageable) zygon).setAdult();
                player.playSound(zygon.getLocation(), "zygon", 1.0f, 1.0f);
                ZygonEquipment.set(zygon, false);
                plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(zygon, EntityType.ZOMBIE, Monster.ZYGON, eyeLocation));
            }
        }
        return true;
    }
}
