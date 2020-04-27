package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import noppes.npcs.LogWriter;
import noppes.npcs.Server;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static noppes.npcs.Server.writeString;

public class ClientUtils {

    // Send data to the Server
    public static void sendData(EnumPacketClient num, Object... objs){
        PacketBuffer buffer = new PacketBuffer((Unpooled.buffer()));

        try {
            if(!CommonUtils.fillBuffer(buffer, num, objs)){
                return;
            }
            BiGXMain.ChannelPlayer.sendToServer(new FMLProxyPacket(buffer, "MineBikeClient"));
        } catch(IOException except){
            except.printStackTrace();
        }
    }

}
