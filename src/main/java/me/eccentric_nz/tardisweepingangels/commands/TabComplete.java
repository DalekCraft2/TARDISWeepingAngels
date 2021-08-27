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

import com.google.common.collect.ImmutableList;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * TabCompleter
 */
public class TabComplete implements TabCompleter {

    private final TARDISWeepingAngelsPlugin plugin;
    private final ImmutableList<String> onOffSubs = ImmutableList.of("on", "off");
    private final ImmutableList<String> worldSubs;
    private final ImmutableList<String> monsterSubs;
    ImmutableList<String> rootSubs = ImmutableList.of("spawn", "equip", "disguise", "kill", "count", "follow", "stay", "remove", "set", "give");

    public TabComplete(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        List<String> temp = new ArrayList<>();
        for (Monster monster : Monster.values()) {
            temp.add(monster.toString().toLowerCase());
        }
        monsterSubs = ImmutableList.copyOf(temp);
        List<String> worlds = new ArrayList<>();
        this.plugin.getServer().getWorlds().forEach((world) -> worlds.add(world.getName()));
        worldSubs = ImmutableList.copyOf(worlds);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            return partial(args[0], rootSubs);
        } else if (args.length == 2) {
            if (args[0].equals("give")) {
                return null;
            } else {
                return partial(args[1], monsterSubs);
            }
        } else if (args.length == 3) {
            return switch (args[0]) {
                case "disguise" -> partial(args[2], onOffSubs);
                case "give" -> partial(args[2], monsterSubs);
                case "follow" -> Collections.singletonList("15");
                default -> partial(args[2], worldSubs);
            };
        }
        return ImmutableList.of();
    }

    private List<String> partial(String token, Collection<String> from) {
        return StringUtil.copyPartialMatches(token, from, new ArrayList<>(from.size()));
    }
}
