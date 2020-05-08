package edu.ics.uci.minebike.minecraft;

import com.google.common.base.Charsets;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import edu.ics.uci.minebike.minecraft.item.CustomHook;
import edu.ics.uci.minebike.minecraft.item.ItemGameFishingRod;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
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
//    @SubscribeEvent
//    public void onPacketData(FMLNetworkEvent.ClientCustomPacketEvent event){
//        System.out.println("Client got a custom packet from Server!");
//        if(event.side().isServer()) {
//            System.err.println("Somehow this ClientCustomPacketEvent is triggered at server???");
//            return;
//        }
//        ByteBuf buffer = event.getPacket().payload();
//        EnumPacketServer type = EnumPacketServer.values()[buffer.readInt()];
//        System.out.println("Enum is " + type.toString());
//        if(type == EnumPacketServer.SoccerQueueingTime){
//            String waitingTime = readString(buffer);
//            System.out.println("Current Waiting Time is " + waitingTime);
//        }
        //EntityPlayer player = Minecraft.getMinecraft().player; // get the client side of the player
//        if(player != null){
//            ByteBuf buffer = event.getPacket().payload(); // get the packet payload
//        }

    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event){

        EntityPlayerMP player = ((NetHandlerPlayServer)event.getHandler()).player;
        ByteBuf buffer = event.getPacket().payload();
        int integer = buffer.readInt();
        System.out.println("I got a custom packet from the client " + player.getName() + "!!!!");
        System.out.println("Enum of the packet is: " + integer);
        //buffer.readInt();
//        System.out.println(buffer.readInt());
        EnumPacketClient num = EnumPacketClient.values()[integer];
        System.out.println("Enum is " + num.toString());
        if(num == EnumPacketClient.PlayerJoin) {
            String questNum = CommonUtils.readString(buffer);
            System.out.println("questNum = " + questNum);
            int a = Integer.parseInt(questNum);
            System.out.println("a = " + a);
            CustomQuestManager.customQuests.get(Integer.parseInt(questNum)).onPlayerJoin(player);
        }else if (num == EnumPacketClient.FishingDistance)
        {
            CustomHook.distance= buffer.readInt();
        }
        else if (num == EnumPacketClient.Fish)
        {

            CustomHook.current_fish= buffer;
        }
        //System.out.println(readString(buffer));
        // teleport and start the quest here!!!!
    }


}
