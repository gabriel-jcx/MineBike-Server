package edu.ics.uci.minebike.minecraft.client.hud;
import edu.ics.uci.minebike.minecraft.BiGXMain;
import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.client.AI.AbstractQuestAI;
import edu.ics.uci.minebike.minecraft.client.AI.OuterAI;
import edu.ics.uci.minebike.minecraft.client.HudManager;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.quests.QuestUtils;
import  edu.ics.uci.minebike.minecraft.quests.customQuests.FishingQuest;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
//import org.ngs.bigx.minecraft.BiGX;
//import org.ngs.bigx.minecraft.context.BigxClientContext;
//import org.ngs.bigx.minecraft.BiGX;
//import org.ngs.bigx.minecraft.context.BigxClientContext;
//import org.ngs.bigx.minecraft.client.ClientEventHandler;


import java.util.concurrent.TimeUnit;

public class OuterAIHud {
    public HudString heartString;
    public HudString resistance;
    public HudString goalString;
    public HudString goalReached;
    public HudString popUpQuest;
    public HudString popUpInstruction;
    public HudRectangle popUpBG;
    private int minGoal=0;
    private int maxGoal=150;
    private int hr=0;
//    private Minecraft mc = Minecraft.getMinecraft();
//    private HudManager hudManager= HudManager.getInstance(this.mc);

    public HudString minString;
    private int progressCounter=90;
    private int secCounter=0;

    public HudString incline;

    private static OuterAIHud instance = null;
    private int level=0;
    private int currentTime=0;
    private float currentResistance=0;
//    private OuterAI outerAI = null;

    ResourceLocation heartLocation= new ResourceLocation(BiGXMain.MOD_ID +":textures/heart.png");
    ResourceLocation popUpBackGround= new ResourceLocation(BiGXMain.MOD_ID +":textures/bg.png");




    public OuterAIHud(){
        instance = this;

//        outerAI = OuterAI.getInstance();
    }
    public static OuterAIHud getInstance(){
        if(instance == null)
            instance = new OuterAIHud();
        return instance;
    }
    @SideOnly(Side.CLIENT)
    public void showHeartIcon(){

        Minecraft mc = Minecraft.getMinecraft();

        mc.getTextureManager().bindTexture(heartLocation);

        mc.ingameGUI.drawTexturedModalRect(0,0,0,0,40,40);
        mc.ingameGUI.drawTexturedModalRect(5,40,40,5,25,80);
        mc.ingameGUI.drawTexturedModalRect(5,40+progressCounter,70,5+progressCounter,25,80);

    }
//    @SideOnly(Side.CLIENT)
//    public void showPopUpBG(){
//
//        this.popUpBG= new HudRectangle(0,0,90,40,200,true,true);
////        Minecraft mc = Minecraft.getMinecraft();
////        mc.getTextureManager().bindTexture(popUpBackGround);
////        mc.ingameGUI.drawTexturedModalRect(5,40+progressCounter,70,5+progressCounter,25,80);
//
//    }
    public void showHud_progress(){

    }
    @SideOnly(Side.CLIENT)
    public void showHud(int heartRate){
        this.heartString= new HudString(14,16, ""+ heartRate,false,false);
        this.goalString= new HudString(50, 16, "Heart Rate Goal: "+minGoal+"-"+maxGoal, false, false);
//        this.goalReached= new HudString(10, 220, "Goal Reached!",false,false);
        this.resistance= new HudString(10, 200, "Current Resistance: "+currentResistance,false,false);
//        this.minString = new HudString(400, 10, "Time Played: "+progressCounter+"m", false, false);
//        this.incline = new HudString(10, 400, "Current working level: "+ level, false, false);
    }
    @SideOnly(Side.CLIENT)
    public void refresh(){
        int curr_sec = QuestUtils.getSeconds(System.currentTimeMillis());
        if(progressCounter<=0){
            this.goalReached= new HudString(10, 220, "Goal Reached!",false,false);
        }
        if (currentTime != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
            currentTime = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            hr+=1; // TODO: hr += 1 is a temp place holder need to read from actual heartrate;

//            System.out.println(((BigxClientContext) BiGX.instance().clientContext).resistance);
//
//            if(((BigxClientContext) BiGX.instance().clientContext).resistance!= currentResistance)
//            {
//                currentResistance=((BigxClientContext) BiGX.instance().clientContext).resistance;
//                this.resistance.setText("Current Resistance: "+currentResistance);
//            }


        }
    }

    @SideOnly(Side.CLIENT)
    public void displayPopUpHUD(AbstractQuestAI questAI){
        // TODO: yet to be completed need to map each AI to its questName/dimemnsion????


        this.popUpBG= new HudRectangle(-200,-30,400,60, 0xe4344aff,true,true);

        this.popUpQuest = new HudString(0, 0, "Attention! "+questAI.getQuestName()+" quest with extra reward!",true,true);

        this.popUpInstruction= new HudString(0,10,"Press B to accept the quest with extra reward.Press V to cancel",true,true);

    }
    @SideOnly(Side.CLIENT)
    public void hidePopUp(){
        this.popUpQuest.unregister();
        this.popUpInstruction.unregister();
        this.popUpBG.unregister();
    }
    @SideOnly(Side.CLIENT)
    public void hide(){
        heartString.unregister();
        goalString.unregister();
        minString.unregister();
        incline.unregister();
    }



}
