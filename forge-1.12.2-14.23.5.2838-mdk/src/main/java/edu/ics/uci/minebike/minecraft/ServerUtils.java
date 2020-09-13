package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import edu.ics.uci.minebike.minecraft.utils.MineBikeScheduler;
import io.netty.buffer.Unpooled;
import net.doubledoordev.d3commands.util.DimChanger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.NoppesUtilPlayer;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ServerUtils {
    //@SideOnly(Side.CLIENT)
    synchronized public static void telport(EntityPlayerMP player, Vec3d pos, int dimID){
        DimChanger.changeDim(player, dimID);
        player.connection.setPlayerLocation(pos.x,pos.y,pos.z,player.rotationYaw,player.rotationPitch);

        //player.
//        WorldServer ws = DimensionManager.getWorld(dimID);
//        ws.
//        NoppesUtilPlayer.teleportPlayer(player,pos.x,pos.y,pos.z,dimID);
        //playerSP.sendChatMessage("/tpx "+ dimID + " " + pos.x + " " + pos.y + " " + pos.z );
    }
    public static void sendQuestData(EnumPacketServer type, EntityPlayerMP player, Object... objs){
        sendQuestDataDelayed(player, type, 0, objs);
    }
    private static void sendQuestDataDelayed(EntityPlayerMP playerMP, EnumPacketServer type, int delay, Object... obs){
        MineBikeScheduler.runTask(()->{
            PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
            try{
                if(!CommonUtils.fillBuffer(buffer,type,obs)){
                    return;
                }
                System.out.println("Send: " + type + " to " + playerMP.getName() + " at " + playerMP.getPlayerIP());
                BiGXMain.Channel.sendTo(new FMLProxyPacket(buffer, "MineBikeServer"), playerMP);
            } catch(IOException var5){
                var5.printStackTrace();
                //printStackTrace;
            }
        },delay);
    }
    public static EntityPlayer getPlayer(MinecraftServer server, UUID uuid){
        List<EntityPlayerMP> list = server.getPlayerList().getPlayers();

        // NOTE: use iterator to iterate over list and find Player based on uuid

        return null;

    }
}