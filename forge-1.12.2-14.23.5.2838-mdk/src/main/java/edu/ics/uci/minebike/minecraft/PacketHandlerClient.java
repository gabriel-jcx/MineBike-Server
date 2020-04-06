package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

public class PacketHandlerClient extends PacketHandlerServer{
    public PacketHandlerClient(){

    }
//    @SubscribeEvent
//    public void onWatever(TickEvent.WorldTickEvent event){
//        System.out.println("this is registered properly");
//    }
    @SubscribeEvent
    public void onPacketData(FMLNetworkEvent.ClientCustomPacketEvent event){
        System.out.println("Client got a custom packet from Server!");
        if(event.side().isServer()) {
            System.err.println("Somehow this ClientCustomPacketEvent is triggered at server???");
            return;
        }
        ByteBuf buffer = event.getPacket().payload();
        EnumPacketServer type = EnumPacketServer.values()[buffer.readInt()];
        System.out.println("Enum is " + type.toString());
        if(type == EnumPacketServer.SoccerQueueingTime){
            String waitingTime = readString(buffer);
            System.out.println("Current Waiting Time is " + waitingTime);
        }
        //EntityPlayer player = Minecraft.getMinecraft().player; // get the client side of the player
//        if(player != null){
//            ByteBuf buffer = event.getPacket().payload(); // get the packet payload
//        }
    }
}
