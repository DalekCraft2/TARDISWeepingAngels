package me.eccentric_nz.tardisweepingangels.monsters.judoon;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class JudoonListener implements Listener {

    private final TARDISWeepingAngels plugin;

    public JudoonListener(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ArmorStand && event.getDamager() instanceof Player) {
            ArmorStand stand = (ArmorStand) event.getEntity();
            if (stand.getPersistentDataContainer().has(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID) && stand.getPersistentDataContainer().has(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER)) {
                event.setCancelled(true);
                Player player = (Player) event.getDamager();
                player.playSound(stand.getLocation(), "judoon", 1.0f, 1.0f);
                if (!player.hasPermission("tardisweepingangels.judoon")) {
                    return;
                }
                UUID judoonId = stand.getPersistentDataContainer().get(TARDISWeepingAngels.OWNER_UUID, TARDISWeepingAngels.PersistentDataTypeUUID);
                if (judoonId.equals(player.getUniqueId())) {
                    int ammo = stand.getPersistentDataContainer().get(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER);
                    if (Tag.SHULKER_BOXES.isTagged(player.getInventory().getItemInMainHand().getType())) {
                        // top up ammo
                        ItemStack box = player.getInventory().getItemInMainHand();
                        BlockStateMeta bsm = (BlockStateMeta) box.getItemMeta();
                        ShulkerBox shulkerBox = (ShulkerBox) bsm.getBlockState();
                        Inventory inv = shulkerBox.getInventory();
                        if (inv.contains(Material.ARROW)) {
                            int a = inv.first(Material.ARROW);
                            ItemStack arrows = inv.getItem(a);
                            ItemMeta aim = arrows.getItemMeta();
                            if (aim.hasCustomModelData() && aim.getCustomModelData() == 13) {
                                int remove = plugin.getConfig().getInt("judoon.ammunition") - ammo;
                                if (arrows.getAmount() > remove) {
                                    arrows.setAmount(arrows.getAmount() - remove);
                                } else {
                                    remove = arrows.getAmount();
                                    arrows = null;
                                }
                                inv.setItem(a, arrows);
                                shulkerBox.update();
                                bsm.setBlockState(shulkerBox);
                                box.setItemMeta(bsm);
                                stand.getPersistentDataContainer().set(TARDISWeepingAngels.JUDOON, PersistentDataType.INTEGER, (ammo + remove));
                                stand.setCustomName("Ammunition: " + (ammo + remove));
                                player.sendMessage("You reloaded " + remove + " Judoon ammunition.");
                            }
                        }
                    } else {
                        ItemStack arm = stand.getEquipment().getItemInMainHand();
                        ItemMeta im = arm.getItemMeta();
                        int cmd = im.getCustomModelData();
                        if (cmd == 4) {
                            // point weapon
                            cmd = 9;
                            stand.setCustomName("Ammunition: " + ammo);
                            stand.setCustomNameVisible(true);
                        } else {
                            // stand easy
                            cmd = 4;
                            stand.setCustomNameVisible(false);
                        }
                        im.setCustomModelData(cmd);
                        arm.setItemMeta(im);
                        stand.getEquipment().setItemInMainHand(arm);
                        if (ammo > 0) {
                            // add to repeating task
                            TARDISWeepingAngels.guards.add(stand.getUniqueId());
                        } else {
                            // end guarding task
                            player.sendMessage((cmd == 9) ? "Your Judoon has no ammunition!" : "Judoon standing at ease.");
                            TARDISWeepingAngels.guards.remove(stand.getUniqueId());
                        }
                    }
                }
            }
        }
    }
}
