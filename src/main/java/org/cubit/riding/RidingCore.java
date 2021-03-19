package org.cubit.riding;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.cubit.riding.commands.RidingCommands;
import org.cubit.riding.manager.RidingManager;
import org.cubit.riding.packet.PacketInjector;

public class RidingCore extends JavaPlugin {

    private RidingManager ridingManager;
    private PacketInjector packetInjector;
    private RidingCommands ridingCommands;

    @Override
    public void onEnable() {
        this.ridingManager = new RidingManager();
        this.packetInjector = new PacketInjector(this.ridingManager);
        this.ridingCommands = new RidingCommands(this , this.ridingManager , this.packetInjector);
        Bukkit.getPluginCommand("riding").setExecutor(this.ridingCommands);
    }
}
