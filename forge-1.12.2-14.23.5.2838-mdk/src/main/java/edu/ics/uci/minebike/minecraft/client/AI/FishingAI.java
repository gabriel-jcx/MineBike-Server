package edu.ics.uci.minebike.minecraft.client.AI;
import com.teammetallurgy.aquaculture.items.AquacultureItems;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.List;

import static java.lang.Float.sum;

public class FishingAI {
    public List<Float> heart_rate;
    //public Float avg;
    public FishingAI(){

    }
    public FishingAI(float h){

    }
    public void add_heart_rate(float h){
        heart_rate.add(h);
    }

    public ItemStack fish_testing(){
        ItemStack shrooma = AquacultureItems.fish.getItemStackFish("Red Shrooma");
        System.out.println("fish_testing creat fish.......................");
        return shrooma;

    }
    public ItemStack spawn_fish(){
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
