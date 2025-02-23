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

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class CountCommand {

    private final TARDISWeepingAngelsPlugin plugin;

    public CountCommand(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean count(CommandSender sender, String[] args) {
        if (args.length < 3) {
            return false;
        }
        String which = args[1].toLowerCase();
        String what = "Weeping Angels";
        int count = 0;
        World world = plugin.getServer().getWorld(args[2]);
        if (world == null) {
            sender.sendMessage(plugin.getMessagePrefix() + "Could not find a world with that name!");
            return true;
        }
        if (which.equals("g")) {
            what = "Invisible Guardians without Endermen";
            Collection<Guardian> guardians = world.getEntitiesByClass(Guardian.class);
            for (Guardian guardian : guardians) {
                if (guardian.hasPotionEffect(PotionEffectType.INVISIBILITY) && guardian.getVehicle() == null) {
                    count++;
                }
            }
        } else {
            Monster monster;
            try {
                monster = Monster.valueOf(which);
            } catch (IllegalArgumentException e) {
                sender.sendMessage(plugin.getMessagePrefix() + "Invalid monster type \"" + which + "\"! " + e.getMessage());
                return true;
            }
            switch (monster) {
                case WEEPING_ANGEL -> {
                    Collection<Skeleton> angels = world.getEntitiesByClass(Skeleton.class);
                    for (Skeleton angel : angels) {
                        if (angel.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.weepingAngel, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case CYBERMAN -> {
                    what = "Cybermen";
                    Collection<Zombie> cybermen = world.getEntitiesByClass(Zombie.class);
                    for (Zombie cyberman : cybermen) {
                        if (cyberman.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.cyberman, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case DALEK -> {
                    what = "Daleks";
                    Collection<Skeleton> daleks = world.getEntitiesByClass(Skeleton.class);
                    for (Skeleton dalek : daleks) {
                        if (dalek.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.dalek, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case EMPTY_CHILD -> {
                    what = "Empty Children";
                    Collection<Zombie> emptyChildren = world.getEntitiesByClass(Zombie.class);
                    for (Zombie emptyChild : emptyChildren) {
                        if (emptyChild.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.emptyChild, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case HATH -> {
                    what = "Hath";
                    Collection<PigZombie> haths = world.getEntitiesByClass(PigZombie.class);
                    for (PigZombie hath : haths) {
                        if (hath.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.hath, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case ICE_WARRIOR -> {
                    what = "Ice Warriors";
                    Collection<PigZombie> iceWarriors = world.getEntitiesByClass(PigZombie.class);
                    for (PigZombie iceWarrior : iceWarriors) {
                        if (iceWarrior.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.iceWarrior, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case JUDOON -> {
                    what = "Judoon";
                    Collection<ArmorStand> judoons = world.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand judoon : judoons) {
                        if (judoon.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.judoon, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case K9 -> {
                    what = "K9";
                    Collection<ArmorStand> k9s = world.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand k9 : k9s) {
                        if (k9.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.k9, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case SILENT -> {
                    what = "Silents";
                    Collection<Enderman> silents = world.getEntitiesByClass(Enderman.class);
                    for (Enderman silent : silents) {
                        if (silent.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.silent, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case SONTARAN -> {
                    what = "Sontarans";
                    Collection<Zombie> sontarans = world.getEntitiesByClass(Zombie.class);
                    for (Zombie sontaran : sontarans) {
                        if (sontaran.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.sontaran, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case OOD -> {
                    what = "Ood";
                    Collection<ArmorStand> oods = world.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand ood : oods) {
                        if (ood.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.ood, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case SILURIAN -> {
                    what = "Silurians";
                    Collection<Skeleton> silurians = world.getEntitiesByClass(Skeleton.class);
                    for (Skeleton silurian : silurians) {
                        if (silurian.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.silurian, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case VASHTA_NERADA -> {
                    what = "Vashta Nerada";
                    Collection<Zombie> vashtaNeradas = world.getEntitiesByClass(Zombie.class);
                    for (Zombie vashtaNerada : vashtaNeradas) {
                        if (vashtaNerada.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.vashtaNerada, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                case ZYGON -> {
                    what = "Zygons";
                    Collection<Zombie> zygons = world.getEntitiesByClass(Zombie.class);
                    for (Zombie zygon : zygons) {
                        if (zygon.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.zygon, PersistentDataType.INTEGER)) {
                            count++;
                        }
                    }
                }
                default -> {
                }
            }
        }
        sender.sendMessage(plugin.getMessagePrefix() + "There are " + count + " " + what + " in " + world.getName());
        return true;
    }
}
