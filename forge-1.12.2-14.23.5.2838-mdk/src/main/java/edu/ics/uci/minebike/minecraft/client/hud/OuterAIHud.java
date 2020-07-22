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

import org.ngs.bigx.minecraft.BiGX;
import org.ngs.bigx.minecraft.context.BigxClientContext;
//import org.ngs.bigx.minecraft.BiGX;
//import org.ngs.bigx.minecraft.bike.BiGXPacketHandler;
//import org.ngs.bigx.minecraft.context.BigxClientContext;
import org.ngs.bigx.minecraft.client.ClientEventHandler;


import java.util.concurrent.TimeUnit;

public class OuterAIHud {
    public HudString heartString;
    private int hr=0;
    public HudString goalString;
    public HudString goalReached;
    private int minGoal=0;
    private int maxGoal=150;

    public HudString minString;
    private int minCount=90;
    private int secCounter=0;

    public HudString incline;
    //TODO: need API
    private int level=0;
    private int current_t=0;
    private int rr=15;

    public boolean heart_shown=false;

    ResourceLocation heartLocation= new ResourceLocation(BiGXMain.MOD_ID +":textures/heart.png");


    public OuterAIHud(){

    }
    public void show_heart(){
        System.out.println(((BigxClientContext) BiGX.instance().clientContext).heartrate);
//        System.out.println(heartLocation+".........................................");
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(heartLocation);
//        mc.currentScreen.drawTexturedModalRect(10,10,0,0,40,40);


        mc.ingameGUI.drawTexturedModalRect(0,0,0,0,40,40);
        mc.ingameGUI.drawTexturedModalRect(5,40,40,5,25,80);
        mc.ingameGUI.drawTexturedModalRect(5,40+minCount,70,5+minCount,25,80);

    }
    public void show_progress(){

    }
    public void show(){
        this.heartString= new HudString(14,16, ""+hr,false,false);
        this.goalString= new HudString(50, 16, "Heart Rate Goal: "+minGoal+"-"+maxGoal, false, false);
        this.goalReached= new HudString(10, 200, "Resistance Changed to"+rr,false,false);
//        this.minString = new HudString(400, 10, "Time Played: "+minCount+"m", false, false);
//        this.incline = new HudString(10, 400, "Current working level: "+ level, false, false);
    }
    public void refresh(){
        System.out.println(((BigxClientContext) BiGX.instance().clientContext).resistance);
        ClientEventHandler.getHandler().updateResistance(5);

//        ((BigxClientContext) BiGX.instance().clientContext).resistance= BigxClientContext.Resistance.LOW;

        if(minCount<=0){
            this.goalReached= new HudString(10, 220, "Goal Reached!",false,false);
        }
        if (current_t != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
            current_t = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            hr+=1;
            System.out.println(((BigxClientContext) BiGX.instance().clientContext).resistance);

            ((BigxClientContext) BiGX.instance().clientContext).resistance=(float)hr;


            heartString.text= ""+hr;
            System.out.println(minCount);
            if(minCount<=0){
//                this.goalReached= new HudString(10, 220, "Goal Reached!",false,false);
            }
            else{
                minCount-=10;
            }
//            show_heart();

        }
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
