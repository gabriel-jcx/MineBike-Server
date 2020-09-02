package edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI;
import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.client.AI.AbstractQuestAI;
import edu.ics.uci.minebike.minecraft.client.AI.Fishpond;
import edu.ics.uci.minebike.minecraft.client.AI.QuestHeartRate;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
//import org.ngs.bigx.minecraft.BiGX;
//import org.ngs.bigx.minecraft.context.BigxClientContext;

import java.util.*;

public class FishingAI extends AbstractQuestAI {

    public String lastFish;

    public FishStatus fishStatus = FishStatus.QUIT;

    public int level = 1;

    private Fishpond pond= new Fishpond();

    private HashMap<String ,Integer> currentPond;

    Random random = new Random();

    Object[] allFishNamesInPond;

    public FishingAI() {

    }

    public void selectPond(int level) {
        switch (level) {
            case 1:
                System.out.println("easy");
                currentPond = pond.getEasy();

            case 2:
                System.out.println("medium");
                currentPond = pond.getMedium();
            case 3:
                System.out.println("hard");
                currentPond = pond.getHard();
        }
        allFishNamesInPond = currentPond.keySet().toArray();
    }

    public Integer changeFish() {
        //Heart Rate reached
        if (fishStatus == FishStatus.QUIT || lastFish==null) {
            Integer l= allFishNamesInPond.length;
            return randomFish(l);
        }

        if (questAvgHR <= getMaxTargetHR() && questAvgHR >= getMinTargetHR()) {
            //smaller fish
            if (fishStatus == FishStatus.ESCAPE) {
                Integer l = Arrays.asList(allFishNamesInPond).indexOf(lastFish);
                return randomFish(l);
            }
            else{
                Integer l= allFishNamesInPond.length;
                return randomFish(l);
            }
        }
        //High hr ,smaller fish
        else if (questAvgHR >= getMaxTargetHR()) {
            Integer l = Arrays.asList(allFishNamesInPond).indexOf(lastFish);
            return randomFish(l);
        }
        //Low hr ,bigger fish
        else {
            Integer l = Arrays.asList(allFishNamesInPond).indexOf(lastFish);
            Integer i= random.nextInt(allFishNamesInPond.length-l);
            return randomFish(l+i);
        }
    }
    //tell the CustomHook fish name, return the resistance to FishingQuest
    private Integer randomFish(int l) {
//        System.out.println(((BigxClientContext) BiGX.instance().clientContext).resistance);
        Integer fishIndex=random.nextInt(l);
        Object key = allFishNamesInPond[random.nextInt(l)];
        lastFish= (String)key;
        ClientUtils.sendData(EnumPacketClient.Fish,lastFish);
        return fishIndex;


    }
    public String getQuestName(){
        return "fishingQuest";
    }
    public enum FishStatus {
        ESCAPE,
        CAUGHT,
        QUIT
    }

}

