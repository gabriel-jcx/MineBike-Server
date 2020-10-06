package edu.ics.uci.minebike.minecraft.client.QuestHud;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import  edu.ics.uci.minebike.minecraft.quests.customQuests.FishingQuest;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
//import org.ngs.bigx.minecraft.bike.BiGXPacketHandler;

import java.util.concurrent.TimeUnit;

import static sun.misc.Version.print;

public class FishingQuestHud {
    //Hud
    public HudString powerString;
    public HudString timerString;
    public HudRectangle powerBar;
    public HudRectangle powerLine;
    public HudString distanceString;
    public HudString gameTime;

    private int fishCountDown=0;
    private int gameCountDown=0;
    public static int distance=4;
    public static int timer=10;
    static final int BAR_MIN= -70;
    static final int BAR_MAX=65;


    public int requiredGameTime =240;
    public int gameTimeDisplay=requiredGameTime;

    //this variable varies according to kids' prescription
    public int requiredPower=1;


    public FishingQuestHud(){


    }
    @SideOnly(Side.CLIENT)
    public void initializeGameTime(){
        this.gameTime= new HudString(-165, 20, "Time: "+gameTimeDisplay, true, false);
    }

    @SideOnly(Side.CLIENT)
    public void initializeFishingHud(){

//        this.gameTime= new HudString(-165, 20, "Time: "+gameTimeDisplay, true, false);
        this.powerString = new HudString(-125, 20, "POWER LEVEL", true, false);
        this.distanceString = new HudString(-10, 35, "Distance "+ distance, true, false);
        this.timerString = new HudString(-10, 45, "The fish will run away in:  "+ timer+" seconds", true, false);
        this.powerBar= new HudRectangle(-70,0, 140, 30, 0xe4344aff,true,false);
        this.powerLine = new HudRectangle(-70,0, 5, 30, 0xffffffff,true,false);
    }

    @SideOnly(Side.CLIENT)
    public void unregisterHud(){
        this.powerLine.unregister();
        this.distanceString.unregister();
        this.powerBar.unregister();
        this.powerString.unregister();
        this.timerString.unregister();
        this.gameTime.unregister();
    }
    @SideOnly(Side.CLIENT)
    public void refreshCountDown() {
        System.out.println("refreshCountDown");
        if (gameCountDown != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
            gameCountDown = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            gameTimeDisplay -= 1;
            if(gameTimeDisplay!=requiredGameTime){
                this.gameTime.setText("Time: "+gameTimeDisplay);
            }
            else{
                initializeGameTime();
            }

        }
    }

    @SideOnly(Side.CLIENT)
    public void refreshTimerString(){
        System.out.println("refreshing...................................");
        if (fishCountDown != (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) {
            fishCountDown = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            timer -= 1;

            // You don't need to new here, you can just simply modify this.timerString.text
//            this.timerString.unregister();
            this.timerString.setText( "The fish will run away in:  " + timer + " seconds");

        }
    }
    @SideOnly(Side.CLIENT)
    public void refreshPowerBar(){
        int currentPowerLineX=powerLine.getX();
        if (this.requiredPower==getPower()&&currentPowerLineX+5<=BAR_MAX) {
            this.powerLine.x=currentPowerLineX + 5;

        }else if (this.requiredPower!=getPower()&&currentPowerLineX-5>=BAR_MIN)
        {

            this.powerLine.x=currentPowerLineX - 5;

        }
    }

    @SideOnly(Side.CLIENT)
    public void reduceFishDistance(){
        if (this.powerLine.getX()==BAR_MAX && distance-1>=0)
        {
            distance-=1;
            this.distanceString.setText( "Distance "+ distance);
            if(distance==0)
            {
                ClientUtils.sendData(EnumPacketClient.FishingDistance,distance);

            }

//            this.distanceString = new HudString(-10, 35, "Distance "+ distance, true, false);
        }
    }

    //Copied from previous code. It detects the bike's current power.
    private int getPower()
    {
//        return BiGXPacketHandler.change * 4;
        return 1;
    }
}
