package edu.ics.uci.minebike.minecraft.quests;

import edu.ics.uci.minebike.minecraft.npcs.customNpcs.AbstractCustomNpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public abstract class AbstractCustomQuest{
    public static EntityPlayer player = null;

    protected boolean started = false;
    protected boolean finished;
    public int DIMID;
    private long questStartTime;
    protected Vec3d questStartLocation;

    public enum Difficulty{
        EASY, MEDIUM, HARD
    }

    public AbstractCustomQuest(){

    }
    public boolean onPlayerJoin(EntityPlayer player){
        if(!started){
            setupQuestEnv(player.world, player);
            return true;
        }else{

            return false;
        }
    }
    public Vec3d getStartLocation() {return questStartLocation;};
    protected abstract void setupQuestEnv(World world, EntityPlayer player);
    public abstract void start(EntityPlayerMP player);
    public abstract void start(EntityJoinWorldEvent event);
    public abstract void end();
}
