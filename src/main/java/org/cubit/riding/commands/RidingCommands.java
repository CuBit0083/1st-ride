package org.cubit.riding.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.cubit.riding.manager.RidingManager;
import org.cubit.riding.packet.PacketInjector;
import org.cubit.riding.riding.RidingInfo;

public class RidingCommands implements CommandExecutor {

    private JavaPlugin plugin;
    private RidingManager ridingManager;
    private PacketInjector packetInjector;

    public RidingCommands(JavaPlugin plugin, RidingManager ridingManager, PacketInjector packetInjector) {
        this.plugin = plugin;
        this.ridingManager = ridingManager;
        this.packetInjector = packetInjector;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("usage: /type create [name] [durability] [speed]");
            sender.sendMessage("usage: /type delete [name]");
            sender.sendMessage("usage: /riding add [playername ][name]");

            sender.sendMessage("usage: /riding info");
            sender.sendMessage("usage: /riding spawn [name]");
            return true;
        }

        switch (args[0])
        {
            case "create":
                this.createType(sender , args);
                break;
            case "delete":
                this.deleteType(sender, args);
                break;
            case "add":
                this.addRidingType(sender, args);
                break;
            case "spawn":
                this.spawnRiding(sender, args);
                break;
        }
        return true;
    }

    private void createType(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        if (args[1] != null && args[2] != null && args[3] != null) {
            if (this.ridingManager.getRidingType(args[1]) != null) {
                sender.sendMessage("there is a riding entity that has already been created");
                return;
            }
            this.ridingManager.createRidingType(args[1] , Short.parseShort(args[2]) , Double.parseDouble(args[3]));
            sender.sendMessage("create success: " + args[1] + " , " + args[2] + " , " + args[3]);
        } else {
            sender.sendMessage("create failure: please check the command properly");

        }
    }

    private void deleteType(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        if (args[1] != null) {
            if (this.ridingManager.getRidingType(args[1]) == null) {
                sender.sendMessage("riding objects that have not been created cannot be deleted");
                return;
            }
            this.ridingManager.removeRidingType(args[1]);
            sender.sendMessage("delete success: " + args[1]);
        } else {
            sender.sendMessage("delete failed: please check the command correctly");

        }
    }

    private void addRidingType(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        if (args[1] != null && args[2] != null) {
            if (Bukkit.getPlayer(args[1]) == null || this.ridingManager.getRidingType(args[2]) == null) {
                System.out.println(">");
                return;
            }
            RidingInfo ridingInfo = new RidingInfo(this.ridingManager.getRidingType(args[2]) ,  Bukkit.getPlayer(args[1]).getUniqueId());
            this.ridingManager.addRiding(ridingInfo);
            sender.sendMessage("add success: " + args[1] + " , " + args[2]);
        }else {
            sender.sendMessage("additional failure: Please check the command correctly");
        }

    }

    private void spawnRiding(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        if (args[1] != null) {
            if (this.ridingManager.getRidingInfo(((Player) sender).getUniqueId() , args[1]) == null) {
                sender.sendMessage("커맨드 싫다고 진짜 라이브러리 만들거라고 진짜 개빡친다고1");
                return;
            }
            Entity entity = ((Player) sender).getWorld().spawn(((Player) sender).getLocation() , ArmorStand.class);
            entity.setPassenger((Entity) sender);
            this.packetInjector.addPlayer((Player) sender);
            this.ridingManager.startVelocityScheduler(this.plugin , this.ridingManager.getRidingInfo(((Player) sender).getUniqueId() , args[1]));

        }else {
            sender.sendMessage("커맨드 싫다고 진짜 라이브러리 만들거라고 진짜 개빡친다고2");
        }



    }



}
