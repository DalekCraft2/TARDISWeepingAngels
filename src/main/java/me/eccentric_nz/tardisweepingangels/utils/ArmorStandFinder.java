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

import me.eccentric_nz.tardisweepingangels.monsters.weeping_angel.Blink;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ArmorStandFinder {

    public static ArmorStand getStand(Player player) {
        // get the armour stand player is looking at
        Location observerPose = player.getEyeLocation();
        Vector3D observerDirection = new Vector3D(observerPose.getDirection());
        Vector3D observerStart = new Vector3D(observerPose);
        Vector3D observerEnd = observerStart.add(observerDirection.multiply(16));
        // Get nearby entities
        for (Entity target : player.getNearbyEntities(8.0d, 8.0d, 8.0d)) {
            // Bounding box of the given player
            Vector3D targetPosition = new Vector3D(target.getLocation());
            Vector3D minimum = targetPosition.add(-0.5, 0, -0.5);
            Vector3D maximum = targetPosition.add(0.5, 1.67, 0.5);
            if (target.getType().equals(EntityType.ARMOR_STAND) && Blink.hasIntersection(observerStart, observerEnd, minimum, maximum)) {
                return (ArmorStand) target;
            }
        }
        return null;
    }
}
