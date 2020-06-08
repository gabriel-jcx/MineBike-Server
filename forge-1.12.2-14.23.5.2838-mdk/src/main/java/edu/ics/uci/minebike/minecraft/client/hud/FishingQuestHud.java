package edu.ics.uci.minebike.minecraft.client.hud;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import  edu.ics.uci.minebike.minecraft.quests.customQuests.FishingQuest;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;

import java.util.concurrent.TimeUnit;

public class FishingQuestHud {
    public HudString powerString;
    public HudString timerString;
    public HudRectangle powerBar;
    public HudRectangle powerLine;
    public HudString distanceString;
    public HudString gameTime;

    private int current_t=0;
    private int current_tt=0;
    public static int distance=4;
    public static int timer=10;
    public static int bar_min= -70;
    public static int bar_max=65;
    public int requiredPower=1;
    public int game_t=240;

    public FishingQuestHud(){

    }
    public void Initial_game_time(){
        this.gameTime= new HudString(-165, 20, "Time: "+game_t, true, false);
    }
    public void Initial_fishing_hud(){
        this.gameTime= new HudString(-165, 20, "Time: "+game_t, true, false);
        this.powerString = new HudString(-125, 20, "POWER LEVEL", true, false);
        this.distanceString = new HudString(-10, 35, "Distance "+ distance, true, false);
        this.timerString = new HudString(-10, 45, "The fish will run away in:  "+ timer+" seconds", true, false);
        this.powerBar= new HudRectangle(-70,0, 140, 30, 0xe4344aff,true,false);
        this.powerLine = new HudRectangle(-70,0, 5, 30, 0xffffffff,true,false);
    }
    public void update_hud(){

    }
    public void unreg_hud(){
        this.powerLine.unregister();
        this.distanceString.unregister();
        this.powerBar.unregister();
        this.powerString.unregister();
        this.timerString.unregister();
    }
    public void refresh_count_down() {
        if (current_tt != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
            current_tt = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            game_t -= 1;
            this.gameTime.text= "Time: "+game_t;
        }
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
    private int getPower()
    {
        //Todo:for bigx
        //return (BiGXPacketHandler.change * 4);
        return 1;
    }
}
