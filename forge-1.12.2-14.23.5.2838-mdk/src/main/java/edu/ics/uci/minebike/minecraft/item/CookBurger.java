package edu.ics.uci.minebike.minecraft.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.model.ModelLoader;

public class CookBurger  extends ItemFood {
    public CookBurger()
    {
        super(5,4f,false);
        setUnlocalizedName("hamburger");
        setRegistryName("hamburger");

    }
    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

//    public static Item makeItem(Class itemClass, String name, String ResourceLocation) throws InstantiationException, IllegalAccessException
//    {
//        return ((Item)(itemClass.newInstance())).setUnlocalizedName(name).setFull3D().setCreativeTab(CreativeTabs.MISC).setTextureName(ResourceLocation);
//    }
}
