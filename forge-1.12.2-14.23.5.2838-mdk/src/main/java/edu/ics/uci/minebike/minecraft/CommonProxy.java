package edu.ics.uci.minebike.minecraft;

//import biomesoplenty.common.biome.overworld.BiomeGenWhiteBeach;
import edu.ics.uci.minebike.minecraft.worlds.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class CommonProxy implements IGuiHandler {
    public CommonProxy(){
//        System.out.println("Registering Dimension with id = " + WorldProviderSoccerQuest.DIM_ID);
        DimensionType soccerDType = DimensionType.register("soccerDim", "customDim", WorldProviderSoccerQuest.DIM_ID,WorldProviderSoccerQuest.class, true);
        DimensionManager.registerDimension(soccerDType.getId(),soccerDType);
        DimensionType fishingDtype = DimensionType.register("fishingDim", "customDim", WorldProviderFishing.DIM_ID,WorldProviderFishing.class ,true);
        DimensionManager.registerDimension(fishingDtype.getId(),fishingDtype);
        DimensionType fishingOceanDtype = DimensionType.register("oceanDim", "customDim", WorldProviderFishingOcean.DIM_ID, WorldProviderFishingOcean.class, true);
        DimensionManager.registerDimension(fishingOceanDtype.getId(), fishingOceanDtype);
        DimensionType minerDtype = DimensionType.register("minerDim", "customDim", WorldProviderMiner.DIM_ID, WorldProviderMiner.class, true);
        DimensionManager.registerDimension(minerDtype.getId(), minerDtype);
        DimensionType OverCookedDtype = DimensionType.register("OverCookedDim", "customDim", WorldProviderOverCooked.DIM_ID, WorldProviderOverCooked.class, true);
        DimensionManager.registerDimension(OverCookedDtype.getId(), OverCookedDtype);

    }
    public void load(){
        BiGXMain.Channel.register(new PacketHandlerClient());
        BiGXMain.ChannelPlayer.register(new PacketHandlerServer());
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
