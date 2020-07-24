package edu.ics.uci.minebike.minecraft.quests.customQuests;

import java.time.Clock;
import java.util.ArrayList;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.CommonUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.item.CookBurgerBun;
import edu.ics.uci.minebike.minecraft.item.CookLettuce;
import edu.ics.uci.minebike.minecraft.item.CookSandwichBread;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.ChefGusteau;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Gordon;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.QuestUtils;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderOverCooked;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.Server;

public class OverCookedQuest extends AbstractCustomQuest {


    public int DIMID;
    public Vec3d questStartLocation;

    private boolean isStarted;
    private boolean isWaiting;



    private final int completeOrder = 30;
    private final int failedOrder = -10;
    private int score = 0;
    private int ticks = 0;

    private ArrayList<Vec3d> ingredientCoord;
    private ArrayList<Recipe> recipes = new ArrayList<>();


    private long gameTime = 480000; //ms 8min
    private long waitTime = 10000;//ms 10sec


    private long serverStartWaitTime = 0 ;
    private long serverEndWaitTime = 0;
    private long serverGameEndTime = 0;
    private long serverWaitTime = waitTime;
    private long serverStartTime = gameTime;
    private int maxPlayerCount = 4;
    private long timeLeft = 0;

    private long clientWaitTime = 0;
    private long clientStartWaitTime = 0;
    private long clientEndWaitTime = 0;
    private long clientStartTime = 0;
    private long clientEndTime = 0;


    public ArrayList<EntityPlayerMP> playersInGame = new ArrayList<>();
    public ArrayList<EntityPlayer> playersInQueue = new ArrayList<>();

    private HudRectangle rectangle;
    private HudString hudTimer;
//             = new HudRectangle(580,280, 50,50, 0x4aa188, true, false);
    private HudString scoreTitle;
//         = new HudString(590,290, "Score:");
    private HudString scoreVal;
    public int gameScore = 0;
//         = new HudString(600, 305, "0");


    public OverCookedQuest()
    {
        this.DIMID = WorldProviderOverCooked.DIM_ID;
        this.questStartLocation = new Vec3d(0,4,0);
        this.isStarted = false;
        this.isWaiting = false;
        setRecipes();
    }

    @SideOnly(Side.SERVER)
    @Override
    public boolean onPlayerJoin(EntityPlayer player){
        System.out.println("Player attempting to join");
        if(isStarted)
        {
//            setupQuestEnv(player.world, player);
            ServerUtils.sendQuestData(EnumPacketServer.QuestJoinFailed,(EntityPlayerMP)player, Long.toString(this.waitTime));
            timeLeft = (serverGameEndTime - System.currentTimeMillis())/1000;
            player.sendStatusMessage(new TextComponentTranslation("Game In Progress : Please Wait " + timeLeft + " Seconds", new Object[0]).setStyle((new Style()).setColor(TextFormatting.DARK_RED)),false);

            return false;
        }else{
            if (!isWaiting)
            {
//                WorldServer ws = DimensionManager.getWorld(this.DIMID);
                isWaiting = true;
                //Records current time and when waiting period ends. Starts Waiting. Sets End Time
                serverStartWaitTime = System.currentTimeMillis();
                serverEndWaitTime = serverStartWaitTime + waitTime;
                serverGameEndTime = serverEndWaitTime + gameTime;
            }
            if(playersInGame.size() <= maxPlayerCount) {
                ServerUtils.sendQuestData(EnumPacketServer.OverCookedWaitTime,(EntityPlayerMP)player,Long.toString(this.serverWaitTime));
                ServerUtils.telport((EntityPlayerMP)player, this.questStartLocation,this.DIMID);
                playersInGame.add((EntityPlayerMP)player);
                return true;
            } else {
                playersInQueue.add(player);
                ServerUtils.sendQuestData(EnumPacketServer.QuestJoinFailed,(EntityPlayerMP)player, Long.toString(this.waitTime));
                player.sendStatusMessage(new TextComponentTranslation("Game is currently full. Position in queue: " + playersInQueue.size(), new Object[0]).setStyle((new Style()).setColor(TextFormatting.DARK_RED)),false);

                return false;
            }
        }
    }

    @Override
    public void setupQuestEnv(World world, EntityPlayer player)
    {

    }

    @Override
    public Vec3d getStartLocation()
    {
        return questStartLocation;
    }

