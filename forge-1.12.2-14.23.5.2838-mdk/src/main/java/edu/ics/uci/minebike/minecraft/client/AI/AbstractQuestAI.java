package edu.ics.uci.minebike.minecraft.client.AI;

import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import io.netty.util.internal.ObjectUtil;

public abstract class AbstractQuestAI {

    private boolean isStarted;

    public AbstractQuestAI(){

    }

    public boolean start(){
        OuterAI temp = OuterAI.getInstance();
        if(temp.setRunningQuest(this) == false){
            return false;
        }

        return true;
    }

    public boolean end(){
        if(OuterAI.getInstance().setRunningQuest(null)){
            return true;
        }
        return false;
    }
}
