package edu.ics.uci.minebike.minecraft.quests.customQuests;

import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.CommonUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.HudManager;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.client.hud.HudTexture;
import edu.ics.uci.minebike.minecraft.item.*;
import edu.ics.uci.minebike.minecraft.npcs.AbstractCustomNpc;
import edu.ics.uci.minebike.minecraft.npcs.NpcDatabase;
import edu.ics.uci.minebike.minecraft.npcs.NpcUtils;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.ChefGusteau;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Gordon;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Manager;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Shuttle;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.QuestUtils;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderOverCooked;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.state.IBlockState;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.Server;
import noppes.npcs.entity.EntityCustomNpc;
import org.lwjgl.Sys;
import sun.plugin2.util.ColorUtil;

public class OverCookedQuest extends AbstractCustomQuest {


    public int DIMID;
    public WorldServer overcookWs = DimensionManager.getWorld(this.DIMID);
//    public Vec3d questStartLocation;

    private boolean isStarted;
    private boolean isWaiting;



    private final int completeOrder = 30;
    private final int failedOrder = -5;
    private int score = 0;
    private int ticks = 0;
    private boolean sameBlock;

    private Map<String, BlockPos> stations = new HashMap<>();
    private ArrayList<Recipe> recipes = new ArrayList<>();



    private long gameTime = 480000; //ms 8min
    private long waitTime = 10000;//ms 10sec
    private long expirationTime = 90000;//1.5mins


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
    public static OrderHolder orders;

    private long clientWaitTime = 0;
    private long clientStartWaitTime = 0;
    private long clientEndWaitTime = 0;
    private long clientStartTime = 0;
    private long clientEndTime = 0;


    public ArrayList<EntityPlayer> playersInGame = new ArrayList<>();
    public ArrayList<EntityPlayer> playersInQueue = new ArrayList<>();
    public static ArrayList<Recipe> curOrders = new ArrayList<>();
    public static ArrayList<Long> orderAddTime = new ArrayList<>();


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
                overcookWs = DimensionManager.getWorld(this.DIMID);
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

