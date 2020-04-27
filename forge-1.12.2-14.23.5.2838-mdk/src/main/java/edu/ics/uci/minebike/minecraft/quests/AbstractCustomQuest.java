package edu.ics.uci.minebike.minecraft.quests;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractCustomQuest{
    public static EntityPlayer player = null;

    protected boolean isStarted = false;
    protected boolean isFinished = false;
    public int DIMID;
    private long questStartTime;
    public Vec3d questStartLocation;

    public enum Difficulty{
        EASY, MEDIUM, HARD
    }

    public AbstractCustomQuest(){

    }
    public boolean onPlayerJoin(EntityPlayer player){
        if(!isStarted){
            setupQuestEnv(player.world, player);
            return true;
        }else{

            return false;
        }
    }
    public Vec3d getStartLocation() {return questStartLocation;};
    protected abstract void setupQuestEnv(World world, EntityPlayer player);
    @SideOnly(Side.SERVER)
    public abstract void start(EntityPlayerMP player);
    @SideOnly(Side.CLIENT)
    public abstract void start(EntityPlayerSP player);
    public abstract void start(EntityJoinWorldEvent event);
    public abstract void start(); // This is the start interface for client
    public abstract void end();


    public abstract void onWorldTick(TickEvent.WorldTickEvent event);
    public abstract void onPlayerTick(TickEvent.PlayerTickEvent event);
}
