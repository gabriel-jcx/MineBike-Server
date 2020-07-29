package edu.ics.uci.minebike.minecraft.client.AI;
import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import org.ngs.bigx.minecraft.BiGX;
import org.ngs.bigx.minecraft.context.BigxClientContext;

import java.util.*;

public class FishingAI extends QuestHeartRate {

    public String lastFish;

    public FishStatus fishRunAway = FishStatus.QUIT;

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
        if (fishRunAway == FishStatus.QUIT || lastFish==null) {
            Integer l= allFishNamesInPond.length;
            return randomFish(l);
        }
        if (avg <= p.getTargetMax() && avg >= p.getTargetMin()) {
            //smaller fish
            if (fishRunAway == FishStatus.ESCAPE) {
//                int l=current_pond.get(lastFish);

                Integer l = Arrays.asList(allFishNamesInPond).indexOf(lastFish);

                return randomFish(l);
//                String key = (String)allFishNamesinPond[random.nextInt(l)];
//                lastFish= (String)key;
//                ClientUtils.sendData(EnumPacketClient.Fish,lastFish);
//                return current_pond.get(key);
            }
            else{
                Integer l= allFishNamesInPond.length;
                return randomFish(l);
            }
        }
        //High,smaller fish
        else if (avg >= p.getTargetMax()) {
//            int l=pond.indexOf(lastFish);
            Integer l = Arrays.asList(allFishNamesInPond).indexOf(lastFish);
            return randomFish(l);
//            int i = random.nextInt(l);
//            Pair<Integer, ItemStack> r = pond.get(i);
//            lastFish=r.getValue();
//            return r;
        }
        //Low,bigger fish
        else {
//            int l=pond.indexOf(lastFish);
//            int i = random.nextInt(pond.size()-l);
//            Pair<Integer, ItemStack> r = pond.get(l+i);
//            lastFish=r.getValue();
//            return r;
            Integer l = Arrays.asList(allFishNamesInPond).indexOf(lastFish);
            Integer i= random.nextInt(allFishNamesInPond.length-l);
            return randomFish(l+i);
        }
    }
    //tell the CustomHook fish name, return the resistance to FishingQuest
    private Integer randomFish(int l) {
        System.out.println(((BigxClientContext) BiGX.instance().clientContext).resistance);
        Integer fishIndex=random.nextInt(l);
        Object key = allFishNamesInPond[random.nextInt(l)];
        lastFish= (String)key;
        ClientUtils.sendData(EnumPacketClient.Fish,lastFish);
//        ((BigxClientContext) BiGX.instance().clientContext).resistance= currentPond.get(key);
        return fishIndex;


    }

    public enum FishStatus {
        ESCAPE,
        CAUGHT,
        QUIT
    }

}

