package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.client.AI.OuterAI;
import edu.ics.uci.minebike.minecraft.client.HudManager;
import edu.ics.uci.minebike.minecraft.item.GameFishingEvent;
import edu.ics.uci.minebike.minecraft.quests.CustomQuestManager;
import edu.ics.uci.minebike.minecraft.serverSave.ServerSaveManager;
import net.doubledoordev.d3commands.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;
@Mod(modid = BiGXMain.MOD_ID,name = BiGXMain.MOD_NAME, version = BiGXMain.MOD_VERSION,
//        dependencies = "required-after:soccer;required-after:fishingmadebetter;required-after:aquaculture;")
        dependencies = "required-after:soccer;required-after:fishingmadebetter;required-after:aquaculture;required-after:bike;")
public class BiGXMain {
    public static final String MOD_ID = "minebikemod";
    public static final String MOD_NAME = "MineBike Mod";
    public static final String MOD_VERSION = "0.1.0";
    private static Logger logger;
    public static CommonEventHandler handler = new CommonEventHandler();
    public static CommonProxy proxy;// = new CommonProxy();
    public static ServerSaveManager saveManager ;
    public static CustomQuestManager questMangager;
    public static GameFishingEvent gameFishingEvent= new GameFishingEvent();
    public static OuterAI outerAI;
//    FMLne
    public HudManager hudManager;
    public static FMLEventChannel Channel;
    public static FMLEventChannel ChannelPlayer;
    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event){


        proxy = new CommonProxy();
        Channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("MineBikeServer");
        ChannelPlayer = NetworkRegistry.INSTANCE.newEventDrivenChannel("MineBikeClient");


        // Also need to load the data from the save here and sent the packet to the client
        System.out.println("Preinitializing - Finish registering the Custom Dimensions");
        // preInit goes here
        System.out.printf("MineBike: PreInit finished");
        logger = event.getModLog();

        if(event.getSide().isClient()){
            outerAI = OuterAI.getInstance();
            MinecraftForge.EVENT_BUS.register(outerAI);
        }

        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        MinecraftForge.EVENT_BUS.register(handler);
        MinecraftForge.EVENT_BUS.register(proxy);
        MinecraftForge.EVENT_BUS.register(gameFishingEvent);


        proxy.load();
        logger.info("MineBike: PreInit finished");
    }


    @EventHandler
    public void init(FMLInitializationEvent event){
        // Initialization goes here!
        logger.info("Initalization Started");
        if(event.getSide().isClient()){
            hudManager = HudManager.getInstance(Minecraft.getMinecraft());
            MinecraftForge.EVENT_BUS.register(hudManager);


        }else{ // should be server side
            saveManager = new ServerSaveManager();
        }
        ModConfig.tpxPermissionLevel = 0;
        logger.info("Setting tpxPermission Level to " + ModConfig.tpxPermissionLevel);
        logger.info("MineBike: Init finished");

        System.out.println();
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        // Post initialization codes goes here!
        //handler = new CommonEventHandler();

        logger.info("MineBike: PostInit finished");
        questMangager = new CustomQuestManager();
    }

}
