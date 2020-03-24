package edu.ics.uci.minebike.minecraft;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

public class PacketHandlerClient extends PacketHandlerServer{
    public PacketHandlerClient(){

    }
    @SubscribeEvent
    public void onPacketData(FMLNetworkEvent.ClientCustomPacketEvent event){
        EntityPlayer player = Minecraft.getMinecraft().player; // get the client side of the player
        if(player != null){
            ByteBuf buffer = event.getPacket().payload(); // get the packet payload
        }
    }
}
