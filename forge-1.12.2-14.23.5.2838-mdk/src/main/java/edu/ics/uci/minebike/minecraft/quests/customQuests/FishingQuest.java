package edu.ics.uci.minebike.minecraft.quests.customQuests;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI.FishingAI;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFishing;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import edu.ics.uci.minebike.minecraft.item.ItemGameFishingRod;
import edu.ics.uci.minebike.minecraft.client.QuestHud.FishingQuestHud;
//import org.ngs.bigx.minecraft.BiGX;

import java.util.Random;

import static java.lang.Math.abs;

public class FishingQuest  extends AbstractCustomQuest {
    EntityPlayer player = null;
//    private int fishCountDown=0;
//    private int fishCountDown=0;
    public static int distance=4;
    public static int timer=10;
    public static Movement retract=Movement.INIT;
//    public static int BAR_MIN= -70;
//    public static int BAR_MAX=65;
//    public int requiredPower=1;
//    public int gameTimeDisplay=240;
    public static ItemGameFishingRod rod =null;
//    public static HudString powerString;
//    public static HudString timerString;
//    public static HudRectangle powerBar;
//    public static HudRectangle powerLine;
//    public static HudString distanceString;
//    public static HudString gameTime;
    public static Integer fish_id;
    FishingAI fishingAI =new FishingAI();
    FishingQuestHud fishingQuestHud= new FishingQuestHud();
    Random random= new Random();
    private Integer currentFishResistance;
    //private Vec3d ball_location = new Vec3d(10,10,10);

    public FishingQuest(){
        super();
        this.NAME = "fishing";
        this.DIMID = WorldProviderFishing.DIM_ID;
        this.questStartLocation = new Vec3d(-88,4,62);

    }
    @Override
    protected void setupQuestEnv(World world, EntityPlayer player) {

    }

    @Override
    public boolean onPlayerJoin(EntityPlayer player) {
        System.out.println("Fishing onPlayerJoin");
        give_rod(player);
//        this.start((EntityPlayerMP)player);
        return true;
    }

