package edu.ics.uci.minebike.minecraft.quests;

import edu.ics.uci.minebike.minecraft.npcs.customNpcs.AbstractCustomNpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class AbstractCustomQuest{
    public static EntityPlayer player = null;

    protected boolean started = false;
    protected boolean finished;

    private long questStartTime;

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
    protected abstract void setupQuestEnv(World world, EntityPlayer player);
    protected abstract void start();
    protected abstract void end();
}
