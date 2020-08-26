package edu.ics.uci.minebike.minecraft.client.AI;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.*;
import java.util.concurrent.TimeUnit;

//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientInfo;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
//import org.ngs.bigx.minecraft.bike.BiGXPacketHandler;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientInfo;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
//import org.ngs.bigx.minecraft.bike.BiGXPacketHandler;

// should outer AI update QuestHeartRate
public class OuterAI extends QuestHeartRate{

    //Stores the quest/mini games' world dimension and their avg heart rate for that game
    public Map<Integer, Integer> questList;

    public List<Integer> generalQuestList= new ArrayList<Integer>(){{
        //soccer,fishing
        add(222);
        add(223);
    }};

    // After init_threshold numbers of heart rate transfer, the OuterAI will start to interact the game
    private Integer init_threshold= 300;
    // If the kids does not play any mini game with in time_threshold numbers of heart rate transfer,
    private Integer time_threshold= 300;
    // Count down the inactive time
    private Integer gameTimeDisplayTimer=0;

    private boolean keyXPressed= false;

    private boolean popUpHudShowing= true;



    //Assign mini game AI to this variable
    public QuestHeartRate running_game;
    //Check the whether there is a mini game running or not
    public QuestStatus questStatus=QuestStatus.NONE;

    public OuterAI(){

    }

    public Integer getCurrent_heart_rate() {
        return current_heart_rate;
    }

    //Check whether the heart rate reach the goal
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        //kids just started the game, don't modify

        Integer curr = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        if (questStatus==QuestStatus.NONE) {
            if (time != curr) {
                time = curr;
                gameTimeDisplayTimer+=1;
                //kids just started the game, don't modify
                if (num_heart_rate< init_threshold){
                    ;
                }
                //if kids are doing nothing in outer world, pop up a quest
                else if( gameTimeDisplayTimer>time_threshold && !reach_target())
                {
                    pop_up_quest();
                }


            }
            //If player pressed X, discard the GUI
            if (Keyboard.isKeyDown(0x2D) && popUpHudShowing)
            {
                popUpHudShowing=false;
            }
            //If player pressed C, start the quest
            if (Keyboard.isKeyDown(0x2E) && popUpHudShowing)
            {

                ClientUtils.sendData(EnumPacketClient.PlayerJoin,"222");
                popUpHudShowing=false;
            }
        }
        else if (questStatus==QuestStatus.RUNNING){
            gameTimeDisplayTimer=0;

        }
        else{
            System.out.println("Error: Invalid questStatus");
        }

    }


    //TODO: if the kids not in mini-game, and avg heart rate does not reach the goal for __ mins, pop up a quest
    public void pop_up_quest(){
        if(questList.size()==0){
            //Randomly generate a quest
            Random rand = new Random();
            int randomQuest = generalQuestList.get(rand.nextInt(generalQuestList.size()));

        }
        else{
            int bestQuest = questList.get(Collections.max(questList.keySet()));
            //TODO: spawn the quest, pop up hud may be?
        }
    }

    public enum QuestStatus {
        RUNNING,
        NONE
    }
}
