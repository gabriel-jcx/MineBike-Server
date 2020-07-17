package edu.ics.uci.minebike.minecraft.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.model.ModelLoader;

public class CookSandwichBread extends ItemFood {
    public CookSandwichBread(){
        super(2,1.2f,false);
        setUnlocalizedName("sandwichbread");
        setRegistryName("sandwichbread");
    }
    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
