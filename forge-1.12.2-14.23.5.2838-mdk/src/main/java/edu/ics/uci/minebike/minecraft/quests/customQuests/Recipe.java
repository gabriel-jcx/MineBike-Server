package edu.ics.uci.minebike.minecraft.quests.customQuests;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class Recipe
{
    private Item orderType; //type of bread
    private Item[] insideBread; //array of ingredients
    private String name;

    public Recipe(Item type, Item[] items, String foodName)
    {
        orderType = type;
        insideBread = items;
        name = foodName;
    }

    //return type of bread
    public Item getType()
    {
        return orderType;
    }

    //return ingredients array
    public Item[] getInsides()
    {
        return insideBread;
    }

    public String getName(){return  name;}

    //returns true if recipe can be made with player's inventory
    @SideOnly(Side.SERVER)
    public boolean canMake(TickEvent.PlayerTickEvent event, EntityPlayerMP player) //~~works i think
    {
        int ingedrientCnt = insideBread.length;
        boolean type = false;
        InventoryPlayer inventory = player.inventory;

        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack stack = inventory.getStackInSlot(i);
            if(stack != null)
            {
                for(int j = 0; j < insideBread.length; j++)
                {
                    if(stack.getItem().getUnlocalizedName().equals(orderType.getUnlocalizedName()))
                        type = true;
                    else if(stack.getItem().getUnlocalizedName().equals(insideBread[j].getUnlocalizedName()))
                        ingedrientCnt--;

//					System.out.println(stack.getItem().getUnlocalizedName() + " realbread " + orderType.getUnlocalizedName());
//					System.out.println(stack.getItem().getUnlocalizedName() + " ingredient " + insideBread[j].getUnlocalizedName());
                }
            }
        }
        if(ingedrientCnt==0 && type)
            return true;
        System.out.println(ingedrientCnt);
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean canMake(TickEvent.PlayerTickEvent event) //~~works i think
    {
        int ingedrientCnt = insideBread.length;
        boolean type = false;
        InventoryPlayer inventory = event.player.inventory;

        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack stack = inventory.getStackInSlot(i);
            if(stack != null)
            {
                for(int j = 0; j < insideBread.length; j++)
                {
                    if(stack.getItem().getUnlocalizedName().equals(orderType.getUnlocalizedName()))
                        type = true;
                    else if(stack.getItem().getUnlocalizedName().equals(insideBread[j].getUnlocalizedName()))
                        ingedrientCnt--;

//					System.out.println(stack.getItem().getUnlocalizedName() + " realbread " + orderType.getUnlocalizedName());
//					System.out.println(stack.getItem().getUnlocalizedName() + " ingredient " + insideBread[j].getUnlocalizedName());
                }
            }
        }
        if(ingedrientCnt==0 && type)
            return true;
        System.out.println(ingedrientCnt);
        return false;
    }
}
