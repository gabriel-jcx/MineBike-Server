package edu.ics.uci.minebike.minecraft.item;


import com.teammetallurgy.aquaculture.items.ItemFish;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static com.teammetallurgy.aquaculture.items.AquacultureItems.fish;

//import net.minecraft.client.renderer.entity.RenderItem;
//import net.minecraft.util.AxisAlignedBB;
//import net.minecraft.util.MathHelper;
//import net.minecraft.util.MovingObjectPosition;
//import net.minecraft.util.Vec3;
//import net.minecraft.util.WeightedRandomFishable;

//import org.ngs.bigx.minecraft.BiGX;
//import org.ngs.bigx.minecraft.bike.BiGXPacketHandler;
//import org.ngs.bigx.minecraft.client.GuiMessageWindow;
//import org.ngs.bigx.minecraft.client.gui.hud.HudManager;
//import org.ngs.bigx.minecraft.client.gui.hud.HudRectangle;
//import org.ngs.bigx.minecraft.client.gui.hud.HudString;
//import org.ngs.bigx.minecraft.context.BigxClientContext;
//import org.ngs.bigx.minecraft.items.EnumFishType;

public class CustomHook extends EntityFishHook
{

    private int xTile;
    private int yTile;
    private int zTile;
    private Block inTile;
    private boolean inGround;
    public int shake;
    //    public OlReliable rod;
    public EntityPlayer angler;

    @SideOnly(Side.CLIENT)
    private double clientMotionX;
    @SideOnly(Side.CLIENT)
    private double clientMotionY;
    @SideOnly(Side.CLIENT)
    private double clientMotionZ;
    private static final String __OBFID = "CL_00001663";
    private boolean justSpawned;
    private int fish_n=0;


    //Pulling Mechanic Variables

    //When beginPull is true the custom fishing mechanic will start in the onUpdate method
    private boolean beginPull = false;

    /*When fishing mechanic starts the tick count will count every tick that happens in a second
      This is used to keep track of each second*/
    private int tickCount = 0;
    //public int i = 0;
    //Activated when the required power level is achieved and counts how long they maintain the required power
    private int tickSuccess = 0;

    //Activated when the required power level is under half of the required power for too long
    private int tickFail = 0;

    //The number of times they click, used to increase the power level
    //HERE FOR DEBUGGING
    private double clickRate = 0;

    //Checks to see if the Power Level GUI has reached maximum height
    private int checkHeight = 0;

    //Holds the time in seconds needed to hold the power level
    private static int time = 5;

    //Holds the time in seconds needed to fail
    private static int timeF = 5;

    //Checks to see if the holdTimer GUI is initialized
    private boolean checkTime = false;

    //Checks to see if the failTime GUI is initialized
    private boolean checkFail = false;

    public static int fishingLocation = 0;

    //Checks the rarity of the fish in order to adjust the difficulty
    public static int difficulty; // 4 7 6 3

    //Sets the required power level they need to reach in order to catch the fish, based on difficulty
    public int requiredPower=1;

    //The Number of fish the player had caught
    public static int numFish = 0;

    //Determines how much clickRate is decreased by, Used to make catching faster if power above certain limit
    public static int addPower;

    //Remaining distance to catch the fish
    public static int distance = 4;
    public static String current_fish;
    public static int spawn_fish;

    public int timer = 10;

    EntityItem entityitem;


    //Store all the fishes with their weights
    private List<ArrayList<ItemFish>> fishingSpots = new ArrayList<ArrayList<ItemFish>>();


    //The Gui for the Power Level Display

    public static HudString powerString;
    public static HudString timerString;
    public static HudRectangle powerBar;
    public static HudRectangle powerLine;
    public static HudString distanceString;

