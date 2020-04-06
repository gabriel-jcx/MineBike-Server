package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.NoppesUtilPlayer;

import java.util.List;
import java.util.UUID;

public class ServerUtils {
    //@SideOnly(Side.CLIENT)
    public static void telport(EntityPlayerMP player, Vec3d pos, int dimID){
        NoppesUtilPlayer.teleportPlayer(player,pos.x,pos.y,pos.z,dimID);
        //playerSP.sendChatMessage("/tpx "+ dimID + " " + pos.x + " " + pos.y + " " + pos.z );
    }
    public static void sendQuestData(EnumPacketServer type, EntityPlayerMP player, String name){

    }
    public static EntityPlayer getPlayer(MinecraftServer server, UUID uuid){
        List<EntityPlayerMP> list = server.getPlayerList().getPlayers();

        // NOTE: use iterator to iterate over list and find Player based on uuid

        return null;

    }
}
