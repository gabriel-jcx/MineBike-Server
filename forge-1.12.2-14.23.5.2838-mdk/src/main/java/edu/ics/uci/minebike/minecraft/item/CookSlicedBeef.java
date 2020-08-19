package edu.ics.uci.minebike.minecraft.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CookSlicedBeef  extends ItemFood {
    public CookSlicedBeef()
    {
        super(10,2.5f,false);
        setUnlocalizedName("sliced_beef");
        setRegistryName("sliced_beef");
        setCreativeTab(CreativeTabs.FOOD);
    }

    @SideOnly(Side.CLIENT)
    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
