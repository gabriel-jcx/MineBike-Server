package edu.ics.uci.minebike.minecraft.client.hud;
import edu.ics.uci.minebike.minecraft.BiGXMain;
import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import  edu.ics.uci.minebike.minecraft.quests.customQuests.FishingQuest;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.ngs.bigx.minecraft.BiGX;
import org.ngs.bigx.minecraft.context.BigxClientContext;
import org.ngs.bigx.minecraft.client.ClientEventHandler;


import java.util.concurrent.TimeUnit;

public class OuterAIHud {
    public HudString heartString;
    public HudString resistance;
    public HudString goalString;
    public HudString goalReached;
    public HudString popUpQuest;
    public HudString popUpInstruction;
    private int minGoal=0;
    private int maxGoal=150;
    private int hr=0;

    public HudString minString;
    private int progressCounter=90;
    private int secCounter=0;

    public HudString incline;

    private int level=0;
    private int currentTime=0;
    private float currentResistance=0;


    ResourceLocation heartLocation= new ResourceLocation(BiGXMain.MOD_ID +":textures/heart.png");
    ResourceLocation popUpBackGround= new ResourceLocation(BiGXMain.MOD_ID +":textures/bg.png");




    public OuterAIHud(){

    }
    @SideOnly(Side.CLIENT)
    public void showHeartIcon(){

        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(heartLocation);

        mc.ingameGUI.drawTexturedModalRect(0,0,0,0,40,40);
        mc.ingameGUI.drawTexturedModalRect(5,40,40,5,25,80);
        mc.ingameGUI.drawTexturedModalRect(5,40+progressCounter,70,5+progressCounter,25,80);

    }
    @SideOnly(Side.CLIENT)
    public void showPopUpBG(){

        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(popUpBackGround);
        mc.ingameGUI.drawTexturedModalRect(5,40+progressCounter,70,5+progressCounter,25,80);

    }
    public void showHud_progress(){

    }
    @SideOnly(Side.CLIENT)
    public void showHud(){
        this.heartString= new HudString(14,16, ""+hr,false,false);
        this.goalString= new HudString(50, 16, "Heart Rate Goal: "+minGoal+"-"+maxGoal, false, false);
        this.goalReached= new HudString(10, 220, "Goal Reached!",false,false);
        this.resistance= new HudString(10, 200, "Current Resistance: "+currentResistance,false,false);
//        this.minString = new HudString(400, 10, "Time Played: "+progressCounter+"m", false, false);
//        this.incline = new HudString(10, 400, "Current working level: "+ level, false, false);
    }
    @SideOnly(Side.CLIENT)
    public void refresh(){

        if(progressCounter<=0){
            this.goalReached= new HudString(10, 220, "Goal Reached!",false,false);
        }
        if (currentTime != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
            currentTime = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            hr+=1;
            System.out.println(((BigxClientContext) BiGX.instance().clientContext).resistance);

            if(((BigxClientContext) BiGX.instance().clientContext).resistance!= currentResistance)
            {
                currentResistance=((BigxClientContext) BiGX.instance().clientContext).resistance;
                this.resistance.text="Current Resistance: "+currentResistance;
            }
//            heartString.text= ""+hr;
//            System.out.println(progressCounter);
//            if(progressCounter<=0){
////                this.goalReached= new HudString(10, 220, "Goal Reached!",false,false);
//            }
//            else{
//                progressCounter-=10;
//            }
//     p       showHeartIcon();

        }
    }
//    public void setPopUpQuest(int questNum){
//
//    }
//    public void hidePopUpQuest(){
//
//    }
    public void showPopUpHUD(int dim){

        if (dim ==222){
            this.popUpQuest =new HudString(0, 0, "Soccer Quest",true,true);
        }
        else if (dim ==223){
            this.popUpQuest =new HudString(0, 0, "Fishing Quest",true,true);
        }
        showPopUpBG();
        this.popUpInstruction= new HudString(0,10,"Press X to accept the quest with extra reward. Press C to cancel",true,true);

    }
    public void hidePopUp(){
        this.popUpQuest.unregister();
        this.popUpInstruction.unregister();
    }
    public void hide(){
        heartString.unregister();
        goalString.unregister();
        minString.unregister();
        incline.unregister();
    }

//    @SubscribeEvent
//    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
//
//        if(event.player.dimension==0)
//        {
//            System.out.println("onPlayerTick in OuterHud");
//        }
//    }

}
