package edu.ics.uci.minebike.minecraft.client;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
//import org.ngs.bigx.dictionary.objects.clinical.BiGXPatientPrescription;
import edu.ics.uci.minebike.minecraft.client.item.ItemGameFishingRod;

public class ItemManager {
    public static final ItemGameFishingRod GAME_FISHING_ROD = new ItemGameFishingRod("game_rod", 20,5,1);


    public ItemManager() {
    }

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(new Item[]{GAME_FISHING_ROD});
        }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        GAME_FISHING_ROD.registerItemModel(GAME_FISHING_ROD);

    }
}