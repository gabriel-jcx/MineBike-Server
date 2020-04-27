package edu.ics.uci.minebike.minecraft.quests.customQuests;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFishing;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import edu.ics.uci.minebike.minecraft.item.ItemGameFishingRod;

import java.util.concurrent.TimeUnit;

public class FishingQuest  extends AbstractCustomQuest {
    EntityPlayer player = null;
    private int current_t=0;
    public static int distance=4;
    public static int timer=10;
    public static int retract=0;
    public static int bar_min= -70;
    public static int bar_max=65;
    public int requiredPower=1;
    public static ItemGameFishingRod rod;
    public static HudString powerString;
    public static HudString timerString;
    public static HudRectangle powerBar;
    public static HudRectangle powerLine;
    public static HudString distanceString;
    //private Vec3d ball_location = new Vec3d(10,10,10);

    public FishingQuest(){
        super();
        this.DIMID = WorldProviderFishing.DIM_ID;
        this.questStartLocation = new Vec3d(10,10,10);

    }
    @Override
    protected void setupQuestEnv(World world, EntityPlayer player) {

    }

    @Override
    public boolean onPlayerJoin(EntityPlayer player) {
        give_rod(player);
        return true;
    }

    public void give_rod(EntityPlayer playerSP){
        //ResourceLocation resourcelocation = new ResourceLocation("minebikemod:game_rod"); //
        //Fishing rod name
        ResourceLocation resourcelocation = new ResourceLocation("minebikemod:game_fish_rod");
        this.rod = (ItemGameFishingRod)Item.REGISTRY.getObject(resourcelocation);
        ItemStack itemstack = new ItemStack(rod,1);
        playerSP.inventory.addItemStackToInventory(itemstack);
        playerSP.world.playSound((EntityPlayer)null, playerSP.posX, playerSP.posY, playerSP.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((playerSP.getRNG().nextFloat() - playerSP.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
        playerSP.inventoryContainer.detectAndSendChanges();

    }
    @Override
    public void start(EntityPlayerMP playerMP) {
        //this.player = player;

        System.out.println(" Start Fishing quest ");
//        TextComponentString give = new TextComponentString(String.format("/give %s fishingmadebetter:diamond_fishing_rod ", this.player.getName()));
//        this.player.sendMessage(give);
        System.out.println("Trying to teleport " + playerMP.getName() + " to DIM" + this.DIMID);
        ServerUtils.telport((EntityPlayerMP) playerMP, questStartLocation, DIMID);

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
    public void onWorldTick(TickEvent.WorldTickEvent event) {

    }

    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (retract==2){
            retract=0;
            System.out.println("retract");
            unreg_hud();
            distance=4;
            timer=10;

        }
        else if(retract==1)
        {
            if (powerLine!=null)
            {
                unreg_hud();
//                System.out.println("unreg_hud");
            }

            System.out.println("throw");
            this.powerString = new HudString(-125, 20, "POWER LEVEL", true, false);
            this.distanceString = new HudString(-10, 35, "Distance "+ distance, true, false);
            this.timerString = new HudString(-10, 45, "The fish will run away in:  "+ timer+" seconds", true, false);
            this.powerBar= new HudRectangle(-70,0, 140, 30, 0xe4344aff,true,false);
            this.powerLine = new HudRectangle(-70,0, 5, 30, 0xffffffff,true,false);
            retract=3;
        }
        else if (retract==3){
            refresh_powerline();
            reduce_distance();
            refresh_timerString();
            if (distance == 0) {
                retract=0;

                unreg_hud();
                distance=4;
                timer=10;
            }
        }

    }
    private int getPower()
    {
        //Todo:for bigx
        //return (BiGXPacketHandler.change * 4);
        return 1;
    }
    public void refresh_timerString(){
        System.out.println("refreshing...................................");
        if (current_t != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
            current_t = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            timer -= 1;

            // You don't need to new here, you can just simply modify this.timerString.text
//            this.timerString.unregister();
            this.timerString.text= "The fish will run away in:  " + timer + " seconds";

        }
    }
    public void refresh_powerline(){
        System.out.println("powerline....................");
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
            if(distance==0)
            {
                ClientUtils.sendData(EnumPacketClient.FishingDistance,distance);
            }

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
