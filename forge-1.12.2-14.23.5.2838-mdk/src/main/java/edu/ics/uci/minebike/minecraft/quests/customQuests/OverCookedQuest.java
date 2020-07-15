package edu.ics.uci.minebike.minecraft.quests.customQuests;

import java.time.Clock;
import java.util.ArrayList;

import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.item.CookBurgerBun;
import edu.ics.uci.minebike.minecraft.item.CookLettuce;
import edu.ics.uci.minebike.minecraft.item.CookSandwichBread;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderOverCooked;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OverCookedQuest extends AbstractCustomQuest {


    public int DIMID;
    private boolean isStarted;
    private boolean isWaiting;
    public Vec3d questStartLocation;
    private final int completeOrder = 30;
    private final int failedOrder = -10;
    private int score = 0;
    private int ticks = 0;
    private boolean countTicks;
    private ArrayList<Vec3d> ingredientCoord;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private long gameTime = 480000; //ms 8min
    private long waitTime = 10000;//ms 10sec
    private long serverStartTime;
    private long serverWaitTime;

    private HudRectangle rectangle;
//             = new HudRectangle(580,280, 50,50, 0x4aa188, true, false);
    private HudString scoreTitle;
//         = new HudString(590,290, "Score:");
    private HudString scoreVal;
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
            isWaiting = true;

            return false;
        }else{
            if (!isWaiting)
            {
                WorldServer ws = DimensionManager.getWorld(this.DIMID);
                isWaiting = true;
            }
            ServerUtils.telport((EntityPlayerMP)player, this.questStartLocation,this.DIMID);
            return true;
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


    @SideOnly(Side.SERVER)
    @Override
    public void start(EntityPlayerMP player)
    {
        if(isStarted)
        {
            System.out.println("Game Already Started Going to Waiting");

        }
        if(!isWaiting)
        {

        }
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

    @Override
    public void start()
    {

    }// This is the start interface for client

    @Override
    public void end()
    {

    }

    @Override
    public void onWorldTick(TickEvent.WorldTickEvent event)
    {

    }


    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {

    }


}
