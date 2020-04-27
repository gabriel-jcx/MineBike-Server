package edu.ics.uci.minebike.minecraft.client.AI;
import com.teammetallurgy.aquaculture.items.AquacultureItems;
import com.teammetallurgy.aquaculture.items.ItemFish;
import net.minecraft.item.ItemStack;

import java.util.*;

import static java.lang.Float.sum;

public class FishingAI {
    public Map<Integer, ItemStack> fishes= new HashMap<Integer, ItemStack>();
    ItemStack shrooma = AquacultureItems.fish.getItemStackFish("Red Shrooma");
    ItemStack goldfish = AquacultureItems.fish.getItemStackFish("Goldfish");
    ItemStack whale = AquacultureItems.fish.getItemStackFish("Whale");


    public List<Float> heart_rate;
    //public Float avg;
    public FishingAI(){
        fishes.put(0,shrooma);
        fishes.put(1,goldfish);
        fishes.put(2,whale);
    }
    public FishingAI(float h){

    }
    public void add_heart_rate(float h){
        heart_rate.add(h);
    }

    public ItemStack fish_testing(){
        Random random= new Random();
        int i= random.nextInt(3);
        ItemStack r= fishes.get(i);

        System.out.println("fish_testing creat fish......................."+ r.getDisplayName());


        return r;

    }
    public ArrayList<ItemFish> spawn_fish(){
        //TODO: Cole spawn many fishes, also EnumFishType fish: EnumFishType.values() on OldRod.java

        //AI using Heart Rate

        return null;
    }
    private float calc_avg(){
        float s=0;
        for (int i = 0; i < heart_rate.size(); i++) {
            s+= heart_rate.get(i);
        }
        return s/heart_rate.size();

    }

}
