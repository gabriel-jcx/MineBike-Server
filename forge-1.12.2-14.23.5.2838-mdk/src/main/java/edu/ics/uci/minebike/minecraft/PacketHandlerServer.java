package edu.ics.uci.minebike.minecraft;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import java.nio.ByteBuffer;

public class PacketHandlerServer {
    public PacketHandlerServer(){


    }
    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event){
        System.out.println("I got a custom packet from the client!!!");
        EntityPlayerMP player = ((NetHandlerPlayServer)event.getHandler()).player;
        ByteBuf buffer = event.getPacket().payload();
        //buffer.readInt();
        System.out.println(buffer.readInt());
        System.out.println(readString(buffer));
        // teleport and start the quest here!!!!
    }
    public static String readString(ByteBuf buffer) {
        try {
            byte[] bytes = new byte[buffer.readInt()];
            buffer.readBytes(bytes);
            return new String(bytes, Charsets.UTF_8);
        } catch (IndexOutOfBoundsException var2) {
            return null;
        }
    }

}
