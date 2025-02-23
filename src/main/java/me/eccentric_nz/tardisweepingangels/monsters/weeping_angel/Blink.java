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
package me.eccentric_nz.tardisweepingangels.monsters.weeping_angel;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsPlugin;
import me.eccentric_nz.tardisweepingangels.utils.Vector3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Blink implements Listener {

    private final TARDISWeepingAngelsPlugin plugin;
    private final List<String> message = new ArrayList<>();

    public Blink(TARDISWeepingAngelsPlugin plugin) {
        this.plugin = plugin;
        message.add("Don't blink. Blink and you're dead.");
        message.add("They are fast. Faster than you can believe.");
        message.add("Don't turn your back. Don't look away.");
        message.add("And don't blink. Good Luck.");
    }

    public static boolean hasIntersection(Vector3D point1, Vector3D point2, Vector3D min, Vector3D max) {
        double epsilon = 0.0001f;
        Vector3D d = point2.subtract(point1).multiply(0.5); // TODO Figure out what these three variables are and rename them.
        Vector3D e = max.subtract(min).multiply(0.5);
        Vector3D c = point1.add(d).subtract(min.add(max).multiply(0.5));
        Vector3D ad = d.abs();
        if (Math.abs(c.x) > e.x + ad.x) {
            return false;
        }
        if (Math.abs(c.y) > e.y + ad.y) {
            return false;
        }
        if (Math.abs(c.z) > e.z + ad.z) {
            return false;
        }
        if (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + epsilon) {
            return false;
        }
        if (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + epsilon) {
            return false;
        }
        return Math.abs(d.x * c.y - d.y * c.x) <= e.x * ad.y + e.y * ad.x + epsilon;
    }

    // TODO Make Weeping Angels freeze whilst being watched, regardless of whether a player is sneaking.
    @EventHandler(priority = EventPriority.NORMAL)
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Location observerPose = player.getEyeLocation();
        Vector3D observerDirection = new Vector3D(observerPose.getDirection());
        Vector3D observerStart = new Vector3D(observerPose);
        Vector3D observerEnd = observerStart.add(observerDirection.multiply(16));

        Skeleton skeleton = null;
        // Get nearby entities
        for (Skeleton target : player.getWorld().getEntitiesByClass(Skeleton.class)) {
            // Bounding box of the given player
            Vector3D targetPos = new Vector3D(target.getLocation());
            Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
            Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);
            if (hasIntersection(observerStart, observerEnd, minimum, maximum)) {
                if (skeleton == null || skeleton.getLocation().distanceSquared(observerPose) > target.getLocation().distanceSquared(observerPose)) {
                    // is it an angel?
                    EntityEquipment entityEquipment = target.getEquipment();
                    if (entityEquipment.getHelmet().getType().equals(Material.BRICK) || entityEquipment.getHelmet().getType().equals(Material.STONE_BUTTON)) {
                        skeleton = target;
                    }
                }
            }
        }
        // freeze the closest skeleton
        if (skeleton != null) {
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, plugin.getConfig().getInt("angels.freeze_time"), 30, true, false));
            if (!player.isSneaking()) {
                player.sendMessage(plugin.getMessagePrefix() + message.get(TARDISWeepingAngelsPlugin.random.nextInt(4)));
            }
        }
    }
}
