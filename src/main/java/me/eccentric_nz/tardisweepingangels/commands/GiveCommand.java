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
import me.eccentric_nz.tardisweepingangels.utils.HeadBuilder;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand {

    private final TARDISWeepingAngelsPlugin plugin;

    public GiveCommand(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean give(CommandSender sender, String[] args) {
        if (args.length < 3) {
            return false;
        }
        // get the player
        Player player = plugin.getServer().getPlayer(args[1]); // TODO Replace every applicable instance of OfflinePlayer(String) with this.
        if (player == null) {
            sender.sendMessage(plugin.getMessagePrefix() + "Player not found!");
            return true;
        }
        // check monster type
        String upper = args[2].toUpperCase();
        Monster monster;
        try {
            monster = Monster.valueOf(upper);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(plugin.getMessagePrefix() + "Invalid monster type!");
            return true;
        }
        if (monster == Monster.K9 || monster == Monster.TOCLAFANE) {
            sender.sendMessage(plugin.getMessagePrefix() + "That monster type can't be equipped as a helmet!");
            return true;
        }
        ItemStack itemStack = HeadBuilder.getItemStack(monster);
        player.getInventory().addItem(itemStack);
        player.updateInventory();
        sender.sendMessage(plugin.getMessagePrefix() + " gave " + player.getName() + " 1 " + monster.getName() + " head");
        String who = "The server";
        if (sender instanceof Player) {
            who = sender.getName();
        }
        if (!who.equals(player.getName())) {
            sender.sendMessage(plugin.getMessagePrefix() + who + " gave you 1 " + monster.getName() + " head");
        }
        return true;
    }
}
