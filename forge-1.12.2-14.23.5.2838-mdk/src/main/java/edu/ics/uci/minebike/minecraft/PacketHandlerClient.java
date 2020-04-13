package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.quests.customQuests.SoccerQuest;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

// NOTE: Somehow this class isn't registered properly, need to
public class PacketHandlerClient {
    public PacketHandlerClient(){

    }
    @SubscribeEvent
    public void onWatever(TickEvent.WorldTickEvent event){
        System.out.println("this is registered properly");
    }
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
            String waitingTime = CommonUtils.readString(buffer);
            System.out.println("Current Waiting Time is " + waitingTime);
            SoccerQuest soccer = (SoccerQuest) CustomQuestManager.customQuests.get(222);
            soccer.clientStartWaiting(waitingTime);
        }else if(type == EnumPacketServer.QuestStart){
            String DIMID = CommonUtils.readString(buffer);
            System.out.println("Client received a packet start on quest " + DIMID);

            CustomQuestManager.findAndStart(Integer.parseInt(DIMID));
        }else if(type == EnumPacketServer.SoccerLeftScoreUpdate){
            SoccerQuest soccer = (SoccerQuest) CustomQuestManager.customQuests.get(222);
            soccer.leftScoreUpdate();
        }else if(type == EnumPacketServer.SoccerRightScoreUpdate){
            SoccerQuest soccer = (SoccerQuest) CustomQuestManager.customQuests.get(222);
            soccer.rightScoreUpdate();
        }
        //EntityPlayer player = Minecraft.getMinecraft().player; // get the client side of the player
//        if(player != null){
//            ByteBuf buffer = event.getPacket().payload(); // get the packet payload
//        }
    }
}
