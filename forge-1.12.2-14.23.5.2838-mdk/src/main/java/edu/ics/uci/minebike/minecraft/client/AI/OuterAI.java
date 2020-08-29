package edu.ics.uci.minebike.minecraft.client.AI;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.client.hud.OuterAIHud;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.constants.EnumQuestStatus;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.ngs.bigx.minecraft.BiGX;
import org.ngs.bigx.minecraft.client.AI.OuterAI.QuestStatus;
import java.util.*;
import java.util.concurrent.TimeUnit;

//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientInfo;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
//import org.ngs.bigx.minecraft.bike.BiGXPacketHandler;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientInfo;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
//import org.ngs.bigx.minecraft.bike.BiGXPacketHandler;

// should outer AI update QuestHeartRate
public class OuterAI {

    //Stores the quest/mini games' world dimension and their avg heart rate for that game
    public Map<Integer, Integer> questList;

//    public List<Integer> generalQuestList= new ArrayList<Integer>(){{
//        //soccer,fishing
//        add(222);
//        add(223);
//    }};


    // After init_threshold numbers of heart rate transfer, the OuterAI will start to interact the game
    private Integer init_threshold= 300;
    // If the kids does not play any mini game with in time_threshold numbers of heart rate transfer,
    private Integer time_threshold= 300;
    // Count down the inactive time
    private Integer gameTimeDisplayTimer=0;

    private boolean keyXPressed= false;

    private boolean popUpHudShowing= true;
    // local instance
    private static OuterAI instance = null;

    // NOTE: public variables




    // NOTE: private variables
    // bike vars
    private int currHR; // current heart rate
    private float currResistance = 0; // current resistance level
    private int prevSec;
    private AbstractQuestAI currentQuest;
    private EnumQuestStatus questStatus;
    private int idleSeconds = 600;
    private int idleCounter = 0;
    private boolean targetReached = false;

    // time related memebers

    private void readData(){
    }

    private OuterAI(){
        //
//        questStatus = QuestStatus.NONE;
    }
    private OuterAIHud hud= new OuterAIHud();

    public static OuterAI getInstance(){
        if(instance == null)
            instance = new OuterAI();
        return instance;
    }

    public int get_currHR() {
        return this.currHR;
    }

    public void set_currHR(int heartRate){
        this.currHR = heartRate;
    }

    public AbstractQuestAI getRunningQuest(){
        return this.currentQuest;
    }

    public boolean setRunningQuest(AbstractQuestAI questAI){ // checks if theres any game running
        if(this.currentQuest != null)
            return false;
        this.currentQuest = questAI;
        return true;
    }
    private void updateHR(){
        this.currHR = BiGX.instance().clientContext.heartrate;
    }

    private void updateResistance(){
        this.currResistance = BiGX.instance().clientContext.resistance;
    }
    private void checkPopUpQuest(){
        if(questStatus == EnumQuestStatus.None){
            idleCounter++;
            if(idleCounter >= idleSeconds){
                popUpQuest();
                popUpHudShowing=true;
                idleCounter = 0; // reset idle counter

            }
            //If player pressed X, discard the GUI
            if (Keyboard.isKeyDown(0x2D) && popUpHudShowing)
            {
                popUpHudShowing=false;
            }
            //If player pressed C, start the quest
            if (Keyboard.isKeyDown(0x2E) && popUpHudShowing)
            {
                ClientUtils.sendData(EnumPacketClient.PlayerJoin,currentQuest);
                popUpHudShowing=false;
            }
        }
        else if (questStatus==EnumQuestStatus.InProgress){
            gameTimeDisplayTimer=0;
        }
        else{
            System.out.println("Error: Invalid questStatus");
        }


    }

    public boolean reach_target(){
        return targetReached;
    }
    // Update the heart rate / resistance value every second
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        //kids just started the game, don't modify

        int currSec = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        if(currSec != prevSec){  // update every second
            updateHR();
            prevSec = currSec;
            checkPopUpQuest();

        }
        if (questStatus==EnumQuestStatus.None) {


        }

    }


    //TODO: if the kids not in mini-game, and avg heart rate does not reach the goal for __ mins, pop up a quest
    public void popUpQuest(){
        if(questList.size()==0){
            //Randomly generate a quest
            Random rand = new Random();
            currentQuest = QuestDatabase.getQuestsAIs(rand.nextInt(generalQuestList.size()));
        }
        else{
            currentQuest = questList.get(Collections.max(questList.keySet()));

            //TODO: spawn the quest, pop up hud may be?
        }
        hud.showPopUpHUD(currentQuest);
    }

}
