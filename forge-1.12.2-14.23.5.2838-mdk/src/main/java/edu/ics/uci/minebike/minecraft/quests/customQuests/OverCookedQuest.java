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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.Server;

public class OverCookedQuest extends AbstractCustomQuest {


    public int DIMID;
    public WorldServer overcookWs = DimensionManager.getWorld(this.DIMID);
//    public Vec3d questStartLocation;

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
    private int maxOrderCount = 5;
    private long timeLeft = 0;
    private long curWorldTime = 0;

    private long lastGenerated = 0;
    private long nextGenerated = 0;
    private OrderHolder orders;

    private long clientWaitTime = 0;
    private long clientStartWaitTime = 0;
    private long clientEndWaitTime = 0;
    private long clientStartTime = 0;
    private long clientEndTime = 0;


    public ArrayList<EntityPlayer> playersInGame = new ArrayList<>();
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
        Potion night_vision = Potion.getPotionById(16);
        Potion saturation = Potion.getPotionById(23);
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
                System.out.print("Teleported Player: " + player.getName() + " to Overcooked Quest Dim");
                player.addPotionEffect(new PotionEffect(night_vision, (int)(gameTime + waitTime)/1000 * 20, 5, false, false));
                player.addPotionEffect(new PotionEffect(saturation, (int)(gameTime + waitTime)/1000 * 20, 5, false, false));
                playersInGame.add(player);
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
        Item water = Item.getByNameOrId("water_bottle");

        Item[] all = new Item[] {chicken, lettuce, potato, water};
        Item[] veg = new Item[] {lettuce, potato, water};
        Item[] meat = new Item[] {chicken, potato, water};
        Item[] nopot = new Item[] {chicken, lettuce, steak, water};
        Item[] plain = new Item[] {chicken, lettuce, water};
        //Sandwiches
        recipes.add(new Recipe(sandwichbread, all,"Loaded Sandwich"));
        recipes.add(new Recipe(sandwichbread, veg, "Veggie Sandwich"));
        recipes.add(new Recipe(sandwichbread, meat, "Meat Lover's Sandwich"));
        recipes.add(new Recipe(sandwichbread, nopot,"Potato-Less Sandwich"));
        recipes.add(new Recipe(sandwichbread, plain,"Plain Sandwich"));
        //Hamburgers
        recipes.add(new Recipe(hamburgerbun, all,"Loaded Hamburger"));
        recipes.add(new Recipe(hamburgerbun, veg, "Veggie Hamburger"));
        recipes.add(new Recipe(hamburgerbun, meat, "Meat Lover's Hamburger"));
        recipes.add(new Recipe(hamburgerbun, nopot,"Potato-Less Hamburger"));
        recipes.add(new Recipe(hamburgerbun, plain,"Plain Hamburger"));

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
        regScore();

    }// This is the start interface for client

    @Override
    public void end()
    {
        if(!overcookWs.isRemote) {
            for (EntityPlayer Player : this.playersInGame) {
                ServerUtils.telport((EntityPlayerMP) Player, ChefGusteau.LOCATION, 0);
            }
            playersInGame.clear();
            resetWorldTime();
            System.out.println("Server ending, teleported all players back to overworld");
        } else {
            System.out.println("Client ending, unregistering HUD elements");
            hudTimer.unregister();
            scoreTitle.unregister();
            scoreVal.unregister();
            orders.endGame();
        }
        isStarted = false;
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
        if(serverStartWaitTime == 0){
            serverStartWaitTime = curTime;
        }
        int secsPassed = QuestUtils.getRemainingSeconds(curTime, serverStartWaitTime);
        if(secsPassed >= waitTime/1000){
            for(EntityPlayer player: this.playersInGame){
                this.start((EntityPlayerMP)player); // event game start triggered
            }
            isWaiting = false;
            isStarted = true;
            serverStartTime = System.currentTimeMillis();
            serverGameEndTime = serverStartTime + gameTime;
        }
        else if(curTime == serverStartWaitTime){
            updateWorldTime();
        }
    }

    public void serverStartTick()
    {
        //Need to add NPC interaction here to submit orders
        long curTime = System.currentTimeMillis();
        if(curTime >= serverGameEndTime){
            end();
        }
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
        if(curTime >= clientEndTime) {
            end();
        }else{
            if(curTime - clientStartTime < 3000) {
                hudTimer.text = "Start Cooking!";
            }else{
                hudTimer.text = QuestUtils.formatSeconds(QuestUtils.getRemainingSeconds(clientEndTime,curTime));
            }
            orders.update();
            checkExpiration();
            generateOrder();
            score += orders.update();
            scoreVal.text = Integer.toString(score);
        }


    }

    public void clientStartWaiting(String waitingTime){
        clientWaitTime = Long.parseLong(waitingTime);
        int waitingSeconds = QuestUtils.getRemainingSeconds(clientWaitTime);
        clientStartWaitTime = System.currentTimeMillis();
        clientEndWaitTime = clientStartWaitTime + clientWaitTime;
//        int clientWaitLeftSeconds = QuestUtils.getRemainingSeconds(clientEndWaitTime, clientStartWaitTime);
        hudTimer = new HudString(15,10, QuestUtils.formatSeconds(waitingSeconds),2.5f,true, false);
        isWaiting = true;
        orders = new OrderHolder();
    }

    public void generateOrder(){
        if(orders.size() <= maxOrderCount) {
            if (lastGenerated == 0) {
                orders.add(recipes.get((int) (Math.random() * recipes.size())));
                lastGenerated = System.currentTimeMillis();
                nextGenerated = lastGenerated + randTime();
                System.out.println("Next generation at : " + nextGenerated);
            } else if(System.currentTimeMillis() >= nextGenerated){
                    orders.add(recipes.get((int) (Math.random() * recipes.size())));
                    lastGenerated = System.currentTimeMillis();
                    nextGenerated = lastGenerated + randTime();
                    System.out.println("Generated a new food : " + orders.get(orders.size()-1).getName());
                    System.out.println("Next generation at : " + nextGenerated);
            }
        }
    }



    public void regScore(){
        scoreTitle = new HudString(-5,25, "Score:", 2.0f, true, false);
        scoreVal = new HudString(20, 25,Integer.toString(score), 2.0f, true, false);
    }

    public long randTime(){return (long)(Math.random() * 10000) + 20000;}

    public void updateWorldTime(){
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if(server.worlds != null && server.worlds.length > 0){
            curWorldTime = server.worlds[0].getWorldTime();
            for(World worl : server.worlds){
                worl.getGameRules().setOrCreateGameRule("doFireTick","false");
                worl.getGameRules().setOrCreateGameRule("doDaylightCycle","false");
                worl.setWorldTime(300);
            }
        }
    }

    public void resetWorldTime(){
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if(server.worlds != null && server.worlds.length > 0){
            for(World worl : server.worlds){
                worl.getGameRules().setOrCreateGameRule("doFireTick","true");
                worl.getGameRules().setOrCreateGameRule("doDaylightCycle","true");
                worl.setWorldTime(curWorldTime);
            }
        }
    }

    public void checkExpiration(){
        ArrayList<Long> times = orders.getExpiration();
        int len = times.size();
        if(len == 0)
        {
            return;
        }
        long curTime = System.currentTimeMillis();
        for(int i = 0 ; i < len ; i++){
            if(curTime >= times.get(i)){
                orders.expire(i);
                score += failedOrder;
                return;
            }
        }
    }
}
