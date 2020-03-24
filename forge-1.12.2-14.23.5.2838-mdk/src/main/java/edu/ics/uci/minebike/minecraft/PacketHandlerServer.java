package edu.ics.uci.minebike.minecraft;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class PacketHandlerServer {
    public PacketHandlerServer(){


    }
    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event){
        EntityPlayerMP player = ((NetHandlerPlayServer)event.getHandler()).player;

    }

}
