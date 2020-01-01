package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KillCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final List<String> types = new ArrayList<>();

    public KillCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
        types.add("a");
        types.add("c");
        types.add("d");
        types.add("e");
        types.add("i");
        types.add("m");
        types.add("r");
        types.add("o");
        types.add("s");
        types.add("v");
        types.add("z");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twak")) {
            if (args.length < 2) {
                return false;
            }
            String which = args[0].toLowerCase();
            if (!types.contains(which)) {
                return false;
            }
            World w = plugin.getServer().getWorld(args[1]);
            if (w == null) {
                sender.sendMessage(plugin.pluginName + "Could not find a world with that name!");
                return true;
            }
            int count = 0;
            String what = "Angels";
            switch (which) {
                case "a":
                    Collection<Skeleton> angels = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton a : angels) {
                        EntityEquipment ee = a.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.BRICK) || ee.getHelmet().getType().equals(Material.STONE_BUTTON)) {
                            a.remove();
                            count++;
                        }
                    }
                    break;
                case "c":
                    what = "Cybermen";
                    Collection<Zombie> cybermen = w.getEntitiesByClass(Zombie.class);
                    for (Zombie c : cybermen) {
                        EntityEquipment ee = c.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.IRON_INGOT)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                                c.remove();
                                count++;
                            }
                        }
                    }
                    break;
                case "d":
                    what = "Daleks";
                    Collection<Skeleton> daleks = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton d : daleks) {
                        EntityEquipment ee = d.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.VINE)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Dalek")) {
                                d.remove();
                                count++;
                            }
                        }
                    }
                    break;
                case "e":
                    what = "Empty Children";
                    Collection<Zombie> kids = w.getEntitiesByClass(Zombie.class);
                    for (Zombie e : kids) {
                        EntityEquipment ee = e.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.SUGAR)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Empty Child")) {
                                e.remove();
                                count++;
                            }
                        }
                    }
                    break;
                case "i":
                    what = "Ice Warriors";
                    Collection<PigZombie> warriors = w.getEntitiesByClass(PigZombie.class);
                    for (PigZombie i : warriors) {
                        EntityEquipment ee = i.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.SNOWBALL)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Cyberman")) {
                                i.remove();
                                count++;
                            }
                        }
                    }
                    break;
                case "s":
                    what = "Silurians";
                    Collection<Skeleton> silurians = w.getEntitiesByClass(Skeleton.class);
                    for (Skeleton s : silurians) {
                        EntityEquipment ee = s.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.FEATHER)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Silurian")) {
                                s.remove();
                                count++;
                            }
                        }
                    }
                    break;
                case "m":
                    what = "Silence";
                    Collection<Enderman> silence = w.getEntitiesByClass(Enderman.class);
                    for (Enderman m : silence) {
                        if (!m.getPassengers().isEmpty() && m.getPassengers().get(0) != null && m.getPassengers().get(0).getType().equals(EntityType.GUARDIAN)) {
                            m.remove();
                            count++;
                        }
                    }
                    break;
                case "o":
                    what = "Sontarans";
                    Collection<Zombie> sontarans = w.getEntitiesByClass(Zombie.class);
                    for (Zombie o : sontarans) {
                        EntityEquipment ee = o.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.POTATO)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Sontaran")) {
                                o.remove();
                                count++;
                            }
                        }
                    }
                    break;
                case "r":
                    what = "Ood";
                    Collection<ArmorStand> ood = w.getEntitiesByClass(ArmorStand.class);
                    for (ArmorStand o : ood) {
                        if (o.getPersistentDataContainer().has(TARDISWeepingAngels.OOD, PersistentDataType.INTEGER)) {
                            o.remove();
                            count++;
                        }
                    }
                    break;
                case "v":
                    what = "Vashta Nerada";
                    Collection<Zombie> vashta = w.getEntitiesByClass(Zombie.class);
                    for (Zombie v : vashta) {
                        EntityEquipment ee = v.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.BOOK)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Vashta")) {
                                v.remove();
                                count++;
                            }
                        }
                    }
                    break;
                case "z":
                    what = "Zygons";
                    Collection<Zombie> zygons = w.getEntitiesByClass(Zombie.class);
                    for (Zombie z : zygons) {
                        EntityEquipment ee = z.getEquipment();
                        if (ee.getHelmet().getType().equals(Material.PAINTING)) {
                            ItemStack is = ee.getHelmet();
                            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith("Zygon")) {
                                z.remove();
                                count++;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            sender.sendMessage(plugin.pluginName + "Removed " + count + " " + what + " in " + w.getName());
            return true;
        }
        return false;
    }
}
