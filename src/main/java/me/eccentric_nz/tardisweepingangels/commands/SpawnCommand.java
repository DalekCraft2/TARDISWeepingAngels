package me.eccentric_nz.tardisweepingangels.commands;

import me.eccentric_nz.tardischunkgenerator.TARDISHelper;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelSpawnEvent;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngels;
import me.eccentric_nz.tardisweepingangels.utils.Monster;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class SpawnCommand implements CommandExecutor {

    private final TARDISWeepingAngels plugin;
    private final Set<Material> trans = null;

    public SpawnCommand(TARDISWeepingAngels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("twas")) {
            if (args.length == 0) {
                return false;
            }
            String upper = args[0].toUpperCase();
            if (upper.equals("OOD")) {
                sender.sendMessage(plugin.pluginName + "Please use the '/ood spawn' command to make an Ood!");
                return true;
            }
            // check monster type
            Monster monster;
            try {
                monster = Monster.valueOf(upper);
            } catch (IllegalArgumentException e) {
                sender.sendMessage(plugin.pluginName + "Invalid monster type!");
                return true;
            }
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage(plugin.pluginName + "Command can only be used by a player!");
                return true;
            }
            Location eyeLocation = player.getTargetBlock(trans, 50).getLocation();
            eyeLocation.setX(eyeLocation.getX() + 0.5F);
            eyeLocation.setY(eyeLocation.getY() + 1);
            eyeLocation.setZ(eyeLocation.getZ() + 0.5F);
            World world = eyeLocation.getWorld();
            switch (monster) {
                case ANGEL:
                case WEEPING_ANGEL:
                    LivingEntity a = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                    a.setSilent(true);
                    a.setNoDamageTicks(75);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setAngelEquipment(a, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(a, EntityType.SKELETON, Monster.WEEPING_ANGEL, eyeLocation));
                    }, 5L);
                    break;
                case CYBERMAN:
                    LivingEntity c = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                    c.setSilent(true);
                    c.setNoDamageTicks(75);
                    Zombie cyber = (Zombie) c;
                    cyber.setBaby(false);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setCyberEquipment(c, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(c, EntityType.ZOMBIE, Monster.CYBERMAN, eyeLocation));
                    }, 5L);
                    break;
                case DALEK:
                    LivingEntity d = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                    d.setSilent(true);
                    d.setNoDamageTicks(75);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setDalekEquipment(d, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(d, EntityType.SKELETON, Monster.DALEK, eyeLocation));
                        if (args.length > 1 && args[1].equalsIgnoreCase("flying") && plugin.getServer().getPluginManager().isPluginEnabled("TARDISChunkGenerator")) {
                            TARDISHelper tardisHelper = (TARDISHelper) plugin.getServer().getPluginManager().getPlugin("TARDISChunkGenerator");
                            // make the Dalek fly
                            EntityEquipment ee = d.getEquipment();
                            ee.setChestplate(new ItemStack(Material.ELYTRA, 1));
                            // teleport them straight up
                            d.teleport(d.getLocation().add(0.0d, 20.0d, 0.0d));
                            tardisHelper.setFallFlyingTag(d);
                        }
                    }, 2L);
                    break;
                case ICE:
                case ICE_WARRIOR:
                case WARRIOR:
                    LivingEntity i = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.PIG_ZOMBIE);
                    i.setSilent(true);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setWarriorEquipment(i, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(i, EntityType.PIG_ZOMBIE, Monster.ICE_WARRIOR, eyeLocation));
                    }, 5L);
                    PigZombie pigman = (PigZombie) i;
                    pigman.setBaby(false);
                    pigman.setAngry(true);
                    pigman.setAnger(Integer.MAX_VALUE);
                    break;
                case CHILD:
                case EMPTY:
                case EMPTY_CHILD:
                    LivingEntity e = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                    e.setSilent(true);
                    e.setNoDamageTicks(75);
                    Zombie child = (Zombie) e;
                    child.setBaby(true);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setEmptyChildEquipment(e, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(e, EntityType.ZOMBIE, Monster.EMPTY_CHILD, eyeLocation));
                    }, 5L);
                case SILENT:
                    LivingEntity l = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ENDERMAN);
                    l.setSilent(true);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setSilentEquipment(l);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(l, EntityType.ENDERMAN, Monster.SILENT, eyeLocation));
                    }, 5L);
                    break;
                case SILURIAN:
                    LivingEntity s = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.SKELETON);
                    s.setSilent(true);
                    s.setNoDamageTicks(75);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setSilurianEquipment(s, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(s, EntityType.SKELETON, Monster.SILURIAN, eyeLocation));
                    }, 5L);
                    break;
                case SONTARAN:
                    LivingEntity o = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                    o.setSilent(true);
                    o.setNoDamageTicks(75);
                    Zombie sontaran = (Zombie) o;
                    sontaran.setBaby(false);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setSontaranEquipment(o, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(o, EntityType.ZOMBIE, Monster.SONTARAN, eyeLocation));
                    }, 5L);
                    break;
                case STRAX:
                    LivingEntity x = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.PIG_ZOMBIE);
                    x.setSilent(true);
                    x.setNoDamageTicks(75);
                    PigZombie strax = (PigZombie) x;
                    strax.setBaby(false);
                    strax.setAngry(false);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setButlerEquipment(x, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(x, EntityType.PIG_ZOMBIE, Monster.STRAX, eyeLocation));
                    }, 5L);
                    break;
                case VASHTA:
                case VASHTA_NERADA:
                    LivingEntity v = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                    v.setSilent(true);
                    v.setNoDamageTicks(75);
                    Zombie vashta = (Zombie) v;
                    vashta.setBaby(false);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setVashtaNeradaEquipment(v, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(v, EntityType.ZOMBIE, Monster.VASHTA_NERADA, eyeLocation));
                    }, 5L);
                    break;
                case ZYGON:
                    LivingEntity z = (LivingEntity) world.spawnEntity(eyeLocation, EntityType.ZOMBIE);
                    z.setSilent(true);
                    z.setNoDamageTicks(75);
                    Zombie zygon = (Zombie) z;
                    zygon.setBaby(false);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        TARDISWeepingAngels.getEqipper().setZygonEquipment(z, false);
                        plugin.getServer().getPluginManager().callEvent(new TARDISWeepingAngelSpawnEvent(z, EntityType.ZOMBIE, Monster.ZYGON, eyeLocation));
                    }, 5L);
                    break;
            }
            return true;
        }
        return false;
    }
}
