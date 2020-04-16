package edu.ics.uci.minebike.minecraft.item;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.theawesomegem.fishingmadebetter.common.item.fishingrod.ItemBetterFishingRod;
import edu.ics.uci.minebike.minecraft.item.CustomHook;

import java.util.concurrent.TimeUnit;
//import net.theawesomegem.fishingmadebetter.common.item.fishingrod.ItemBetterFishingRod;

public class ItemGameFishingRod extends ItemBetterFishingRod {
    //Create a custom fishing rod based on Player's prescription
    CustomHook gameHook;
    private int current_t=0;
    public static int distance=4;
    public static int timer=0;
    public static HudString powerString;
    public static HudString timerString;
    public static HudRectangle powerBar;
    public static HudRectangle powerLine;
    public static HudString distanceString;
    public static int bar_min= -70;
    public static int bar_max=65;
    public int requiredPower=1;
    public ItemGameFishingRod() {

//        super();
        super("game_fish_rod", 20, 1, 60);
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        System.out.println("Right click aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        if (playerIn.fishEntity != null)
        {
            int i = playerIn.fishEntity.handleHookRetraction();
            unreg_hud();
            itemstack.damageItem(i, playerIn);
            playerIn.swingArm(handIn);
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        }
        else
        {
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!worldIn.isRemote)
            {
                System.out.println("New Hook bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                gameHook = new CustomHook(worldIn, playerIn);



                int j = EnchantmentHelper.getFishingSpeedBonus(itemstack);

                if (j > 0)
                {
                    gameHook.setLureSpeed(j);
                }

                int k = EnchantmentHelper.getFishingLuckBonus(itemstack);

                if (k > 0)
                {
                    gameHook.setLuck(k);
                }

                worldIn.spawnEntity(gameHook);

            }
            else{

                this.powerString = new HudString(-125, 20, "POWER LEVEL", true, false);
                this.distanceString = new HudString(-10, 35, "Distance "+ distance, true, false);
                this.timerString = new HudString(-10, 45, "The fish will run away in:  "+ timer+" seconds", true, false);
                this.powerBar= new HudRectangle(-70,0, 140, 30, 0xe4344aff,true,false);
                this.powerLine = new HudRectangle(-70,0, 5, 30, 0xffffffff,true,false);

            }


            playerIn.swingArm(handIn);
            playerIn.addStat(StatList.getObjectUseStats(this));
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
    private int getPower()
    {
        //Todo:for bigx
        //return (BiGXPacketHandler.change * 4);
        return 1;
    }
    public void refresh_timerString(){
        if (current_t != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
            current_t = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            timer -= 1;

            // You don't need to new here, you can just simply modify this.timerString.text
//            this.timerString.unregister();
            this.timerString.text= "The fish will run away in:  " + timer + " seconds";

        }
    }
    public void refresh_powerline(){
        int tempx=powerLine.getX();
        if (this.requiredPower==getPower()&&tempx+5<=bar_max) {
//            this.powerLine.unregister();
            this.powerLine.x=tempx + 5;
//            this.powerLine = new HudRectangle(tempx + 5, 0, 5, 30, 0xffffffff, true, false);

        }else if (this.requiredPower!=getPower()&&tempx-5>=bar_min)
        {
//            this.powerLine.unregister();
            this.powerLine.x=tempx - 5;
//            this.powerLine = new HudRectangle(tempx - 5, 0, 5, 30, 0xffffffff, true, false);
        }
    }
    public void reduce_distance(){
        if (this.powerLine.getX()==bar_max && distance-1>=0)
        {
            distance-=1;
            this.distanceString.text= "Distance "+ distance;
            ClientUtils.sendData(EnumPacketClient.FishingDistance,distance);
//            this.distanceString = new HudString(-10, 35, "Distance "+ distance, true, false);
        }
    }
    public void unreg_hud(){
        this.powerLine.unregister();
        this.distanceString.unregister();
        this.powerBar.unregister();
        this.powerString.unregister();
        this.timerString.unregister();
    }
}
