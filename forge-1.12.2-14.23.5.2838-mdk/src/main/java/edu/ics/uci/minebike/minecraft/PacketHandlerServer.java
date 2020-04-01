package edu.ics.uci.minebike.minecraft;

import com.google.common.base.Charsets;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
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
        if(num == EnumPacketClient.QuestStart) {
            CustomQuestManager.customQuests.get("fishing").start(player);
        }
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
