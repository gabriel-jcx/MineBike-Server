package edu.ics.uci.minebike.minecraft.client.AI;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.client.AI.CustomQuestAI.MinerAI;
import edu.ics.uci.minebike.minecraft.client.hud.OuterAIHud;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketClient;
import edu.ics.uci.minebike.minecraft.constants.EnumQuestStatus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
//import org.ngs.bigx.minecraft.BiGX;
//import org.ngs.bigx.minecraft.client.AI.OuterAI.QuestStatus;
import java.util.*;
import java.util.concurrent.TimeUnit;

//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientInfo;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
//import org.ngs.bigx.minecraft.bike.BiGXPacketHandler;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientInfo;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
//import org.ngs.bigx.minecraft.bike.BiGXPacketHandler;

// should outer AI update QuestHeartRate
@SideOnly(Side.CLIENT)
public class OuterAI {



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

    private boolean popUpHudShowing= false;
    // local instance
    private static OuterAI instance = null;

    public static GamePlayTracker gamePlayTracker = GamePlayTracker.getInstance();

    public OuterAIHud hud = null;
    // NOTE: public variables






    // NOTE: private variables
    // bike vars
    private int currHR; // current heart rate
    private float currResistance = 0; // current resistance level
    private int prevSec;
    private AbstractQuestAI currentQuestAI;
    private EnumQuestStatus questStatus = EnumQuestStatus.None;
    private int idleSeconds = 180;
    private int idleCounter = 0;
    private boolean targetReached = false;

    private PlayerBehaviorAnalyzer playerBehaviorAnalyzer = null;
    private BiGXPatientPrescription prescription  = null;

    // time related memebers
    //Stores the quest/mini games' world dimension and their avg heart rate for that game
    //public Map<Integer, Integer> questList; // Quest List should be included in the game tracker
    private void readData(){
    }

    public OuterAI(){
        instance = this;
        prescription = null;// TODO: read the patient prescription form
        hud = OuterAIHud.getInstance();
        playerBehaviorAnalyzer =  new PlayerBehaviorAnalyzer();
//        questStatus = QuestStatus.NONE;
    }

    public static void setGamePlayTracker(GamePlayTracker gpt){
        gamePlayTracker = gpt;
    }

    public static OuterAI getInstance(){
        if(instance == null)
            instance = new OuterAI();
        return instance;
    }

    public int getCurrHR() {
        return this.currHR;
    }

    public void setCurrHR(int heartRate){
        this.currHR = heartRate;
    }

    public AbstractQuestAI getRunningQuest(){
        return this.currentQuestAI;
    }

    public boolean setRunningQuest(AbstractQuestAI questAI){ // checks if theres any game running
        if(this.currentQuestAI != null)
            return false;
        this.currentQuestAI = questAI;
        return true;
    }
//    private void updateHR(){
//        this.currHR = BiGX.instance().clientContext.heartrate;
//    }
//
//    private void updateResistance(){
//        this.currResistance = BiGX.instance().clientContext.resistance;
//    }

    private void checkPopUpQuest(){
        if(questStatus == EnumQuestStatus.None){
            idleCounter++;
            if(idleCounter >= idleSeconds){
                playerBehaviorAnalyzer.findAndSetPopupQuest();
                popUpHudShowing=true;
                hud.displayPopUpHUD(currentQuestAI);
                idleCounter = 0; // reset idle counter
                System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

            }
            //If player pressed X, discard the GUI
            if (Keyboard.isKeyDown(0x2D) && popUpHudShowing)
            {
                System.out.println("pressed X, discard the GUI");
                hud.hidePopUp();
                popUpHudShowing=false;
                idleCounter=0;
            }
            //If player pressed C, start the quest
            if (Keyboard.isKeyDown(0x2E) && popUpHudShowing)
            {
//                playerBehaviorAnalyzer.findAndSetPopupQuest();
                System.out.println("pressed C, start the quest");
                hud.hidePopUp();
                ClientUtils.sendData(EnumPacketClient.PlayerJoin, currentQuestAI.getQuestDim());
                popUpHudShowing=false;
                idleCounter=0;
                questStatus=EnumQuestStatus.InProgress;
            }
        }
        else if (questStatus==EnumQuestStatus.InProgress){
            gameTimeDisplayTimer=0;
        }
        else{
            System.out.println("Error: Invalid questStatus, current Quest Status is " + questStatus);
        }
    }



    public boolean isTargetHRReached(){
        return targetReached;
    }
    // Update the heart rate / resistance value every second
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        //kids just started the game, don't modify
//        if(event.side.isClient() && event.player.world.provider.getDimension() == 0){
//            hud.displayPopUpHUD(new MinerAI());
//        }

        int currSec = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        if(currSec != prevSec && event.side.isClient()){  // update every second
//            updateHR();
            prevSec = currSec;
            checkPopUpQuest();
            System.out.println("runing______________________");
//            hud.displayPopUpHUD(new MinerAI());

        }
//        if (questStatus==EnumQuestStatus.None) {
//
//
//        }

    }

// The function below has been moved to PBA class
//    //TODO: if the kids not in mini-game, and avg heart rate does not reach the goal for __ mins, pop up a quest
//    private void findAndSetPopupQuest(){
//        if(this.gamePlayTracker == null){
//
//            System.err.println("ERROR findAndSetPopupQuest(): the GamePlayTracker hasn't been initialized yet");
//        }
//        ArrayList<String> quests = this.gamePlayTracker.getPlayedQuests();
//        ArrayList<AbstractQuestAI> questAIs = QuestAIDatabase.getQuestsAIs();
//        if(quests.size() == 0){ // hasn't play any quests
//            // get random questAI from the AI database, and start the game
//            Random rand = new Random();
//            currentQuestAI = questAIs.get(rand.nextInt());
//        }else{
////            currentQuestAI = quests.get(Collections.max(questAIs.keySet()));
////            TODO: yet to be done!
//        }
//
////        if(questList.size()==0){
////            //Randomly generate a quest
////            Random rand = new Random();
////            currentQuestAI = QuestAIDatabase.getQuestsAIs(rand.nextInt(generalQuestList.size()));
////        }
////        else{
////            currentQuestAI = questList.get(Collections.max(questList.keySet()));
////
////        }
//        hud.displayPopUpHUD(currentQuestAI);
//    }

}
