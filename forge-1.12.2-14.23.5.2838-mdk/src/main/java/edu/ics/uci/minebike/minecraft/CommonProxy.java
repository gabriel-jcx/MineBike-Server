package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.worlds.WorldProviderSoccerQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class CommonProxy implements IGuiHandler {
    public CommonProxy(){
        System.out.println("Registering Dimension with id = " + WorldProviderSoccerQuest.DIM_ID);
        DimensionType soccerDType = DimensionType.register("soccerDim", "customDim", WorldProviderSoccerQuest.DIM_ID,WorldProviderSoccerQuest.class, true);
        DimensionManager.registerDimension(soccerDType.getId(),soccerDType);
    }
    public void load(){
        BiGXMain.Channel.register(new PacketHandlerServer());
        BiGXMain.ChannelPlayer.register(new PacketHandlerClient());
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
