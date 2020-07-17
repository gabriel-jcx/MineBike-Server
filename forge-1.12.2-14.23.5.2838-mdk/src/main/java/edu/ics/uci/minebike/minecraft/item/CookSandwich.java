package edu.ics.uci.minebike.minecraft.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.model.ModelLoader;

public class CookSandwich  extends ItemFood {
    public CookSandwich()
    {
        super(5,4f,false);
        setUnlocalizedName("sandwich");
        setRegistryName("sandwich");
//        setTextureName();
    }
    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
