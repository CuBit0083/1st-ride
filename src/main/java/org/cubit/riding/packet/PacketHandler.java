package org.cubit.riding.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_12_R1.PacketPlayInSteerVehicle;
import org.cubit.riding.manager.RidingManager;

import java.util.UUID;

public class PacketHandler extends ChannelDuplexHandler {
    private UUID uuid;
    private RidingManager ridingManager;

    public PacketHandler(UUID uuid , RidingManager ridingManager) {
        this.uuid = uuid;
        this.ridingManager = ridingManager;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext c, Object m) throws Exception {
        if(m.getClass().getSimpleName().equals("PacketPlayInSteerVehicle")) {
            PacketPlayInSteerVehicle packet = (PacketPlayInSteerVehicle) m;
           this.ridingManager.addUserKey(uuid ,  packet.b());

        }
        super.channelRead(c, m);
    }
}
