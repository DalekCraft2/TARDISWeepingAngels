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
package me.eccentric_nz.tardisweepingangels.monsters.k9;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.UUID;

public class K9Follow {

    public static void run(TARDISWeepingAngelsPlugin plugin, Player player, ArmorStand armorStand, String[] args) {
        if (!player.hasPermission("tardisweepingangels.follow.k9")) {
            player.sendMessage(plugin.pluginName + "You don't have permission to make K9 follow you!");
            return;
        }
        if (armorStand.getPersistentDataContainer().has(TARDISWeepingAngelsPlugin.ownerUuid, TARDISWeepingAngelsPlugin.persistentDataTypeUuid)) {
            UUID uuid = player.getUniqueId();
            UUID k9Id = armorStand.getPersistentDataContainer().get(TARDISWeepingAngelsPlugin.ownerUuid, TARDISWeepingAngelsPlugin.persistentDataTypeUuid);
            if (k9Id.equals(uuid)) {
                double speed = (args.length == 2) ? Math.min(Double.parseDouble(args[1]) / 100.0d, 0.5d) : 0.15d;
                int taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new K9WalkRunnable(armorStand, speed, player), 2L, 2L);
                plugin.getFollowTasks().put(uuid, taskId);
            } else {
                player.sendMessage(plugin.pluginName + "That is not your K9!");
            }
        } else {
            player.sendMessage(plugin.pluginName + "That is a broken K9 :(");
        }
    }
}
