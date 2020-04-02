package edu.ics.uci.minebike.minecraft;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.NoppesUtilPlayer;

public class ServerUtils {
    //@SideOnly(Side.CLIENT)
    public static void telport(EntityPlayerMP player, Vec3d pos, int dimID){
        NoppesUtilPlayer.teleportPlayer(player,pos.x,pos.y,pos.z,dimID);
        //playerSP.sendChatMessage("/tpx "+ dimID + " " + pos.x + " " + pos.y + " " + pos.z );
    }
}
