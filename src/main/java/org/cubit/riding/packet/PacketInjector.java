package org.cubit.riding.packet;

import io.netty.channel.Channel;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.cubit.riding.manager.RidingManager;

public class PacketInjector {

    private RidingManager ridingManager;

    public PacketInjector(RidingManager ridingManager) {
        this.ridingManager = ridingManager;
    }

    public void addPlayer(Player p) {
        try {
            Channel ch = ((CraftPlayer) p).getHandle().playerConnection.networkManager.channel;
            if(ch.pipeline().get("PacketInjector") == null) {
                PacketHandler h = new PacketHandler(p.getUniqueId() , ridingManager);
                ch.pipeline().addBefore("packet_handler", "PacketInjector", h);
            }
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    public void removePlayer(Player p) {
        try {
            Channel ch = ((CraftPlayer) p).getHandle().playerConnection.networkManager.channel;
            if(ch.pipeline().get("PacketInjector") != null) {
                ch.pipeline().remove("PacketInjector");
            }
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
}