    public void give_rod(EntityPlayer playerSP){
        //ResourceLocation resourcelocation = new ResourceLocation("minebikemod:game_rod"); //
        //Fishing rod name
        if(playerSP.world.isRemote){
            ClientUtils.teleport(((EntityPlayerSP)playerSP),questStartLocation,223);
        }

        ResourceLocation resourcelocation = new ResourceLocation("minebikemod:game_fish_rod");
        this.rod = (ItemGameFishingRod)Item.REGISTRY.getObject(resourcelocation);
        ItemStack itemstack = new ItemStack(rod,1);
        System.out.println("Fishingrod"+itemstack);
        playerSP.inventory.addItemStackToInventory(itemstack);
        playerSP.addItemStackToInventory(itemstack);
        playerSP.world.playSound((EntityPlayer)null, playerSP.posX, playerSP.posY, playerSP.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((playerSP.getRNG().nextFloat() - playerSP.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
        playerSP.inventoryContainer.detectAndSendChanges();


    }
    @Override
    public void start(EntityPlayerMP playerMP) {
        //this.player = player;

//        System.out.println(" Start Fishing quest ");
//        TextComponentString give = new TextComponentString(String.format("/give %s fishingmadebetter:diamond_fishing_rod ", this.player.getName()));
//        this.player.sendMessage(give);
//        System.out.println("Trying to teleport " + playerMP.getName() + " to DIM" + this.DIMID);
//        ServerUtils.telport((EntityPlayerMP) playerMP, questStartLocation, DIMID);

//        this.gameTime= new HudString(-165, 20, "Time: "+gameTimeDisplay, true, false);
        fishingQuestHud.initializeGameTime();
        fishingAI.selectPond(1);

    }
    @Override
    public void start(){
    }

    @Override
    public void start(EntityPlayerSP player) {

    }

    @Override
    public void start(EntityJoinWorldEvent event) {

    }

    @Override
    public void end() {
        System.out.println(" Start Fishing quest ");
        TextComponentString rod = new TextComponentString(String.format("/give %s fishingmadebetter:diamond_fishing_rod ", this.player.getName()));
        TextComponentString fish = new TextComponentString(String.format("/give %s Fish 2 ", this.player.getName()));
        this.player.sendMessage(rod);
        this.player.sendMessage(fish);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void onWorldTick(TickEvent.WorldTickEvent event) {

    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side.isClient()) {
//            System.out.println("event.side.isClient()");
            fishingQuestHud.refreshCountDown();
            if (retract == Movement.RETRACT) {
//                BiGX.instance().clientContext.lock(false);
                retract = Movement.INIT;
                System.out.println("retract");
                fishingQuestHud.unregisterHud();
                distance = 4;
                timer = 10;
                fishingAI.fishStatus = FishingAI.FishStatus.QUIT;

            } else if (retract == Movement.THROW) {
//                BiGX.instance().clientContext.lock(true);
                System.out.println("FishingQuest ");
                if (fishingQuestHud.powerLine != null) {
                    fishingQuestHud.unregisterHud();
//                System.out.println("unregisterHud");
                }

                System.out.println("throw");

                currentFishResistance = fishingAI.changeFish();

                timer = currentFishResistance + 10;
                int i = random.nextInt(6);
                distance = abs(currentFishResistance * 2 - i);
                currentFishResistance = fishingAI.changeFish();
//            this.gameTime= new HudString(-165, 20, "Time: "+gameTimeDisplay, true, false);
//            this.powerString = new HudString(-125, 20, "POWER LEVEL", true, false);
//            this.distanceString = new HudString(-10, 35, "Distance "+ distance, true, false);
//            this.timerString = new HudString(-10, 45, "The fish will run away in:  "+ timer+" seconds", true, false);
//            this.powerBar= new HudRectangle(-70,0, 140, 30, 0xe4344aff,true,false);
//            this.powerLine = new HudRectangle(-70,0, 5, 30, 0xffffffff,true,false);
                fishingQuestHud.initializeFishingHud();


                retract = Movement.FISHING;
            } else if (retract == Movement.FISHING) {
                //

                fishingQuestHud.refreshPowerBar();
                fishingQuestHud.reduceFishDistance();
                fishingQuestHud.refreshTimerString();
                if (distance == 0) {
                    retract = Movement.INIT;

                    fishingQuestHud.unregisterHud();
                    fishingAI.fishStatus = FishingAI.FishStatus.CAUGHT;
                }
                if (timer == 0 && distance != 0) {
                    retract = Movement.INIT;
                    fishingQuestHud.unregisterHud();
                    fishingAI.fishStatus = FishingAI.FishStatus.CAUGHT;
                }
            }
        }
    }
    private int getPower()
    {
        //Todo:for bigx
        //return (BiGXPacketHandler.change * 4);
        return 1;
    }
    // TODO: This should be update hr, rename!
    public void fishing_heart_rate(int hr){
            // You don't need to new here, you can just simply modify this.timerString.text
//            this.timerString.unregister();
//            this.timerString.setText("The fish will run away in:  " + timer + " seconds");

    }
//    public void refreshCountDown() {
//        if (gameCountDown != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
//            gameCountDown = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
//            gameTimeDisplay -= 1;
//            this.gameTime.text= "Time: "+gameTimeDisplay;
//        }
//    }
//    public void refreshTimerString(){
//        System.out.println("refreshing...................................");
//        if (fishCountDown != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
//            fishCountDown = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
//            timer -= 1;
//
//            // You don't need to new here, you can just simply modify this.timerString.text
////            this.timerString.unregister();
//            this.timerString.text= "The fish will run away in:  " + timer + " seconds";
//
//        }
//    }
//    public void refreshPowerline(){
//        System.out.println("powerline....................");
//        int tempx=powerLine.getX();
//        if (this.requiredPower==getPower()&&tempx+5<=BAR_MAX) {
////            this.powerLine.unregister();
//            this.powerLine.x=tempx + 5;
////            this.powerLine = new HudRectangle(tempx + 5, 0, 5, 30, 0xffffffff, true, false);
//
//        }else if (this.requiredPower!=getPower()&&tempx-5>=BAR_MIN)
//        {
////            this.powerLine.unregister();
//            this.powerLine.x=tempx - 5;
////            this.powerLine = new HudRectangle(tempx - 5, 0, 5, 30, 0xffffffff, true, false);
//        }
//    }
//    public void reduceFishDistance(){
//        if (this.powerLine.getX()==BAR_MAX && distance-1>=0)
//        {
//            distance-=1;
//            this.distanceString.text= "Distance "+ distance;
//            if(distance==0)
//            {
//                ClientUtils.sendData(EnumPacketClient.FishingDistance,distance);
//
//            }
//
////            this.distanceString = new HudString(-10, 35, "Distance "+ distance, true, false);
//        }
//    }
//    public void unregisterHud(){
//        this.powerLine.unregister();
//        this.distanceString.unregister();
//        this.powerBar.unregister();
//        this.powerString.unregister();
//        this.timerString.unregister();
//    }
    public enum Movement {
        INIT,
        THROW,
        RETRACT,
        FISHING
    }

//    public void reduce_distance(){
//        if (this.powerLine.getX()==bar_max && distance-1>=0)
//        {
//            distance-=1;
//            this.distanceString.setText("Distance "+ distance);
//            if(distance==0)
//            {
//                ClientUtils.sendData(EnumPacketClient.FishingDistance,distance);
//            }
//        }
//    }

}
