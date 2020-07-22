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
import net.minecraft.item.ItemFishingRod;
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

    public static HudString powerString;
    public static HudString timerString;
    public static HudRectangle powerBar;
    public static HudRectangle powerLine;
    public static HudString distanceString;
//    protected final int reelRange;
//    protected final int tuggingAmount;
//    protected final int dragSpeed;
    public ItemGameFishingRod() {
//        super();
//        this.setRegistryName("game_fish_rod");
//
//        //this.func_77655_b("fishingmadebetter." + name);
//        this.reelRange = 20;
//        this.tuggingAmount = 1;
//        this.dragSpeed = 60;
//        super();
        super("game_fish_rod", 20, 1, 60);
        this.setUnlocalizedName("game_fish_rod");
        System.out.println("This tiggered new register event?");
//        this.setRegistryName("game_fish_rod");
        System.out.println("This tiggered new register event?");
        //this.setRegistryName("game_fish_rod");
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        System.out.println("Right click aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        if (playerIn.fishEntity != null)
        {

            if (!worldIn.isRemote)
            {
                System.out.println("Retract 222222222222222222222222222222222");
                ServerUtils.sendQuestData(EnumPacketServer.FishRetract,(EntityPlayerMP)playerIn, 2);
            }
            int i = playerIn.fishEntity.handleHookRetraction();

            itemstack.damageItem(i, playerIn);
            playerIn.swingArm(handIn);
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        }
        else
        {
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!worldIn.isRemote)
            {
                System.out.println("Throw 1111111111111111111111111111");
                ServerUtils.sendQuestData(EnumPacketServer.FishRetract,(EntityPlayerMP)playerIn, 1);
//                System.out.println("New Hook bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
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


            }


            playerIn.swingArm(handIn);
            playerIn.addStat(StatList.getObjectUseStats(this));
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

}
