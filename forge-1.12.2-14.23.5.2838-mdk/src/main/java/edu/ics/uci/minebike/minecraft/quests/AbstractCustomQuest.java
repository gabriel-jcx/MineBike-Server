package edu.ics.uci.minebike.minecraft.quests;

import edu.ics.uci.minebike.minecraft.npcs.customNpcs.AbstractCustomNpc;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractCustomQuest {
    public static EntityPlayer player = null;

    protected boolean started;
    protected boolean finished;

    private long questStartTime;

    public enum Difficulty{
        EASY, MEDIUM, HARD
    }

    public AbstractCustomQuest(){

    }
    public boolean onPlayerJoin(EntityPlayer player){
        if(player == null){

            return true;
        }else{

            return false;
        }
    }
    public abstract void setupQuestEnv();
    public abstract void start();
    public abstract void end();
}
