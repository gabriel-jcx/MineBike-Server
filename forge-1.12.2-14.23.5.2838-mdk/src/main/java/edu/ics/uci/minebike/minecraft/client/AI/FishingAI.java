package edu.ics.uci.minebike.minecraft.client.AI;
import com.teammetallurgy.aquaculture.items.AquacultureItems;
import com.teammetallurgy.aquaculture.items.ItemFish;
import edu.ics.uci.minebike.minecraft.item.Fishpond;
import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

import static java.lang.Float.sum;

public class FishingAI extends QuestHeartRate {

    public static ItemStack last_fish;
    //    public List<Float> heart_rate_per_fish;
//    private float avg_hr_last_fish;
    //2 retract(random), 0 false, 1 true
    public int fish_run_away = 2;
    public boolean hr_goal_reached = false;
    public int level = 1;
    List<Pair<Integer, ItemStack>> pond;
    Random random = new Random();

    public FishingAI() {

    }

    public FishingAI(float h) {

    }
//    public void add_heart_rate(float h){
//        heart_rate.add(h);
//    }

    public void select_pond(int level) {
        switch (level) {
            case 1:
                System.out.println("easy");
                pond = Fishpond.easypond();

            case 2:
                System.out.println("medium");
                pond = Fishpond.mediumpond();
            case 3:
                System.out.println("hard");
                pond = Fishpond.hardpond();
        }
    }

    //    public Pair<Integer, ItemStack> spawn_fish(){
//            //TODO: Cole spawn many fishes, also EnumFishType fish: EnumFishType.values() on OldRod.java
//
//        //AI using Heart Rate
//
//
//    }
    public Pair<Integer, ItemStack> change_fish() {
        //Heart Rate reached
        if (fish_run_away == 2 || last_fish==null) {

            random_fish();
        }
        if (avg <= p.getTargetMax() && avg >= p.getTargetMin()) {
            //smaller fish
            if (fish_run_away == 1) {
                int l=pond.indexOf(last_fish);
                int i = random.nextInt(l);
                Pair<Integer, ItemStack> r = pond.get(i);
                last_fish=r.getValue();
                return r;
            }
            else{
                random_fish();
            }
        }
        //High,smaller fish
        else if (avg >= p.getTargetMax()) {
            int l=pond.indexOf(last_fish);
            int i = random.nextInt(l);
            Pair<Integer, ItemStack> r = pond.get(i);
            last_fish=r.getValue();
            return r;
        }
        //Low,bigger fish
        else {
            int l=pond.indexOf(last_fish);
            int i = random.nextInt(pond.size()-l);
            Pair<Integer, ItemStack> r = pond.get(l+i);
            last_fish=r.getValue();
            return r;
        }
        return null;
    }
    private Pair<Integer, ItemStack> random_fish() {

        int i = random.nextInt(pond.size());
        Pair<Integer, ItemStack> r = pond.get(i);
        last_fish=r.getValue();
        return r;
    }
}

