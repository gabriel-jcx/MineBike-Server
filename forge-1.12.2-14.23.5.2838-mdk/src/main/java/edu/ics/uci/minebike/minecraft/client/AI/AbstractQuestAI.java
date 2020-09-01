package edu.ics.uci.minebike.minecraft.client.AI;

import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import io.netty.util.internal.ObjectUtil;
import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;

public abstract class AbstractQuestAI {

    private boolean isStarted;
    private boolean targetHRReached;
    private String QuestName;
    private int minTargetHR;
    private int maxTargetHR;
    private int remainingTargetTime; // in terms of seconds
    private int ongoingTime; // needs a better name!
    private int totalTargetTime;

    protected float questAvgHR;
    protected BiGXPatientPrescription patientPrescription;

    public AbstractQuestAI(){
        isStarted = false;
        targetHRReached = false;
        QuestName = null;

        patientPrescription = new BiGXPatientPrescription();
        // here need to read set data and from the BiGXPatientPrescription
        // Params to be filled: min/max Target HR, totalTargetTime,

        remainingTargetTime = totalTargetTime;
        questAvgHR = 0; // init to zero
    }

    public int getRemainingTime_Sec(){
        return remainingTargetTime;
    }
    public boolean isTargetHRReached(){
        return targetHRReached;
    }

    public boolean start(){
        OuterAI temp = OuterAI.getInstance();
        if(temp.setRunningQuest(this) == false){
            return false;
        }
        return true;
    }

    protected int getMaxTargetHR(){
        return this.maxTargetHR;
    }
    protected int getMinTargetHR(){
        return this.minTargetHR;
    }

    public boolean end(){
        if(OuterAI.getInstance().setRunningQuest(null)){
            return true;
        }
        return false;
    }


}