    //Adding all recipes to the recipes arraylist
    public void setRecipes()
    {
//        recipes.add();
        ResourceLocation bread = new ResourceLocation("minebikemod:sandwichbread");
        ResourceLocation bun = new ResourceLocation("minebikemod:hamburgerbun");
        ResourceLocation lett = new ResourceLocation("minebikemod:lettuce");

        Item sandwichbread = Item.REGISTRY.getObject(bread);
        Item hamburgerbun = Item.REGISTRY.getObject(bun);
        Item steak = Items.COOKED_BEEF;
        Item lettuce = Item.REGISTRY.getObject(lett);
        Item chicken = Items.COOKED_CHICKEN;
        Item potato = Items.BAKED_POTATO;
        Item water = Items.POTIONITEM;

        Item[] all = new Item[] {chicken, lettuce, potato, water, steak};
        Item[] veg = new Item[] {lettuce, potato, water};
        Item[] meat = new Item[] {chicken, potato, water, steak};
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
        updateWorldTime();
        setTPLocations();
        spawnNPCs();
        sameBlock = false;
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
    //Client Side Start Initializes Hud and start/end time
    @Override
    public void start()
    {
        clientStartTime = System.currentTimeMillis();
        clientEndTime = clientStartTime + gameTime;
        isWaiting = false;
        isStarted = true;
        regScore();
        HudTexture test = new HudTexture(50,10,70,70,"textures/GUI/burger-all.png");

    }// This is the start interface for client

    //Server Side - TPs player to overworld and clears all arraylists
    //Client Side - Unregisters all HUDs
    @Override
    public void end()
    {
        if(overcookWs != null && !overcookWs.isRemote) {
            for (EntityPlayer Player : this.playersInGame) {
                ServerUtils.telport((EntityPlayerMP) Player, ChefGusteau.LOCATION, 0);
                EntityPlayerMP playerMP = (EntityPlayerMP) Player;
                Player.sendStatusMessage(new TextComponentTranslation("The final score was: " + score, new Object[0]).setStyle((new Style()).setColor(TextFormatting.YELLOW)), false);
            }
            playersInGame.clear();
            resetWorldTime();
            System.out.println("Server ending, teleported all players back to overworld");
            curOrders.clear();
            orderAddTime.clear();
            isStarted = false;
            serverWaitTime = waitTime;
        } else{
            System.out.println("Client ending, unregistering HUD elements");
            HudManager.getInstance(Minecraft.getMinecraft()).shape_lock.lock();
            try{
                hudTimer.unregister();
                scoreTitle.unregister();
                scoreVal.unregister();
            }finally{
                HudManager.getInstance(Minecraft.getMinecraft()).shape_lock.unlock();
            }

            orders.endGame();
            isStarted = false;
            score = 0;
        }
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

    //Server side tick during waiting period starts time recordings
    public void serverWaitTick()
    {

        long curTime = System.currentTimeMillis();
        if(serverStartWaitTime == 0){
            serverStartWaitTime = curTime;
        }
        int secsPassed = QuestUtils.getRemainingSeconds(curTime, serverStartWaitTime);
        serverWaitTime = curTime - serverStartWaitTime;
        if(secsPassed >= waitTime/1000){
            System.out.println("Trigger start for players");
            for(EntityPlayer player: this.playersInGame){
                this.start((EntityPlayerMP)player); // event game start triggered
            }
            isWaiting = false;
            isStarted = true;
            serverStartTime = System.currentTimeMillis();
            serverGameEndTime = serverStartTime + gameTime;
        }
//        else if(curTime == serverStartWaitTime){
//            updateWorldTime();
//        }
    }

    //Server side tick during game - Generates, expires orders, checks if player is on redstone block to give or update item
    public void serverStartTick()
    {
        //Need to add NPC interaction here to submit orders
        long curTime = System.currentTimeMillis();
        if(curTime >= serverGameEndTime){
            if(overcookWs.getBlockState(stations.get("Beacon")).getBlock() == Blocks.STAINED_GLASS_PANE) {
                System.out.println("Reset Beacon Color");
                overcookWs.setBlockState(stations.get("Beacon"), Blocks.AIR.getDefaultState());
            }
            end();
        }else{
            checkExpiration();
            generateOrder();
            for(EntityPlayer player: playersInGame){
                EntityPlayerMP playMP = (EntityPlayerMP) player;
                BlockPos cur = (playMP.getPosition());
                BlockPos actual = new BlockPos(cur.getX(), cur.getY()-1, cur.getZ());
                if(overcookWs.getBlockState(actual).getBlock() != null && overcookWs.getBlockState(actual).getBlock() == Blocks.REDSTONE_BLOCK){
                    if(!sameBlock) {
                        checkPosition((EntityPlayerMP) player);
                    }
                }
                else {
                    sameBlock = false;
                }
            }
            if(serverGameEndTime - curTime <= 60000){
                if(overcookWs.getBlockState(stations.get("Beacon")).getBlock() == Blocks.AIR)
                    overcookWs.setBlockState(stations.get("Beacon"), Blocks.STAINED_GLASS_PANE.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.RED));
            }
        }
    }

    //Client side tick during waiting period
    public void clientWaitTick(){
        clientWaitTime = clientEndWaitTime - System.currentTimeMillis();
        int remainingWait = QuestUtils.getRemainingSeconds(clientWaitTime);
        if(remainingWait >= 0)
        {
            hudTimer.text = QuestUtils.formatSeconds(remainingWait);
        }
    }

    //Starts the game for timer side and updates the times on the orders
    public void clientStartTick(){
        long curTime = System.currentTimeMillis();
        if(curTime >= clientEndTime) {
            System.out.println("Client is ending");
            end();
        }else{
            if(curTime - clientStartTime < 3000) {
                hudTimer.text = "Start Cooking!";
            }else{
                hudTimer.text = QuestUtils.formatSeconds(QuestUtils.getRemainingSeconds(clientEndTime,curTime));
            }
            orders.update(curTime);
            scoreVal.text = Integer.toString(score);
        }
    }

    //Initializes time for the client side as well as timer HUD
    public void clientStartWaiting(String waitingTime){
        overcookWs = DimensionManager.getWorld(this.DIMID);
        clientWaitTime = Long.parseLong(waitingTime);
        int waitingSeconds = QuestUtils.getRemainingSeconds(clientWaitTime);
        clientStartWaitTime = System.currentTimeMillis();
        clientEndWaitTime = clientStartWaitTime + clientWaitTime;
//        int clientWaitLeftSeconds = QuestUtils.getRemainingSeconds(clientEndWaitTime, clientStartWaitTime);
        hudTimer = new HudString(15,10, QuestUtils.formatSeconds(waitingSeconds),2.5f,true, false);
        isWaiting = true;
        orders = new OrderHolder();
    }

    //Server side generation of order and calculates when next order should be generated
    public void generateOrder(){
        if(curOrders.size() <= maxOrderCount) {
            int next = (int) (Math.random() * recipes.size());
            if (lastGenerated == 0) {
                for(EntityPlayer player : playersInGame) {
                    ServerUtils.sendQuestData(EnumPacketServer.OverCookedNewOrder, (EntityPlayerMP) player, Integer.toString(next));
                }
                System.out.println("Next food is " + recipes.get(next));

                curOrders.add(recipes.get(next));
                orderAddTime.add(System.currentTimeMillis());
                lastGenerated = System.currentTimeMillis();
                nextGenerated = lastGenerated + randTime();
                System.out.println("Next generation at : " + nextGenerated);
            } else if(System.currentTimeMillis() >= nextGenerated || curOrders.size() == 0){
                for(EntityPlayer player : playersInGame) {
                    ServerUtils.sendQuestData(EnumPacketServer.OverCookedNewOrder, (EntityPlayerMP) player, Integer.toString(next));
                }
                System.out.println("Next food is " + recipes.get(next));
                curOrders.add(recipes.get(next));
                orderAddTime.add(System.currentTimeMillis());
                lastGenerated = System.currentTimeMillis();
                nextGenerated = lastGenerated + randTime();
                System.out.println("Next generation at : " + nextGenerated);
            }
        }
    }


    //Client side registering the HUD for score and scoreval
    public void regScore(){
        scoreTitle = new HudString(-5,25, "Score:", 2.0f, true, false);
        scoreVal = new HudString(20, 25,Integer.toString(score), 2.0f, true, false);
    }

    //generates random time for next order
    public long randTime(){return (long)(Math.random() * 10000) + 65000;}

    //Updates the world time to stop daynight and fire spread and time set
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

    //resets the time to when the game is started and resets the gamerules
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

    //Checks if the orders are expired on the server side
    public void checkExpiration(){

        int len = orderAddTime.size();
        if(len == 0){
            return;
        }
        long curTime = System.currentTimeMillis();
        for(int i = 0; i < len; i++){
            if(orderAddTime.get(i) + expirationTime < curTime){
                for(EntityPlayer player : playersInGame) {
                    ServerUtils.sendQuestData(EnumPacketServer.OverCookedOrderExpire, (EntityPlayerMP) player, Integer.toString(i));
                    orderAddTime.remove(i);
                    curOrders.remove(i);
                    if(score > 0)
                        score += failedOrder;
                    return;
                }
            }
        }
    }

    //Expires order on the client side   server -> client packet
    public void clientSideExpire(int expired)
    {
        orders.expire(expired);
        if(score > 0)
            score += failedOrder;
    }

    public void  clientSideComplete(int complete){
        System.out.println("A player has completed an order!");
        orders.complete(complete);
        score += completeOrder;
    }

    //Adds new order generated from server to client side server -> client packet
    public void newOrder(int added){
        orders.add(recipes.get(added),expirationTime);
    }

    //Checks where the player is to determine which station they are at
    public void checkPosition(EntityPlayerMP playerMP){
        Potion slowness = Potion.getPotionById(2);
        BlockPos pos = playerMP.getPosition();
        BlockPos actual = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
        int x = actual.getX();
        int y = actual.getY();
        int z = actual.getZ();
        for(String key : stations.keySet()){
            BlockPos curVal = stations.get(key);
            //method .get[x,y,z]() is used to key the comparisons relevant with int to int comparison
            ItemStack curHand = playerMP.getHeldItemMainhand();
            InventoryPlayer inv = playerMP.inventory;
            System.out.println("Current map value is" + curVal);
            if(pos.equals(curVal)){
                System.out.println("On a station");
                System.out.println("Player is holding " + curHand.getDisplayName());
                if (key.contains("Cooking")){
                    if(playerMP.getHeldItemMainhand().getItem() == Items.CHICKEN){
                        System.out.println("Player is holding chicken");
                        playerMP.inventory.setInventorySlotContents(playerMP.inventory.currentItem, new ItemStack(Items.COOKED_CHICKEN));
                        playerMP.addPotionEffect(new PotionEffect(slowness, 3 * 20, 255, false, false));
                    }
                    else if (playerMP.getHeldItemMainhand().getItem() == Items.BEEF){
                        System.out.println("Player is holding beef");
                        playerMP.inventory.setInventorySlotContents(playerMP.inventory.currentItem, new ItemStack(Items.COOKED_BEEF));
                        playerMP.addPotionEffect(new PotionEffect(slowness, 3 * 20, 255, false, false));
                    }
                }else if(key.contains("Water")){
                    if(playerMP.getHeldItemMainhand().getItem() == Items.AIR){
                        System.out.println("Player is holding nothing");
                        playerMP.inventory.setInventorySlotContents(playerMP.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
                    }
                }else if(key.contains("Cutting")){
                    System.out.println("Checking Assembly");
                    for(Recipe rec : curOrders){
                        if(rec.canMake(playerMP)){
                            System.out.println("Player can make: " + rec.getName());
                            removeIngredients(playerMP, rec);
                            if(rec.getName().contains("Sandwich"))
                            {
                                playerMP.inventory.addItemStackToInventory(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("minebikemod:sandwich"))).setStackDisplayName(rec.getName()));
                                playerMP.sendStatusMessage(new TextComponentTranslation("Made A " + rec.getName(), new Object[0]).setStyle((new Style()).setColor(TextFormatting.GREEN)),false);
                            }else if(rec.getName().contains("Hamburger")){
                                playerMP.inventory.addItemStackToInventory(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("minebikemod:hamburger"))).setStackDisplayName(rec.getName()));
                                playerMP.sendStatusMessage(new TextComponentTranslation("Made A " + rec.getName(), new Object[0]).setStyle((new Style()).setColor(TextFormatting.GREEN)),false);
                            }
                            playerMP.addPotionEffect(new PotionEffect(slowness, 6 * 20, 255, false, false));
                            break;
                        }
                    }
                }else if(key.contains("Farm")) {
                    if (key.contains("Potato")) {
                        if (playerMP.getHeldItemMainhand().getItem() == Items.AIR) {
                            playerMP.inventory.setInventorySlotContents(playerMP.inventory.currentItem, new ItemStack(Items.POTATO));
                        }
                    } else if (key.contains("Lettuce")) {
                        if (playerMP.getHeldItemMainhand().getItem() == Items.AIR) {
                            playerMP.inventory.setInventorySlotContents(playerMP.inventory.currentItem, new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("minebikemod:lettuce"))));
                        }
                    }
                }
            //Exception of the previous IF-statement due to oven containing a row of redstone blocks and me not wanting to put each and everyone of them in map
            }else if(x >= stations.get("Oven1").getX() && x <= stations.get("Oven2").getX() && y == stations.get("Oven1").getY() && z == stations.get("Oven1").getZ()){
                if(playerMP.getHeldItemMainhand().getItem() == Items.POTATO){
                    playerMP.inventory.setInventorySlotContents(playerMP.inventory.currentItem, new ItemStack(Items.BAKED_POTATO));
                    playerMP.addPotionEffect(new PotionEffect(slowness, 4 * 20, 255, false, false));

                }
            }else if(z <= stations.get("FarmBeef1").getZ() && z >= stations.get("FarmBeef2").getZ() && y == stations.get("FarmBeef1").getY() && x == stations.get("FarmBeef1").getX()){
                if(playerMP.getHeldItemMainhand().getItem() == Items.AIR){
                    playerMP.inventory.setInventorySlotContents(playerMP.inventory.currentItem, new ItemStack(Items.BEEF));
                }
            }else if(x <= stations.get("FarmChicken1").getX() && x >= stations.get("FarmChicken2").getX() && y == stations.get("FarmChicken1").getY() && z == stations.get("FarmChicken1").getZ()){
                if(playerMP.getHeldItemMainhand().getItem() == Items.AIR){
                    playerMP.inventory.setInventorySlotContents(playerMP.inventory.currentItem, new ItemStack(Items.CHICKEN));
                }
            }else if(z >= stations.get("BakerySandwich1").getZ() && z <= stations.get("BakerySandwich2").getZ() && y == stations.get("BakerySandwich1").getY() && x == stations.get("BakerySandwich1").getX()){
                if (playerMP.getHeldItemMainhand().getItem() == Items.AIR) {
                    playerMP.inventory.setInventorySlotContents(playerMP.inventory.currentItem, new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("minebikemod:hamburgerbun"))));
                }
            }else if(x >= stations.get("BakeryHamburger1").getX() && x <= stations.get("BakeryHamburger2").getX() && y == stations.get("BakeryHamburger1").getY() && z == stations.get("BakeryHamburger1").getZ()){
                if (playerMP.getHeldItemMainhand().getItem() == Items.AIR) {
                    playerMP.inventory.setInventorySlotContents(playerMP.inventory.currentItem, new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("minebikemod:sandwichbread"))));
                }
            }
        }
        sameBlock = true;
    }

    //Puts all the keys and values into the Hashmap of locations
    public void setTPLocations(){
        stations.put("Beacon", new BlockPos(0,4,0));
        stations.put("Cooking1", new BlockPos(-46,4,1));
        stations.put("Cooking2", new BlockPos(-46,4,-1));
        stations.put("Water", new BlockPos(0,5,42));
        //NOTE: Oven1 and Oven2 are only the first and last redstone block in the oven station
        stations.put("Oven1", new BlockPos(-2,6,-52));
        stations.put("Oven2",new BlockPos(2,6,-52));
        stations.put("Cutting1", new BlockPos(45,4,-2));
        stations.put("Cutting2", new BlockPos(47,4,-2));
        stations.put("Cutting3", new BlockPos(49,4,0));
        stations.put("Cutting4", new BlockPos(47,4,2));
        stations.put("Cutting5", new BlockPos(45,4,2));
        stations.put("Restaurant", new BlockPos(956,7,30));
        stations.put("FarmBeef1", new BlockPos(-46, 4, -38));
        stations.put("FarmBeef2", new BlockPos(-46, 4, -40));
        stations.put("FarmChicken1", new BlockPos(-38, 4, -46));
        stations.put("FarmChicken2", new BlockPos(-40,4,-46));
        stations.put("FarmPotato1", new BlockPos(-40,4,-41));
        stations.put("FarmPotato2", new BlockPos(-40,4,-42));
        stations.put("FarmLettuce1", new BlockPos(-41,4,-40));
        stations.put("FarmLettuce2", new BlockPos(-42,4,-40));
        stations.put("BakerySandwich1", new BlockPos(42,4,45));
        stations.put("BakerySandwich2", new BlockPos(42,4,47));
        stations.put("BakeryHamburger1", new BlockPos(45,4,42));
        stations.put("BakeryHamburger2", new BlockPos(47,4,42));
    }

    public void removeIngredients(EntityPlayerMP playerMP, Recipe rec){
        InventoryPlayer inv = playerMP.inventory;
        Item bread = rec.getType();
        Item[] ings = rec.getInsides();
        int invLen = inv.getSizeInventory();

        for(int i = 0 ; i < invLen; i ++){
            ItemStack cur = inv.getStackInSlot(i);
            if (cur.getItem() == bread) {
                inv.setInventorySlotContents(i, new ItemStack(Items.AIR));
            }
            else
            {
                for(Item ingr : ings){
                    if (cur.getItem() == ingr)
                    {
                        inv.setInventorySlotContents(i, new ItemStack(Items.AIR));
                        break;
                    }
                }
            }
        }

    }

    public void spawnNPCs(){
        for (Map.Entry<String, AbstractCustomNpc> iter: NpcDatabase.npcs.entrySet()) {
            AbstractCustomNpc cur = iter.getValue();
            if(cur.getName() == "Shuttle" || cur.getName() == "Manager" || cur.getName() == "Waiter"){
                if(cur.isSpawned) continue;
                EntityCustomNpc npcEntity = NpcUtils.spawnNpc(cur.getLocation(), overcookWs,overcookWs,cur.getName(), cur.getTexture());
                cur.setUUID(npcEntity.getUniqueID().toString());
            }
        }
        System.out.println("Attempted to spawn NPCs");
    }

    public void orderComplete(EntityPlayerMP playerMP){
//        System.out.println("Testing for " + order);
//        if(curOrders.get(0).getName() == order){
//            score += completeOrder;
//            for(EntityPlayer player: playersInGame) {
//                player.sendStatusMessage(new TextComponentTranslation("Order for " + order + " Has Been Completed!", new Object[0]).setStyle((new Style()).setColor(TextFormatting.BLUE)), false);
//            }
//        }
        for(int i = 0; i < playerMP.inventory.getSizeInventory(); i++){
            if(playerMP.inventory.getStackInSlot(i) != null){
                if(curOrders.size() > 0 && playerMP.inventory.getStackInSlot(i).getDisplayName() == curOrders.get(0).getName()){
                    playerMP.sendStatusMessage(new TextComponentTranslation("Order for " + curOrders.get(0).getName() + " Has Been Completed!", new Object[0]).setStyle((new Style()).setColor(TextFormatting.BLUE)), false);
                    playerMP.inventory.setInventorySlotContents(i, new ItemStack(Items.AIR));
                    curOrders.remove(0);
                    orderAddTime.remove(0);
                    score += completeOrder;
                    for(EntityPlayer player : playersInGame) {
                        ServerUtils.sendQuestData(EnumPacketServer.OverCookedOrderComplete, (EntityPlayerMP) player, "0");
                    }
                }
            }
        }
    }

    public void teleportPlayer(String coord, EntityPlayerMP playerMP){
        String[] coords = coord.split(" ");
        int x = (int)Double.parseDouble(coords[0]);
        int y = (int)Double.parseDouble(coords[1]);
        int z = (int)Double.parseDouble(coords[2]);
        Vec3d destination = new Vec3d(x,y,z);
        ServerUtils.telport(playerMP,destination, this.DIMID);
        System.out.println("Teleport " + playerMP.getName() + " to " + destination);
    }
}
