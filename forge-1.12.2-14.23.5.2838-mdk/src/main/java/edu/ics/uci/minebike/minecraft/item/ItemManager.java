package edu.ics.uci.minebike.minecraft.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
import edu.ics.uci.minebike.minecraft.item.ItemGameFishingRod;

public class ItemManager {
    public static final ItemFishingRod GAME_FISHING_ROD = new ItemGameFishingRod();


    public ItemManager() {
    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(new Item[]{GAME_FISHING_ROD});
    }


    public static void registerModels() {
        //GAME_FISHING_ROD.registerItemModels(GAME_FISHING_ROD);
        ModelLoader.setCustomModelResourceLocation(GAME_FISHING_ROD, 0, new ModelResourceLocation(GAME_FISHING_ROD.getRegistryName(), "inventory"));


    }
}
