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

import java.util.concurrent.TimeUnit;

public class OuterAIHud {
    public HudString heartString;
    private int hr=0;
    public HudString goalString;
    private int minGoal=0;
    private int maxGoal=150;

    public HudString minString;
    private int minCount=0;
    private int secCounter=0;

    public HudString incline;
    //TODO: need API
    private int level=0;
    private int current_t=0;

    public boolean heart_shown=false;

    ResourceLocation heartLocation= new ResourceLocation(BiGXMain.MOD_ID +":textures/heart.png");


    public OuterAIHud(){

    }
    public void show_heart(){
//        System.out.println(heartLocation+".........................................");
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(heartLocation);
//        mc.currentScreen.drawTexturedModalRect(10,10,0,0,40,40);
        mc.ingameGUI.drawTexturedModalRect(0,0,0,0,40,40);

    }
    public void show(){
        this.heartString= new HudString(10,10, ""+hr,false,false);
        this.goalString= new HudString(50, 10, "Heart Rate Goal:"+minGoal+"-"+maxGoal, false, false);
        this.minString = new HudString(400, 10, "Time Played: "+minCount+"m", false, false);
        this.incline = new HudString(10, 400, "Current working level: "+ level, false, false);
    }
    public void refresh(){
        if (current_t != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
            current_t = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            hr+=1;
            heartString.text= ""+hr;

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