    //The Custom constructor, instantiates custom fish items
    public CustomHook (World worldIn, EntityPlayer fishingPlayer)
    {
        super(worldIn, fishingPlayer);
        justSpawned = true;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.ignoreFrustumCheck = true;
        this.angler = fishingPlayer;
        System.out.println("Hook constructor called");;
        //if(worldIn.isRemote) System.out.println(this.angler.getCommandSenderName() + "<client");

        this.setSize(0.25F, 0.25F);
        this.setLocationAndAngles(fishingPlayer.posX, fishingPlayer.posY + (double)fishingPlayer.getEyeHeight(), fishingPlayer.posZ, fishingPlayer.rotationYaw, fishingPlayer.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        float f = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.handleHookCasting(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);

        //The total range of the bar is x:(-70,65] because the line's w is 5




        //Todo: If is possible, implement the following method for spawning the fishingSpots.
        //adds all the common fish to every fish location
//        for(int i = 0; i < 7; i++)
//        {
//            fishingSpots.add(new ArrayList<ItemFish>());
//            for (EnumFishType fish: EnumFishType.values())
//            {
//                ItemStack shrooma = AquacultureItems.fish.getItemStackFish("Red Shrooma");
//                Item temp = MineBikeCustomItems.itemMap.get("item.ItemFish." + fish.getName());
//                if(fish.getType() == 0)
//                    fishingSpots.get(i).add(new ItemFish(new ItemStack(temp, 1), fish.getWeight()));
//            }
//        }


        //Adds all spot specific fish to the correct fishing location
//        for (EnumFishType fish: EnumFishType.values())
//        {
//            Item temp = MineBikeCustomItems.itemMap.get("item.ItemFish." + fish.getName());
//            if(fish.getType() != 0)
//            {
//                fishingSpots.get(fish.getType() - 1).add(new WeightedRandomFishable(new ItemStack(temp, 1), fish.getWeight()));
//            }
//        }
        //

        this.isImmuneToFire = true;
    }

    //Handles what happens when the hook is cast
    // NOTE: you might be able to replace this with super.shoot()
    public void handleHookCasting(double p_146035_1_, double p_146035_3_, double p_146035_5_, float p_146035_7_, float p_146035_8_)
    {
        float f2 = MathHelper.sqrt(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_);
        p_146035_1_ /= (double)f2;
        p_146035_3_ /= (double)f2;
        p_146035_5_ /= (double)f2;
        p_146035_1_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_146035_8_;
        p_146035_3_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_146035_8_;
        p_146035_5_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_146035_8_;
        p_146035_1_ *= (double)p_146035_7_;
        p_146035_3_ *= (double)p_146035_7_;
        p_146035_5_ *= (double)p_146035_7_;
        this.motionX = p_146035_1_;
        this.motionY = p_146035_3_;
        this.motionZ = p_146035_5_;
        float f3 = MathHelper.sqrt(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_146035_1_, p_146035_5_) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_146035_3_, (double)f3) * 180.0D / Math.PI);

    }

    /**
     * Called to update the entity's position/logic.
     */
    /*Contains the new Fishing Mechanic
     * The start of the onUpdate method outlines the custom fishing mechanic
     */


    @Override
    public void onUpdate()
    {
        //ServerUtils.sendQuestData(EnumPacketServer.FishingDistance, (EntityPlayerMP)this.angler, distance);
        this.onEntityUpdate();
        this.extinguish();
        //super.onUpdate();

        //Todo: erase hud if fish rod is not in hand, retract hook, spawn fish
        if (distance==0)
        {

            this.setDead();
            entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ,fish.getItemStackFish(current_fish));
            spawn_fish();
            fish_n+=1;
            distance=4;

        }

    }


    /*Checks to see what type of event is happening when player retracts hook
     * b0 = 0 inAir
     * b0 = 1 caughtFish
     * b0 = 2 inGround
     * b0 = 3 hookedOnEntity
     */


    //Used to blend the color of the power bar depending on its height
    public int color(int height)
    {
        int temp = Math.abs(height);
        int redHeight = temp - 117;
        int greenValue = 0x00400000;
        int redValue = 0xf0000000;
        int alpha = 0x000040ff;
        int addGreen = 0;
        int subRed = 0;
        if(temp <= 117 && temp >= 0)
        {
            addGreen += (int)(temp * 1.5);
            greenValue = greenValue + (0x00010000 * addGreen);
        }
        else if(temp > 117 && temp <= 210)
        {
            greenValue = 0x00f00000;
            subRed = (int)(redHeight * 1.5);
            redValue = redValue - (0x01000000 * subRed);
        }
        return redValue + greenValue + alpha;
    }

    public void pull(){
        this.beginPull=!this.beginPull;
    }
    public boolean get_pull(){
        return this.beginPull;
    }

    private void spawn_fish(){
        this.angler.world.spawnEntity(entityitem);
        //player.field_70170_p.func_72838_d(entityitem);
        this.angler.world.spawnEntity(new EntityXPOrb(this.angler.world,this.angler.posX,  this.angler.posY + 0.5D, this.angler.posZ+ 0.5D, 10));
    }

    //Gets the speed the player must achieve in order to catch the specified tier of fish
    /*
     * TIERS OF FISH
     * 4 = COMMON
     * 7 = UNCOMMON
     * 6 = RARE
     * 3 = LEGENDARY
     */
//    private double getRequiredPower()
//    {
//        //4 7 6 3
//        switch(difficulty)
//        {
//            case 4:
//                return 50; //15
//            case 7:
//                return 60; //21
//            case 6:
//                return 70; //30
//            case 3:
//                return 80; //35
//            default:
//                return 60; //21
//        }
//    }

    private int getPower()
    {
        //Todo:for bigx
        //return (BiGXPacketHandler.change * 4);
        return 1;
    }

    //Returns the how much the height of the power bar is incrementing based on the tier of fish
//    private int getHeight()
//    {
//        return (int) (-210 * ((double)(getPower()) / (double) getRequiredPower()));
//    }

    //Returns how much extra speed the player must achieve before catching goes twice as fast
    private int getBonus()
    {
        //4 7 6 3
        switch(difficulty)
        {
            case 4:
                return 60;
            case 7:
                return 70;
            case 6:
                return 80;
            case 3:
                return 90;
            default:
                return 70;
        }
    }


    //Gets the types of items and custom fish the player can catch
//    private ItemStack getFishingResult()
//    {
//        ItemStack itemstack;
//        //this.angler.triggerAchievement(StatList.fishCaughtStat);
//
//        //Todo: testing return red shrooma
//        return fishingAI.fish_testing().getValue();
//    }
}