    public void setRecipes()
    {
//        recipes.add();
        Item sandwichbread = new CookSandwichBread();
        Item hamburgerbun = new CookBurgerBun();
        Item steak = Item.getByNameOrId("cooked_beef");
        Item lettuce = new CookLettuce();
        Item chicken = Item.getByNameOrId("cooked_chicken");
        Item potato = Item.getByNameOrId("potato");

        Item[] all = new Item[] {chicken, lettuce, potato};
        Item[] veg = new Item[] {lettuce, potato};
        Item[] meat = new Item[] {chicken, potato};
        Item[] nopot = new Item[] {chicken, lettuce, steak};
        Item[] plain = new Item[] {chicken, lettuce};
        //Sandwiches
        recipes.add(new Recipe(sandwichbread, all));
        recipes.add(new Recipe(sandwichbread, veg));
        recipes.add(new Recipe(sandwichbread, meat));
        recipes.add(new Recipe(sandwichbread, nopot));
        recipes.add(new Recipe(sandwichbread, plain));
        //Hamburgers
        recipes.add(new Recipe(hamburgerbun, all));
        recipes.add(new Recipe(hamburgerbun, veg));
        recipes.add(new Recipe(hamburgerbun, meat));
        recipes.add(new Recipe(hamburgerbun, nopot));
        recipes.add(new Recipe(hamburgerbun, plain));

    }

    //Server Side Start
    @SideOnly(Side.SERVER)
    @Override
    public void start(EntityPlayerMP player)
    {
        System.out.print("Started For Player" + player.getName());
        ServerUtils.sendQuestData(EnumPacketServer.QuestStart, player, Long.toString(this.DIMID));
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void start(EntityPlayerSP player)
    {
        isStarted = true;
    }

    @Override
    public void start(EntityJoinWorldEvent event)
    {

    }
    //Client Side Start Initializes Hud and whatnot
    @Override
    public void start()
    {
        clientStartTime = System.currentTimeMillis();
        clientEndTime = clientStartTime + gameTime;
        isWaiting = false;
        isStarted = true;

    }// This is the start interface for client

    @Override
    public void end()
    {
        hudTimer.unregister();
        for(EntityPlayerMP Player: playersInGame)
        {
            ServerUtils.telport((EntityPlayerMP)player, ChefGusteau.LOCATION,0);
        }
        playersInGame.removeAll(playersInGame);
    }

    @Override
    public void onWorldTick(TickEvent.WorldTickEvent event)
    {
        //Server Side
        if(!event.world.isRemote) {
            if (isWaiting) {
                serverWaitTick();
            } else if (isStarted) {
                serverStartTick();
            }
        }
    }


    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(isWaiting){
            clientWaitTick();
        }else if(isStarted){
            clientStartTick();
        }
    }

    public void serverWaitTick()
    {
        long curTime = System.currentTimeMillis();
        int secsPassed = QuestUtils.getRemainingSeconds(curTime, serverStartWaitTime);

        if(secsPassed >= waitTime/1000){
            for(EntityPlayerMP player: this.playersInGame){
                this.start(player); // event game start triggered
            }

            isWaiting = false;
            isStarted = true;
            serverStartTime = System.currentTimeMillis();
            serverGameEndTime = serverStartTime + gameTime;
            DimensionManager.getWorld(this.DIMID).setWorldTime(500);
        }

    }

    public void serverStartTick()
    {
        //Need to add NPC interaction here to submit orders
    }

    public void clientWaitTick(){
        clientWaitTime = clientEndWaitTime - System.currentTimeMillis();
        int remainingWait = QuestUtils.getRemainingSeconds(clientWaitTime);
        if(remainingWait >= 0)
        {
            hudTimer.text = QuestUtils.formatSeconds(remainingWait);
        }
    }

    public void clientStartTick(){
        long curTime = System.currentTimeMillis();
        if(curTime - clientStartTime < 3000) {
            hudTimer.text = "Start Cooking!";
        } else if(curTime >= clientEndTime){
            end();
        }else{
            hudTimer.text = QuestUtils.formatSeconds(QuestUtils.getRemainingSeconds(clientEndTime,curTime));
        }

    }

    public void clientStartWaiting(String waitingTime){
        clientWaitTime = Long.parseLong(waitingTime);
        int waitingSeconds = QuestUtils.getRemainingSeconds(clientWaitTime);
        clientStartWaitTime = System.currentTimeMillis();
        clientEndWaitTime = clientStartWaitTime + clientWaitTime;
//        int clientWaitLeftSeconds = QuestUtils.getRemainingSeconds(clientEndWaitTime, clientStartWaitTime);
        hudTimer = new HudString(0,35, QuestUtils.formatSeconds(waitingSeconds),2.0f,true, false);
        isWaiting = true;
        regScore();
    }

    public void generateOrder(){

    }

    public void regScore(){
        scoreTitle = new HudString(-5,25, "Score", 2.0f, true, false);
        scoreVal = new HudString(5, 25,Integer.toString(score), 2.0f, true, false);
    }

    public void increaseScore(){score += completeOrder;}
    public void decreaseScore(){score -= failedOrder;}
    public int addNextItem()
    {
        return (int)(Math.random() * recipes.size());
    }

}
