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
package me.eccentric_nz.tardisweepingangels.utils;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

/**
 * @author eccentric_nz
 */
public class Config {

    final double minVersion = 2.0d;
    private final TARDISWeepingAngelsPlugin plugin;
    private final FileConfiguration config;
    HashMap<String, List<String>> listOptions = new HashMap<>();
    HashMap<String, String> stringOptions = new HashMap<>();
    HashMap<String, Integer> intOptions = new HashMap<>();
    HashMap<String, Double> doubleOptions = new HashMap<>();
    HashMap<String, Boolean> booleanOptions = new HashMap<>();

    public Config(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        // integer
        intOptions.put("angels.freeze_time", 100);
        intOptions.put("spawn_rate.how_many", 2);
        intOptions.put("spawn_rate.how_often", 400);
        intOptions.put("spawn_rate.default_max", 0);
        intOptions.put("angels.spawn_from_chat.chance", 50);
        intOptions.put("angels.spawn_from_chat.distance_from_player", 10);
        intOptions.put("judoon.ammunition", 25);
        intOptions.put("judoon.damage", 4);
        intOptions.put("ood.spawn_from_villager", 20);
        intOptions.put("ood.spawn_from_cured", 5);
        intOptions.put("toclafane.spawn_from_bee", 5);
        // string
        stringOptions.put("angels.weapon", "DIAMOND_PICKAXE");
        // list
        listOptions.put("angels.drops", Arrays.asList("STONE", "COBBLESTONE"));
        listOptions.put("angels.teleport_worlds", Collections.singletonList("world"));
        listOptions.put("cybermen.drops", Arrays.asList("REDSTONE", "STONE_BUTTON"));
        listOptions.put("daleks.drops", Arrays.asList("SLIME_BALL", "ROTTEN_FLESH"));
        listOptions.put("empty_child.drops", Arrays.asList("COOKED_BEEF", "SUGAR"));
        listOptions.put("hath.drops", Arrays.asList("SALMON", "STONE_PICKAXE"));
        listOptions.put("ice_warriors.drops", Arrays.asList("ICE", "PACKED_ICE", "SNOW_BLOCK"));
        listOptions.put("silent.drops", Arrays.asList("INK_SAC", "FLOWER_POT"));
        listOptions.put("ood.drops", Collections.singletonList("NAME_TAG"));
        listOptions.put("silurians.drops", Arrays.asList("GOLD_NUGGET", "FEATHER"));
        listOptions.put("sontarans.drops", Arrays.asList("POTATO", "POISONOUS_POTATO"));
        listOptions.put("toclafane.drops", Arrays.asList("GUNPOWDER", "HONEYCOMB"));
        listOptions.put("vashta_nerada.drops", Arrays.asList("BONE", "LEATHER"));
        listOptions.put("zygons.drops", Arrays.asList("PAINTING", "SAND"));
        // boolean
        booleanOptions.put("angels.angels_can_steal", true);
        booleanOptions.put("angels.can_build", true);
        booleanOptions.put("angels.spawn_from_chat.enabled", true);
        booleanOptions.put("cybermen.can_upgrade", true);
        booleanOptions.put("sontarans.can_tame", true);
        booleanOptions.put("judoon.guards", true);
        booleanOptions.put("judoon.can_build", true);
        booleanOptions.put("k9.can_build", true);
        booleanOptions.put("k9.by_taming", true);
        booleanOptions.put("toclafane.destroy_blocks", true);
        // float
        doubleOptions.put("config_version", minVersion);
    }

    public void updateConfig() {
        // clear the old spawn_rate settings
        if (config.contains("angels.spawn_rate")) {
            plugin.getConfig().set("angels.spawn_rate", null);
            plugin.getConfig().set("cybermen.spawn_rate", null);
            plugin.getConfig().set("daleks.spawn_rate", null);
            plugin.getConfig().set("empty_child.spawn_rate", null);
            plugin.getConfig().set("ice_warriors.spawn_rate", null);
            plugin.getConfig().set("silurians.spawn_rate", null);
            plugin.getConfig().set("sontarans.spawn_rate", null);
            plugin.getConfig().set("vashta_nerada.spawn_rate", null);
            plugin.getConfig().set("zygons.spawn_rate", null);
        }

        int i = 0;
        // int values
        for (Map.Entry<String, Integer> entry : intOptions.entrySet()) {
            if (!config.contains(entry.getKey())) {
                plugin.getConfig().set(entry.getKey(), entry.getValue());
                i++;
            }
        }
        // string values
        for (Map.Entry<String, String> entry : stringOptions.entrySet()) {
            if (!config.contains(entry.getKey())) {
                plugin.getConfig().set(entry.getKey(), entry.getValue());
                i++;
            }
        }
        // list values
        for (Map.Entry<String, List<String>> entry : listOptions.entrySet()) {
            if (!config.contains(entry.getKey())) {
                plugin.getConfig().set(entry.getKey(), entry.getValue());
                i++;
            }
        }
        // boolean values
        for (Map.Entry<String, Boolean> entry : booleanOptions.entrySet()) {
            if (!config.contains(entry.getKey())) {
                plugin.getConfig().set(entry.getKey(), entry.getValue());
                i++;
            }
        }
        // double values
        for (Map.Entry<String, Double> entry : doubleOptions.entrySet()) {
            if (!config.contains(entry.getKey())) {
                plugin.getConfig().set(entry.getKey(), entry.getValue());
                i++;
            }
        }
        // fix wrong config node name
        if (config.contains("angels.angel_tp_worlds")) {
            List<String> tpWorlds = config.getStringList("angels.angel_tp_worlds");
            plugin.getConfig().set("angels.teleport_worlds", tpWorlds);
            plugin.getConfig().set("angels.angel_tp_worlds", null);
        }
        // remove milk bucket from Sontaran drops
        List<String> sontaranOld = config.getStringList("sontarans.drops");
        if (sontaranOld.contains("MILK_BUCKET")) {
            sontaranOld.remove("MILK_BUCKET");
            sontaranOld.add("POISONOUS_POTATO");
            plugin.getConfig().set("sontarans.drops", sontaranOld);
        }
        // set POTATO_ITEM to POTATO
        if (sontaranOld.contains("POTATO_ITEM")) {
            sontaranOld.remove("POTATO_ITEM");
            sontaranOld.add("POTATO");
            plugin.getConfig().set("sontarans.drops", sontaranOld);
        }
        // set INK_SACK to INK_SAC, FLOWER_POT_ITEM to FLOWER_POT
        List<String> silentOld = config.getStringList("silent.drops");
        if (silentOld.contains("INK_SACK")) {
            silentOld.remove("INK_SACK");
            silentOld.add("INK_SAC");
            silentOld.remove("FLOWER_POT_ITEM");
            silentOld.add("FLOWER_POT");
            plugin.getConfig().set("silent.drops", silentOld);
        }
        plugin.saveConfig();
        if (i > 0) {
            plugin.getLogger().log(Level.INFO, "Added " + ChatColor.AQUA + i + ChatColor.RESET + " new items to config");
        }
    }
}
