package edu.ics.uci.minebike.minecraft.quests.rewardSystem;

import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static net.minecraftforge.items.ItemHandlerHelper.giveItemToPlayer;

import java.util.ArrayList;
import java.util.Arrays;

public class RewardFunctions {

    private int diamonds = 5;
    //private String[] diffArray = new String[]{"", "easy", "medium", "hard"};

    public RewardFunctions(){

    }

    public void giveDiamonds(ArrayList<EntityPlayerMP> playerList){
        for(EntityPlayer player: playerList){
            giveItemToPlayer(player, new ItemStack( Items.DIAMOND, diamonds));


            //once difficulty gets implemented:
            //giveItemToPlayer(player, new ItemStack(  Items.DIAMOND, diamonds * Arrays.asList(diffArray).indexOf(difficulty))  );


//                ServerUtils.telport((EntityPlayerMP)player, Jaya.LOCATION,0);
        }
    }


}
