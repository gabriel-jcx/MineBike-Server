package edu.ics.uci.minebike.minecraft.client.item;

import net.theawesomegem.fishingmadebetter.common.item.fishingrod.ItemBetterFishingRod;

public class ItemGameFishingRod extends ItemBetterFishingRod {
    //Create a custom fishing rod based on Player's prescription
    public ItemGameFishingRod(String name, int reelRange, int tuggingAmount, int dragSpeed) {
        super("game_fish_rod", 20, 1, 60);

    }


}
