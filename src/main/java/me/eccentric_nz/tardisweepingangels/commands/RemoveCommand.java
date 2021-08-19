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
import me.eccentric_nz.tardisweepingangels.utils.ArmorStandFinder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class RemoveCommand {

    private final TARDISWeepingAngelsPlugin plugin;

    public RemoveCommand(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean remove(CommandSender sender) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (player == null) {
            sender.sendMessage(plugin.getMessagePrefix() + "Command can only be used by a player!");
            return true;
        }
        UUID uuid = player.getUniqueId();
        if (plugin.getFollowTasks().containsKey(uuid)) {
            player.sendMessage(plugin.getMessagePrefix() + "Please tell your follower to stay before removing it! /twa stay");
            return true;
        }
        ArmorStand armorStand = ArmorStandFinder.getStand(player);
        if (armorStand == null || !armorStand.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.ownerUuid, TARDISWeepingAngelsPlugin.persistentDataTypeUuid)) {
            player.sendMessage(plugin.getMessagePrefix() + "You are not looking at a TARDISWeepingAngels entity!");
        } else {
            if (armorStand.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.judoon, PersistentDataType.INTEGER) && !player.hasPermission("tardisweepingangels.remove.judoon")) {
                player.sendMessage(plugin.getMessagePrefix() + "You don't have permission to remove a Judoon!");
                return true;
            } else if (armorStand.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.k9, PersistentDataType.INTEGER) && !player.hasPermission("tardisweepingangels.remove.k9")) {
                player.sendMessage(plugin.getMessagePrefix() + "You don't have permission to remove K9!");
                return true;
            } else if (armorStand.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.ood, PersistentDataType.INTEGER) && !player.hasPermission("tardisweepingangels.remove.ood")) {
                player.sendMessage(plugin.getMessagePrefix() + "You don't have permission to remove an Ood!");
                return true;
            }
            UUID storedUuid = armorStand.getPersistentDataContainer().get(TARDISWeepingAngelsPlugin.ownerUuid, TARDISWeepingAngelsPlugin.persistentDataTypeUuid);
            if (storedUuid != null && storedUuid.equals(uuid)) {
                armorStand.remove();
            } else {
                player.sendMessage(plugin.getMessagePrefix() + "That is not your TARDISWeepingAngels entity!");
            }
        }
        return true;
    }
}
