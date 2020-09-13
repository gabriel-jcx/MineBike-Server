package edu.ics.uci.minebike.minecraft.npcs;

import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.NoppesUtilPlayer;

public abstract class AbstractCustomNpc {
    protected String name;
    protected Vec3d location;
    protected String texture;
    protected String UUID  = "";
    public boolean isSpawned = false;
    //important that this method is called after the instantiation of the object
    public void register() {
    //    NpcDatabase.registerNpc(name, location);
    }

    public abstract void onInteraction(EntityPlayer player, PlayerInteractEvent.EntityInteract event);

    public Vec3d getLocation() {return location;}
    public String getName() {return name;}
    public String getTexture() {return texture;}
    public void setUUID(String UUID){
        this.UUID = UUID;
    }
    public String getUUID(){return UUID;}

}
